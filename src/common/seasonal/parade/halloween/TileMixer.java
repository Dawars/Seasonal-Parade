package seasonal.parade.halloween;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import buildcraft.api.core.Orientations;
import buildcraft.api.liquids.ILiquidTank;
import buildcraft.api.liquids.ITankContainer;
import buildcraft.api.liquids.LiquidManager;
import buildcraft.api.liquids.LiquidStack;
import buildcraft.api.liquids.LiquidTank;
import net.minecraft.src.*;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileMixer extends TileEntity implements IInventory, ISidedInventory
//		,ITankContainer
		{
	private ItemStack[] inventory;

	public static int MAX_LIQUID = LiquidManager.BUCKET_VOLUME * 10;
	
	/** The number of ticks that the mixer will keep mixing */
	public int mixTime = 0; //0 = not running, 200 = running and done -> 0

	
	private int sugarNumber = 3;
	private int slimeNumber = 1;
	
	
	public TileMixer() {
		inventory = new ItemStack[7]; // 8 slots in container: 0, 1, 2, ..., 7
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return inventory[slotIndex];
	}
	
	@SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getCookProgressScaled(int par1)
    {
        return this.mixTime;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    public int getBurnTimeRemainingScaled(int par1)
    {

        return this.mixTime;
    }

    public boolean isMixing()
    {
        return this.mixTime > 0;
    }

	@Override
	public void updateEntity() {
//		if (this.worldObj.getWorldTime() % 10 == 0 )
//			System.out.println(this.mixTime);
		
		if (!this.worldObj.isRemote)
        {
			if(canMix()){
				this.mixTime++;
			}
			
			//when done: reset the timer, decrease the ingredients and add the result
			if(this.mixTime == 200){
				this.mixTime = 0;
				
				boolean decreasedSugar = false;
				boolean decreasedSlime= false;
				for (int i = 0; i < 4; i++){
					//decrease slime
					if(this.inventory[i] != null){
						if(this.inventory[i].itemID == Item.slimeBall.shiftedIndex &&
								this.inventory[i].stackSize >= slimeNumber){//1 slime
							this.inventory[i].stackSize -= slimeNumber;
	
				            if (this.inventory[i].stackSize <= 0)
				            {
				                this.inventory[i] = null;
				            }
				            decreasedSlime = true;
						}
					}
						//decrease slime
					if(this.inventory[i] != null){
						if(this.inventory[i].itemID == Item.sugar.shiftedIndex &&
								this.inventory[i].stackSize >= sugarNumber){//3 sugar
							this.inventory[i].stackSize -= sugarNumber;
	
				            if (this.inventory[i].stackSize <= 0)
				            {
				                this.inventory[i] = null;
				            }
				            decreasedSugar = true;
						}
					}
					if(decreasedSlime && decreasedSugar)
						break;
				}
				//add result
				ItemStack stack = new ItemStack(Item.appleRed, 1); //test result is an apple :)
				
				if (this.inventory[5] == null)
	            {
	                this.inventory[5] = stack.copy();
	            }
	            else if (this.inventory[5].isItemEqual(stack))
	            {
	            	inventory[5].stackSize += stack.stackSize;
	            }
			}
			
			//if not running reset the timer
			if(!canMix() && this.mixTime != 0){
				this.mixTime = 0;
			}
        }
	}

	public boolean canMix() {
			// if(tank==full){
			// return false;
			// }
			
			// if(milkTank==empty){
			// return false;
			// }
			
			//test recipe 1 sugar and 1 slimeball
			boolean isSugar = false;
			boolean isSlimeBall = false;
			
			for (int i = 0; i < 4; i++){

				if(this.inventory[i] != null){
					if(this.inventory[i].itemID == Item.sugar.shiftedIndex &&
							this.inventory[i].stackSize >= sugarNumber){//3 sugar
						isSugar = true;
						break;
					}
				}
			}
			for (int i = 0; i < 4; i++){
				if(this.inventory[i] != null){
					if(this.inventory[i].itemID == Item.slimeBall.shiftedIndex &&
							this.inventory[i].stackSize >= slimeNumber){//1 slime
						isSlimeBall = true;
						break;
					}
				}
			}
			
			if(isSlimeBall && isSugar)
				return true;
		
		return false;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		// This checks to make sure the stack is not nothing, and then makes
		// sure the stack is not going over the limit
		// Of the stack
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int amount) {
		// This gets the stack with the slot number you want
		ItemStack stack = getStackInSlot(slotIndex);

		// Then it checks to make sure it has something in it
		if (stack != null) {
			// Then it checks to make sure that it has something that is equal
			// or lesser than the amount you want to add
			if (stack.stackSize <= amount) {
				setInventorySlotContents(slotIndex, null);
			} else {
				stack = stack.splitStack(amount);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slotIndex, null);
				}
			}
		}

		// Then it returns the stack
		return stack;
	}

	// This returns the stack in the slot you chose
	// It has 1 param
	// @param int slotIndex this is the slot number you choose to get
	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		// This gets the stack in the slot you chose
		ItemStack stack = getStackInSlot(slotIndex);

		// This checks to make sure it has something in it
		if (stack != null) {
			setInventorySlotContents(slotIndex, null);
		}

		// Then it returns the stack
		return stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this == player
				.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory");

		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

			byte slot = tag.getByte("Slot");

			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
        this.mixTime = tagCompound.getShort("MixTime");

	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("MixTime", (short)this.mixTime);

		NBTTagList itemList = new NBTTagList();

		for (int i = 0; i < inventory.length; i++) {
			ItemStack stack = inventory[i];

			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();

				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}

		tagCompound.setTag("Inventory", itemList);
		
	}

	@Override
	public String getInvName() {
		return "Mixer";
	}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		if (side == ForgeDirection.DOWN) {
			return 4;
		}

		if (side == ForgeDirection.UP) {
			return 0;
		}

		return 6;
	}

	/* ISidedInventory */
	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		if (side == ForgeDirection.DOWN) {
			return 1; // There is 1 slot for bottom - for liquid cans started
						// with slotID defined in getStartInventorySide method
						// (slot 4)
		}

		if (side == ForgeDirection.UP) {
			return 4; // There are 4 slots for top - for ingredients started
						// with slotID defined in getStartInventorySide method
						// (slot 0, 1, 2, 3)
		}

		return 1; // There is one slot for sides - for cans started with slotID
					// defined in getStartInventorySide method (slot 6)
	}

	/* ITANKCONTAINER */
//	public int fill(Orientations from, LiquidStack resource, boolean doFill) {
//
//		// Handle coolant
//		if (IronEngineCoolant.getCoolantForLiquid(resource) != null)
//			return fillCoolant(from, resource, doFill);
//
//		int res = 0;
//
//		if (liquidQty > 0 && liquidId != resource.itemID) {
//			return 0;
//		}
//
//		if (IronEngineFuel.getFuelForLiquid(resource) == null)
//			return 0;
//
//		if (liquidQty + resource.amount <= MAX_LIQUID) {
//			if (doFill) {
//				liquidQty += resource.amount;
//			}
//
//			res = resource.amount;
//		} else {
//			res = MAX_LIQUID - liquidQty;
//
//			if (doFill) {
//				liquidQty = MAX_LIQUID;
//			}
//		}
//
//		liquidId = resource.itemID;
//
//		return res;
//	}
//
//	private int fillCoolant(Orientations from, LiquidStack resource, boolean doFill) {
//		int res = 0;
//
//		if (coolantQty > 0 && coolantId != resource.itemID)
//			return 0;
//
//		if (coolantQty + resource.amount <= MAX_LIQUID) {
//			if (doFill)
//				coolantQty += resource.amount;
//
//			res = resource.amount;
//		} else {
//			res = MAX_LIQUID - coolantQty;
//
//			if (doFill)
//				coolantQty = MAX_LIQUID;
//		}
//
//		coolantId = resource.itemID;
//
//		return res;
//	}
//
//	@Override
//	public LiquidTank[] getLiquidSlots() {
//		return new LiquidTank[] { new LiquidTank(liquidId, liquidQty, MAX_LIQUID),
//				new LiquidTank(coolantId, coolantQty, MAX_LIQUID) };
//	}
}