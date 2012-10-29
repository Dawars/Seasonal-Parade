package seasonal.parade.halloween.gui;

import java.util.Iterator;

import buildcraft.api.liquids.LiquidManager;
import buildcraft.api.liquids.LiquidStack;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import seasonal.parade.halloween.LiquidHelper;
import seasonal.parade.halloween.LiquidStacks;
import seasonal.parade.halloween.SlotClosed;
import seasonal.parade.halloween.TileCandyMaker;
import seasonal.parade.halloween.TileMixer;
import seasonal.parade.halloween.gui.slots.SlotPaper;
import net.minecraft.src.*;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class ContainerCandyMaker extends HalloweenContainer{
	protected TileCandyMaker tile;

	private int tankCandyAmount = 0;
	private int tankCandyId = 0;	
    private int lastCookTime = 0;

	public ContainerCandyMaker(InventoryPlayer player_inventory, TileCandyMaker tile) {
		super(tile.getSizeInventory());
		this.tile = tile;

		
		//Input
		addSlotToContainer(new Slot(tile, 0, 23, 15));
		addSlotToContainer(new Slot(tile, 1, 41, 15));
		addSlotToContainer(new Slot(tile, 2, 23, 33));
		addSlotToContainer(new Slot(tile, 3, 41, 33));
		
		
		//Liquid input
		addSlotToContainer(new Slot(tile, 4, 32, 55));

		//Paper
		addSlotToContainer(new SlotPaper(tile, 5, 95, 30));

		//Proccess

		//Candy 
		addSlotToContainer(new SlotClosed(tile, 6, 123, 26));
		addSlotToContainer(new SlotClosed(tile, 7, 141, 26));
		addSlotToContainer(new SlotClosed(tile, 8, 123, 44));
		addSlotToContainer(new SlotClosed(tile, 9, 141, 44));

		
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
	
	public void addCraftingToCrafters(ICrafting iCrafting){
		super.addCraftingToCrafters(iCrafting);
	        
		if(this.tile.tankCandy.getLiquid() != null){
			iCrafting.updateCraftingInventoryInfo(this, 0, this.tile.tankCandy.getLiquid().amount);
		}
		else {
			iCrafting.updateCraftingInventoryInfo(this, 0, 0);
    	}
		
		if(this.tile.tankCandy.getLiquid() != null){
			iCrafting.updateCraftingInventoryInfo(this, 1, this.tile.tankCandy.getLiquid().itemID);
		} else {
			iCrafting.updateCraftingInventoryInfo(this, 1, 0);
		}
        iCrafting.updateCraftingInventoryInfo(this, 2, this.tile.cookTime);

		
    }
	 
	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	public void updateCraftingResults(){
		super.updateCraftingResults();
		Iterator var1 = this.crafters.iterator();

		while (var1.hasNext())
		{
			ICrafting iCrafting = (ICrafting)var1.next();

			if (this.tile.tankCandy.getLiquid() != null && this.tankCandyAmount != this.tile.tankCandy.getLiquid().amount)
			{
				iCrafting.updateCraftingInventoryInfo(this, 0, this.tile.tankCandy.getLiquid().amount);
			}
			else if(this.tile.tankCandy.getLiquid() == null){
				iCrafting.updateCraftingInventoryInfo(this, 0, 0);
			}

			if (this.tile.tankCandy.getLiquid() != null && this.tankCandyId != this.tile.tankCandy.getLiquid().itemID)
			{
				iCrafting.updateCraftingInventoryInfo(this, 1, this.tile.tankCandy.getLiquid().itemID);
			}
			
			if (this.lastCookTime != this.tile.cookTime)
            {
				iCrafting.updateCraftingInventoryInfo(this, 2, this.tile.cookTime);
            }
        }
		
		if(this.tile.tankCandy.getLiquid() != null){
	        this.tankCandyAmount = this.tile.tankCandy.getLiquid().amount;
	        this.tankCandyId = this.tile.tankCandy.getLiquid().itemID;
		}
		this.lastCookTime = this.tile.cookTime;
	}

		@SideOnly(Side.CLIENT)
		public void updateProgressBar(int id, int data)
		{
			switch(id)
			{
				case 0:
					if(this.tile.tankCandy.getLiquid() != null){
						this.tile.tankCandy.getLiquid().amount = data;
					} else {
		        		this.tile.tankCandy.setLiquid(new LiquidStack(0, data));
		        	}
	        	break;
		        case 1:
		        	if(this.tile.tankCandy.getLiquid() != null){
		        		this.tile.tankCandy.getLiquid().itemID = data;
		        	} else {
		        		this.tile.tankCandy.setLiquid(new LiquidStack(data, 0));
		        	}
	        	break;
		        case 2:
		        	this.tile.cookTime = data;
	        	break;
	        }
	    }
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}
		
	/**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
	public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int slotID)
    {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.inventorySlots.get(slotID);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack stack = var3.getStack();
            var2 = stack.copy();

            if (slotID <=9)
            {
                if (!this.mergeItemStack(stack, 7, 43, true))
                {
                    return null;
                }

                var3.onSlotChange(stack, var2);
            } else {
            	if(stack.getItem() == Item.paper){//if empty liquid containers
            		if (!this.mergeItemStack(stack, 5, 6, false))
	                {
	                    return null;
	                }
	            	var3.onSlotChange(stack, var2);
            	} else if(LiquidHelper.contains(LiquidHelper.candyContainers, stack.getItem())){ //filled liquid container
            		if (!this.mergeItemStack(stack, 4, 5, false))
	                {
	                    return null;
	                }
	            	var3.onSlotChange(stack, var2);
            	} else {
            		if (!this.mergeItemStack(stack, 0, 4, false))
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

            var3.func_82870_a(par1EntityPlayer, stack);
        }

        return var2;
    }
}