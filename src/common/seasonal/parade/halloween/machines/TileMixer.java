package seasonal.parade.halloween.machines;

// You will not need this import if you kept the standard package declaration
import net.minecraft.src.*;

// Basically this is where your TileEntity comes from
// Create a class and extend TileEntity and implement IInventory
public class TileMixer extends TileEntity implements IInventory{
	// Then create a ItemStack array (I.E. ItemStack[])
	private ItemStack[] inventory;
	
	// Then create a super constructor and give the inventory the amount of ItemStacks you want (Slots),
	// I.E. (for 1 slot: inventory = new ItemStack[1];, for 5 slots: inventory = new ItemStack[5];)
	public TileMixer(){
		inventory = new ItemStack[8];
	}
	
	// This returns the inventory size
	@Override
	public int getSizeInventory(){
		return inventory.length;
	}
	
	// This returns the stack that is in the slot you picked
	// This has 1 param
	// @param int slotIndex is just the index you selected
	@Override
	public ItemStack getStackInSlot(int slotIndex){
		return inventory[slotIndex];
	}
	
	// This sets the slots contents, it has 2 params
	// @param int slot, this is the slots number
	// @param ItemStack stack, this is the stack you want to add
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack){
		inventory[slot] = stack;
		// This checks to make sure the stack is not nothing, and then makes sure the stack is not going over the limit
		// Of the stack
		if(stack != null && stack.stackSize > getInventoryStackLimit()){
			stack.stackSize = getInventoryStackLimit();
		}
	}
	
	// This decreases the stack size
	// It has 2 params
	// @param int slotIndex, this is the slot number
	// @param int amount, this is the amount you want to decreases by
	@Override
	public ItemStack decrStackSize(int slotIndex, int amount){
		// This gets the stack with the slot number you want
		ItemStack stack = getStackInSlot(slotIndex);
		
		// Then it checks to make sure it has something in it
		if(stack != null){
			// Then it checks to make sure that it has something that is equal or lesser than the amount you want to add
			if(stack.stackSize <= amount){
				setInventorySlotContents(slotIndex, null);
			} else{
				stack = stack.splitStack(amount);
				if(stack.stackSize == 0){
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
	public ItemStack getStackInSlotOnClosing(int slotIndex){
		// This gets the stack in the slot you chose
		ItemStack stack = getStackInSlot(slotIndex);
		
		// This checks to make sure it has something in it
		if(stack != null){
			setInventorySlotContents(slotIndex, null);
		}
		
		// Then it returns the stack
		return stack;
	}
	
	// This gets the inventory's stack limit
	// This is normally 64 but on some conditions it is something lower
	@Override
	public int getInventoryStackLimit(){
		return 64;
	}
	
	// Basically this makes sure the player is not to far away to activate the block and, makes sure its the Entity you are
	// Selecting
	@Override
	public boolean isUseableByPlayer(EntityPlayer player){
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this == player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}
	
	// A dummy, mostly un-used method, for a check to do something when you open the Gui
	@Override
	public void openChest() {}
	
	// Another dummy, mostly un-used method, for a check to do something when you close the Gui
	@Override
	public void closeChest() {}
	
	// This is a really hard method to understand and requires Knowledge of minecrafts NBTTag System, and
	// All you really need to do is copy it because its pretty much the same for every entity
	@Override
	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);
		
		NBTTagList tagList = tagCompound.getTagList("Inventory");
		
		for(int i = 0; i < tagList.tagCount(); i++){
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			
			byte slot = tag.getByte("Slot");
			
			if(slot >= 0 && slot < inventory.length){
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}
	
	// Same as the later
	@Override
	public void writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);
		
		NBTTagList itemList = new NBTTagList();
		
		for(int i = 0; i < inventory.length; i++){
			ItemStack stack = inventory[i];
			
			if(stack != null){
				NBTTagCompound tag = new NBTTagCompound();
				
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		
		tagCompound.setTag("Inventory", itemList);
	}

	// This returns the inventory's name
	@Override
	public String getInvName(){
		return "TileEntityTutorial";
	}
}