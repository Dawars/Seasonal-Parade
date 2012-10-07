package seasonal.parade.halloween.machines;

import net.minecraft.src.*;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

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
		addSlotToContainer(new Slot(tile_entity, 1, 32, 15));
		addSlotToContainer(new Slot(tile_entity, 2, 14, 33));
		addSlotToContainer(new Slot(tile_entity, 3, 32, 33));
		//Liquid input
		
		
		addSlotToContainer(new Slot(tile_entity, 4, 23, 54));

		//Proccess
		addSlotToContainer(new SlotClosed(tile_entity, 5, 90, 43));

		//Liquid 
		addSlotToContainer(new Slot(tile_entity, 6, 153, 18));
		addSlotToContainer(new SlotClosed(tile_entity, 7, 153, 54));

		
		int i;
		
		for (i = 0; i < 3; ++i)
        {
            for (int stack = 0; stack < 9; ++stack)
            {
                this.addSlotToContainer(new Slot(player_inventory, stack + i * 9 + 9, 8 + stack * 18, 84 + i * 18));
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
	

	
	/**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int slotID)
    {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.inventorySlots.get(slotID);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack stack = var3.getStack();
            var2 = stack.copy();

            if (slotID <=7)
            {
                if (!this.mergeItemStack(stack, 3+5, 39+5, true))
                {
                    return null;
                }

                var3.onSlotChange(stack, var2);
            } else {
            	if(stack.itemID == Item.sugar.shiftedIndex || stack.itemID == Item.slimeBall.shiftedIndex){//if ingredients
	            	if (!this.mergeItemStack(stack, 0, 4, false))
	                {
	                    return null;
	                }
	            	var3.onSlotChange(stack, var2);
            	} else if(stack.itemID == Item.bucketEmpty.shiftedIndex){//if empty bucket (later add capsules and cells...)
            		if (!this.mergeItemStack(stack, 6, 7, false))
	                {
	                    return null;
	                }
	            	var3.onSlotChange(stack, var2);
            	} else if(stack.itemID == Item.bucketMilk.shiftedIndex){ //filled liquid container
            		if (!this.mergeItemStack(stack, 4, 5, false))
	                {
	                    return null;
	                }
	            	var3.onSlotChange(stack, var2);
            	}
            }
           

            if (stack.stackSize == 0)
            {
                var3.putStack((ItemStack)null);
            }
            else
            {
                var3.onSlotChanged();
            }

            if (stack.stackSize == var2.stackSize)
            {
                return null;
            }

            var3.onPickupFromSlot(stack);
        }

        return var2;
    }
}