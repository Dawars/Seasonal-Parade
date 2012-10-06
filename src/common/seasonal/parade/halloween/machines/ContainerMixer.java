package seasonal.parade.halloween.machines;

import net.minecraft.src.*;

// Create a class and extend Container
public class ContainerMixer extends Container{
	// This is a declaration of the TileTutorial Class
	protected TileMixer tile_entity;
	
	// This is the super constructor and has 2 params
	// @param TileTutorial, this is the TileTutorial declaration
	// @param InventoryPlayer, this is the players inventory
	public ContainerMixer(TileMixer tile_entity, InventoryPlayer player_inventory){
		// When you start to make the super constructor, you need to declare the TileTutorial, and this is how you actually do it
		this.tile_entity = tile_entity;
		
		//Input
		addSlotToContainer(new Slot(tile_entity, 0, 14, 15));
		addSlotToContainer(new Slot(tile_entity, 1, 14, 33));
		addSlotToContainer(new Slot(tile_entity, 2, 32, 15));
		addSlotToContainer(new Slot(tile_entity, 3, 32, 33));
		
		//Liquid input
		
		addSlotToContainer(new Slot(tile_entity, 4, 23, 54));

		//Proccess
		addSlotToContainer(new SlotClosed(tile_entity, 5, 90, 43));

		//Liquid 
		addSlotToContainer(new Slot(tile_entity, 6, 153, 18));
		addSlotToContainer(new Slot(tile_entity, 7, 153, 54));

		
		int i;
		
		for (i = 0; i < 3; ++i)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(player_inventory, var4 + i * 9 + 9, 8 + var4 * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player_inventory, i, 8 + i * 18, 142));
        }
	}
	
	// This is required and returns if the player can use this Gui
	// It has 1 param
	// @param EntityPlayer, this is the player declaration
	@Override
	public boolean canInteractWith(EntityPlayer player){
		return tile_entity.isUseableByPlayer(player);
	}
	

	
	// This is not required, and is optional,
	// If you want this code, that all it does is just when you right click, it snaps the stack you chose to the next open slot
	// In the players inventory
	// It has 1 param
	// @param int slot_index, this is the slot that the player chose
	@Override
	public ItemStack transferStackInSlot(int slot_index){
		ItemStack stack = null;
		Slot slot_object = (Slot) inventorySlots.get(slot_index);
		
		if(slot_object != null && slot_object.getHasStack()){
			ItemStack stack_in_slot = slot_object.getStack();
			stack = stack_in_slot.copy();
			
			if(slot_index == 0){
				if(!mergeItemStack(stack_in_slot, 1, inventorySlots.size(), true)){
					return null;
				}
			} else if(!mergeItemStack(stack_in_slot, 0, 1, false)){
				return null;
			}
			
			if(stack_in_slot.stackSize == 0){
				slot_object.putStack(null);
			} else{
				slot_object.onSlotChanged();
			}
		}
		
		return stack;
	}
}