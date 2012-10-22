package seasonal.parade.halloween;

import java.util.List;

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
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;

public class TileMixer extends HalloweenTile implements ITankContainer, IInventory
{
	public static Item[] milkContainers = new Item[]{
			Item.bucketMilk,
			Halloween.milkCell,
			Halloween.milkCan,
			Halloween.milkWaxCapsule,
			Halloween.milkRefractoryCapsule
	};
	public static Item[] emptyContainers = new Item[]{
			Item.bucketEmpty,
			Halloween.cell,
			Halloween.canEmpty,
			Halloween.wax_capsule,
			Halloween.refractory_capsule
	};
	public static Item[] ingredients = new Item[]{
		Item.sugar,
		Item.slimeBall
	};
	
	private static final float MAX_LIQUID = LiquidManager.BUCKET_VOLUME * 16;

	private ItemStack[] inventory;

	private int update = 0;
	
    public final ILiquidTank tankMilk = new LiquidTank((int)MAX_LIQUID);
    public final ILiquidTank tankCandy = new LiquidTank((int)MAX_LIQUID);
    
    public int tankCandyID = 0;
    public int TankCandyID = 1;
    
    public boolean hasUpdate = false;
    public SafeTimeTracker tracker = new SafeTimeTracker();

    
    public static <T> boolean contains( final T[] array, final T v ) {
        for ( final T e : array )
            if ( e == v || v != null && v.equals( e ) )
                return true;

        return false;
    }
    /* UPDATING */
    @Override
    public void updateEntity()
    {
        
    	update++;
    	
    	if(update % 5 == 0){
    		ItemStack milkslot = getStackInSlot(4);
    		if(milkslot != null){
    		
	    		if(milkslot.getItem() == Item.bucketMilk && this.tankMilk.fill(LiquidStacks.milk, false) == LiquidStacks.milk.amount){
	    			this.tankMilk.fill(LiquidStacks.milk, true);
	    			milkslot.stackSize = 1;
	    			milkslot.itemID = Item.bucketEmpty.shiftedIndex;
	    		
	    		}
	    		
	    		if(contains(milkContainers, milkslot.getItem())  &&
		    			this.tankMilk.fill(LiquidStacks.milk, false) == LiquidStacks.milk.amount
	    			){
	    			this.tankMilk.fill(LiquidStacks.milk, true);
	    			decrStackSize(4, 1);
	    			hasUpdate = true;
	    			
	    		}
	    		
	    		/*DEBUG FILL CANDY*/
//	    		if(milkslot.getItem() == Halloween.rawCandyBucket && this.tankCandy.fill(LiquidStacks.rawCandy, false) == LiquidStacks.rawCandy.amount){
//	    			this.tankCandy.fill(LiquidStacks.rawCandy, true);
//	    			hasUpdate = true;
//	    		}
    		}
    	
    	
	    	//Fill Candy
			ItemStack emptyCanInSlot = getStackInSlot(5);
			ItemStack filledCanInSlot = getStackInSlot(6);
			
			if(emptyCanInSlot != null){
				LiquidStack available = tankCandy.getLiquid();
	            if(available != null){
	                ItemStack filled = LiquidManager.fillLiquidContainer(available, emptyCanInSlot);
	
	                LiquidStack liquid = LiquidManager.getLiquidForFilledItem(filled);
	               
	                if(liquid != null && filled != null) {
	                	if(filledCanInSlot != null && filledCanInSlot.getItem() == filled.getItem()){
	                		if(filledCanInSlot.stackSize < filledCanInSlot.getItem().getItemStackLimit()){
		                    	decrStackSize(5, 1);
		                    	++inventory[6].stackSize;
			                    tankCandy.drain(liquid.amount, true);
			                    
			                    hasUpdate = true;
	                		}
	                	}
	                	
	                	if(filledCanInSlot == null || filledCanInSlot.stackSize == 0){
	                		
	                		decrStackSize(5, 1);
	                    	setInventorySlotContents(6, filled);
		                    tankCandy.drain(liquid.amount, true);
		                    
		                    hasUpdate = true;
	                	}
	                }
	            }
			}
    	}
    	
    	if(CoreProxy.proxy.isSimulating(worldObj) && hasUpdate && tracker.markTimeIfDelay(worldObj, 2 * Halloween.updateFactor)) {
            sendNetworkUpdate();
            hasUpdate = false;
        }
    }
    @Override
    public void onInventoryChanged(){
    	hasUpdate = true;
    }
	public int getScaledMilk(int i) {
		if(this.tankMilk.getLiquid() == null)
			return 0;
		return (int) (((float) this.tankMilk.getLiquid().amount / (float) (MAX_LIQUID)) * i);
	}

	public int getScaledCandy(int i) {
		if(this.tankCandy.getLiquid() == null)
			return 0;
		return (int) (((float) this.tankCandy.getLiquid().amount / (float) (MAX_LIQUID)) * i);
	}
    
    public TileMixer() {
		inventory = new ItemStack[7]; // 7 slots in container: 0, 1, 2
	}
    
    /* NETWORK */
    @Override
    public PacketPayload getPacketPayload()
    {
        PacketPayload payload = new PacketPayload(6, 0, 0);
        if(this.tankMilk.getLiquid() != null) {
            payload.intPayload[0] = this.tankMilk.getLiquid().itemID;
            payload.intPayload[1] = this.tankMilk.getLiquid().itemMeta;
            payload.intPayload[2] = this.tankMilk.getLiquid().amount;
        } else {
            payload.intPayload[0] = 0;
            payload.intPayload[1] = 0;
            payload.intPayload[2] = 0;
        }
        if(this.tankCandy.getLiquid() != null){
        	payload.intPayload[3] = this.tankCandy.getLiquid().itemID;
            payload.intPayload[4] = this.tankCandy.getLiquid().itemMeta;
            payload.intPayload[5] = this.tankCandy.getLiquid().amount;
    	} else {
            payload.intPayload[3] = 0;
            payload.intPayload[4] = 0;
            payload.intPayload[5] = 0;
    	}
        return payload;
    }

    @Override
    public void handleUpdatePacket(PacketUpdate packet)
    {
        if(packet.payload.intPayload[0] > 0) {
            LiquidStack liquidMilk = new LiquidStack(packet.payload.intPayload[0], packet.payload.intPayload[2], packet.payload.intPayload[1]);
            
        	this.tankMilk.setLiquid(liquidMilk);
        } else {
    		this.tankMilk.setLiquid(null);
        }
        if(packet.payload.intPayload[3] > 0) {
        	LiquidStack liquidCandy = new LiquidStack(packet.payload.intPayload[3], packet.payload.intPayload[5], packet.payload.intPayload[4]);
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
		
		LiquidStack liquid = new LiquidStack(0, 0, 0);
		liquid.readFromNBT(data.getCompoundTag("tank"));
		this.tankMilk.setLiquid(liquid);
		
		LiquidStack liquidCandy = new LiquidStack(0, 0, 0);
		liquidCandy.readFromNBT(data.getCompoundTag("tankCandy"));
	    this.tankCandy.setLiquid(liquidCandy);
    }

    @Override
    public void writeToNBT(NBTTagCompound data)
    {
    	super.writeToNBT(data);
    	
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

		
        if(this.tankMilk.getLiquid() != null)
            data.setTag("tank", this.tankMilk.getLiquid().writeToNBT(new NBTTagCompound()));
		
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
            int used = this.tankMilk.fill(resource, doFill);
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
        ILiquidTank compositeTank = new LiquidTank(this.tankMilk.getCapacity());
        ILiquidTank compositeTankCandy = new LiquidTank(this.tankCandy.getCapacity());

        
        int capacity = this.tankMilk.getCapacity();
        int capacityCandy = this.tankMilk.getCapacity();

        if(this != null && this.tankMilk.getLiquid() != null) {
            compositeTank.setLiquid(this.tankMilk.getLiquid().copy());//Milk and the other tank is Raw Candy
        } else if(this != null && this.tankCandy.getLiquid() != null) {
            compositeTankCandy.setLiquid(this.tankCandy.getLiquid().copy());
        } else {
            return new ILiquidTank[]{compositeTank, compositeTankCandy};
        }

        compositeTank.setCapacity(capacity);
        compositeTankCandy.setCapacity(capacityCandy);
        return new ILiquidTank[]{compositeTank, compositeTankCandy};
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
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int amount) {
		ItemStack stack = getStackInSlot(slotIndex);

		if (stack != null) {
			if (stack.stackSize <= amount) {
				setInventorySlotContents(slotIndex, null);
			} else {
				stack = stack.splitStack(amount);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slotIndex, null);
				}
			}
		}

		return stack;
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
		return "Mixer";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}
}