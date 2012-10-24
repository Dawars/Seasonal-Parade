package seasonal.parade.halloween.gui;

import java.util.Iterator;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import seasonal.parade.halloween.SlotClosed;
import seasonal.parade.halloween.TileMixer;
import net.minecraft.src.*;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class ContainerMixer extends HalloweenContainer{
	protected TileMixer tile;
	protected static boolean isRunning = false;
	
	
	public ContainerMixer(InventoryPlayer player_inventory, TileMixer tile) {
		super(tile.getSizeInventory());
		this.tile = tile;

		
		//Input
		addSlotToContainer(new Slot(tile, 0, 14, 15));
		addSlotToContainer(new Slot(tile, 1, 32, 15));
		addSlotToContainer(new Slot(tile, 2, 14, 33));
		addSlotToContainer(new Slot(tile, 3, 32, 33));
		//Liquid input
		
		
		addSlotToContainer(new Slot(tile, 4, 23, 54));

		//Proccess

		//Liquid 
		addSlotToContainer(new Slot(tile, 5, 143, 21));//Empty Containers
		addSlotToContainer(new SlotClosed(tile, 6, 143, 56));

		
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
	
	 public void addCraftingToCrafters(ICrafting par1ICrafting)
	    {
	        super.addCraftingToCrafters(par1ICrafting);
	        if(this.tile.tankMilk.getLiquid() != null){
		        par1ICrafting.updateCraftingInventoryInfo(this, 0, this.tile.tankMilk.getLiquid().amount);
	        }
	        if(this.tile.tankCandy.getLiquid() != null){
	        	par1ICrafting.updateCraftingInventoryInfo(this, 1, this.tile.tankCandy.getLiquid().amount);
	        }
//	        if(this.tile.isRunning)
//	        	par1ICrafting.updateCraftingInventoryInfo(this, 2, 1);
//	        else
//	        	par1ICrafting.updateCraftingInventoryInfo(this, 2, 0);
	    }


	    @SideOnly(Side.CLIENT)
	    public void updateProgressBar(int par1, int par2)
	    {
	        if (par1 == 0)
	        {
	        	if(this.tile.tankMilk.getLiquid() != null)
	        		this.tile.tankMilk.getLiquid().amount = par2;
	        }

	        if (par1 == 1)
	        {
	        	if(this.tile.tankCandy.getLiquid() != null)
	        		this.tile.tankCandy.getLiquid().amount = par2;//null point exception
	        }

//	        if (par1 == 2)
//	        {
//	        	if(par2 == 1)
//	        		this.tile.isRunning = true;
//	        	else
//	        		this.tile.isRunning = false;
//	        }
	    }
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
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

            if (slotID <=6)
            {
                if (!this.mergeItemStack(stack, 7, 43, true))
                {
                    return null;
                }

                var3.onSlotChange(stack, var2);
            } else {
            	if(TileMixer.contains(TileMixer.ingredients, stack.getItem())){//if ingredients
	            	if (!this.mergeItemStack(stack, 0, 4, false))
	                {
	                    return null;
	                }
	            	var3.onSlotChange(stack, var2);
            	} else if(TileMixer.contains(TileMixer.emptyContainers, stack.getItem())){//if empty liquid containers
            		if (!this.mergeItemStack(stack, 5, 6, false))
	                {
	                    return null;
	                }
	            	var3.onSlotChange(stack, var2);
            	} else if(TileMixer.contains(TileMixer.milkContainers, stack.getItem())){ //filled liquid container
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