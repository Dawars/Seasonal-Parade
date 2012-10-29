package seasonal.parade.halloween;

import java.util.HashMap;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import seasonal.parade.halloween.network.PacketPayload;
import seasonal.parade.halloween.network.PacketUpdate;
import seasonal.parade.halloween.proxy.CoreProxy;
import buildcraft.api.core.Orientations;
import buildcraft.api.core.SafeTimeTracker;
import buildcraft.api.liquids.ILiquidTank;
import buildcraft.api.liquids.ITankContainer;
import buildcraft.api.liquids.LiquidManager;
import buildcraft.api.liquids.LiquidStack;
import buildcraft.api.liquids.LiquidTank;
import buildcraft.api.transport.IPipe;
import buildcraft.api.transport.IPipeConnection;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileCandyMaker extends HalloweenTile implements ITankContainer, IInventory, ISidedInventory
{

    public TileCandyMaker() {
		inventory = new ItemStack[10]; // 10 slots in container: 0, 1, 2
	}
	private boolean init = false;

	private static final int MIN_LIQUID_TO_PROCESS = LiquidManager.BUCKET_VOLUME / 2;
	private static final float MAX_LIQUID = LiquidManager.BUCKET_VOLUME * 16;

	public boolean isRedstonePowered = false;
	
    /** The number of ticks that the current item has been cooking for */
    public int cookTime = 0;
	
	private ItemStack[] inventory;

	private int update = 0;
	
    public final ILiquidTank tankCandy = new LiquidTank((int)MAX_LIQUID);
    
    public boolean hasUpdate = false;
    public SafeTimeTracker tracker = new SafeTimeTracker();

    /* UPDATING */
    @Override
    public void updateEntity()
    {    	
    	if (!init) {
			initialize();
			init = true;
		}
    	
    	update++;
    	if(CoreProxy.proxy.isSimulating(worldObj) && hasUpdate && tracker.markTimeIfDelay(worldObj, 2 * Halloween.updateFactor)) {
            sendNetworkUpdate();
            hasUpdate = false;
        }
    	
        if(CoreProxy.proxy.isRenderWorld(worldObj)) {
            return;
        }
            	
    	
        if(this.canWork())
        {
            ++this.cookTime;

            if (this.cookTime == 200)
            {
                this.cookTime = 0;
                this.smeltItem();
            }
        } else {
        	this.cookTime = 0;
        }
        
        
    	if(update % 5 == 0){
    		
    		
    		
    		ItemStack candyslot = getStackInSlot(4);
    		if(candyslot != null){
    		
	    		if(candyslot.getItem() == Halloween.rawCandyBucket && this.tankCandy.fill(LiquidStacks.rawCandy, false) == LiquidStacks.rawCandy.amount){
	    			this.tankCandy.fill(LiquidStacks.rawCandy, true);
	    			candyslot.stackSize = 1;
	    			candyslot.itemID = Item.bucketEmpty.shiftedIndex;
	    		
	    		}else if(LiquidHelper.contains(LiquidHelper.candyContainers, candyslot.getItem())  &&
		    			this.tankCandy.fill(LiquidStacks.rawCandy, false) == LiquidStacks.rawCandy.amount
	    			){
	    			this.tankCandy.fill(LiquidStacks.rawCandy, true);
	    			decrStackSize(4, 1);
	    			
	    		}
    		}
	    	
    	}
    	
    	
    }
   
	public boolean canWork() {
		if(!this.isRedstonePowered)
			return false;
		if(inventory[5] == null)
			return false;
		if(inventory[5].getItem() != Item.paper)
			return false;
		if(inventory[5].stackSize < 1)
			return false;
		
		for(int i = 0; i < 4; i++){
			
			if(this.tankCandy.getLiquid() != null && this.tankCandy.getLiquid().amount >= MIN_LIQUID_TO_PROCESS){
				if(inventory[i] != null){
					ItemStack stack = CandyRecipes.Recipe().getRecipeResult(this.inventory[i]);
		            if (stack != null){
			            if (this.inventory[i+6] == null)
			            	return true;
			            if (!this.inventory[i+6].isItemEqual(stack))
			            	continue;
			            int result = inventory[i+6].stackSize + stack.stackSize;
			            if(result <= getInventoryStackLimit() && result <= stack.getMaxStackSize())
			            	return true;
		            }
				}
			}
		}
		
		return false;
	}
	
	/**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem()
    {
    	for(int i = 0; i<4; i++){
	        if (this.canWork() && this.inventory[i] != null)
	        {
	        	if(this.inventory[5] == null)
	        		return;
	        	
	            ItemStack stack = CandyRecipes.Recipe().getRecipeResult(this.inventory[i]);
	
	            if(stack != null){
		            if (this.inventory[i+6] == null)
		            {
		                this.inventory[i+6] = stack.copy();
		                
		                this.onInventoryChanged();
		        		
			            this.decrStackSize(i, 1);//recipe
			            
			            this.decrStackSize(5, 1);//paper
			            
			            this.tankCandy.drain(MIN_LIQUID_TO_PROCESS, true);
			            hasUpdate = true;
		            }
		            else if (this.inventory[i+6].isItemEqual(stack))
		            {
		            	inventory[i+6].stackSize += stack.stackSize;
		            	
		            	this.onInventoryChanged();
		        		
			            this.decrStackSize(i, 1);//recipe
			            
			            this.decrStackSize(5, 1);//paper
			            
			            this.tankCandy.drain(MIN_LIQUID_TO_PROCESS, true);
			            hasUpdate = true;
		            }
		            
		            
	            }
	        }
    	}
    }
    
	public int getScaledCandy(int i) {
		if(this.tankCandy.getLiquid() == null)
			return 0;
		return (int) (((float) this.tankCandy.getLiquid().amount / (float) (MAX_LIQUID)) * i);
	}
    
    /* NETWORK */
    @Override
    public PacketPayload getPacketPayload()
    {
        PacketPayload payload = new PacketPayload(3, 0, 0);
        if(this.tankCandy.getLiquid() != null){
        	payload.intPayload[0] = this.tankCandy.getLiquid().itemID;
            payload.intPayload[1] = this.tankCandy.getLiquid().itemMeta;
            payload.intPayload[2] = this.tankCandy.getLiquid().amount;
    	} else {
            payload.intPayload[0] = 0;
            payload.intPayload[1] = 0;
            payload.intPayload[2] = 0;
    	}
        return payload;
    }

    @Override
    public void handleUpdatePacket(PacketUpdate packet)
    {
        if(packet.payload.intPayload[0] > 0) {
        	LiquidStack liquidCandy = new LiquidStack(packet.payload.intPayload[0], packet.payload.intPayload[2], packet.payload.intPayload[1]);
        	this.tankCandy.setLiquid(liquidCandy);
        } else {
        	this.tankCandy.setLiquid(null);
        }
    }
    
    

    /* SAVING & LOADING */
    @Override
    public void readFromNBT(NBTTagCompound data)
    {
    	super.readFromNBT(data);

		NBTTagList tagList = data.getTagList("Inventory");

		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

			byte slot = tag.getByte("Slot");

			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
        this.cookTime = data.getShort("CookTime");

		LiquidStack liquidCandy = new LiquidStack(0, 0, 0);
		liquidCandy.readFromNBT(data.getCompoundTag("tankCandy"));
		
	    this.tankCandy.setLiquid(liquidCandy);
    }

    @Override
    public void writeToNBT(NBTTagCompound data)
    {
    	super.writeToNBT(data);
        data.setShort("CookTime", (short)this.cookTime);

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
		data.setTag("Inventory", itemList);

        if(this.tankCandy.getLiquid() != null)
            data.setTag("tankCandy", this.tankCandy.getLiquid().writeToNBT(new NBTTagCompound()));
        
    }


    /* ITANKCONTAINER */
    @Override
    public int fill(Orientations from, LiquidStack resource, boolean doFill)
    {
        return fill(0, resource, doFill);
    }

    @Override
    public int fill(int tankIndex, LiquidStack resource, boolean doFill)
    {
        if(tankIndex != 0 || resource == null)
           return 0;

        resource = resource.copy();
        int totalUsed = 0;
        if(this != null && resource.amount > 0){
            int used = this.tankCandy.fill(resource, doFill);
            resource.amount -= used;
            if(used>0)
                this.hasUpdate=true;

            totalUsed += used;
        }
        if(totalUsed>0)
            hasUpdate= true;
        return totalUsed;
    }


    @Override
    public LiquidStack drain(Orientations from, int maxEmpty, boolean doDrain)
    {
        return drain(0, maxEmpty, doDrain);
    }

    @Override
    public LiquidStack drain(int tankIndex, int maxEmpty, boolean doDrain)
    {
        this.hasUpdate = true;
        return this.tankCandy.drain(maxEmpty, doDrain);
    }

    @Override
    public ILiquidTank[] getTanks()
    {
        ILiquidTank compositeTank = new LiquidTank(this.tankCandy.getCapacity());

        
        int capacity = this.tankCandy.getCapacity();

        if(this != null && this.tankCandy.getLiquid() != null) {
            compositeTank.setLiquid(this.tankCandy.getLiquid().copy());//Milk and the other tank is Raw Candy
        } else {
            return new ILiquidTank[]{compositeTank};
        }

        compositeTank.setCapacity(capacity);
        return new ILiquidTank[]{compositeTank};
    }

    /*IINVENTORY*/
    
    @Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return inventory[slotIndex];
	}
	
	/**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.inventory[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getCookProgressScaled(int par1)
    {
        return this.cookTime * par1 / 200;
    }
    
    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.inventory[par1] != null)
        {
            ItemStack var3;

            if (this.inventory[par1].stackSize <= par2)
            {
                var3 = this.inventory[par1];
                this.inventory[par1] = null;
                this.onInventoryChanged();
                return var3;
            }
            else
            {
                var3 = this.inventory[par1].splitStack(par2);

                if (this.inventory[par1].stackSize == 0)
                {
                    this.inventory[par1] = null;
                }

                this.onInventoryChanged();
                return var3;
            }
        }
        else
        {
            return null;
        }
    }

	
	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		ItemStack stack = getStackInSlot(slotIndex);

		if (stack != null) {
			setInventorySlotContents(slotIndex, null);
		}

		return stack;
	}

	@Override
	public String getInvName() {
		return "Candy Maker";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}
	
	

	/* ISidedInventory */
	@Override
	public int getStartInventorySide(ForgeDirection side) {
		
		if (side == ForgeDirection.UP) {
			return 0;
		}
		if (side == ForgeDirection.DOWN) {
			return 5;
		}


		return 6;
	}
	
	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		
		if (side == ForgeDirection.UP) {
			return 4; 
		}

		if (side == ForgeDirection.DOWN) {
			return 1;
		}

		return 4;
	}

	public void checkRedstonePower() {
		isRedstonePowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}
}
