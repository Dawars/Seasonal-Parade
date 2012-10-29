package seasonal.parade.halloween.gui;

import java.util.Iterator;

import buildcraft.api.liquids.LiquidManager;
import buildcraft.api.liquids.LiquidStack;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import seasonal.parade.halloween.LiquidHelper;
import seasonal.parade.halloween.LiquidStacks;
import seasonal.parade.halloween.SlotClosed;
import seasonal.parade.halloween.TileMixer;
import net.minecraft.src.*;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class ContainerMixer extends HalloweenContainer{
	protected TileMixer tile;
	
	private int tankMilkAmount = 0;
	private int tankMilkId = 0;
	
	private int tankCandyAmount = 0;
	private int tankCandyId = 0;
	
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
	
	public void addCraftingToCrafters(ICrafting iCrafting){
		super.addCraftingToCrafters(iCrafting);
		
		if(this.tile.tankMilk.getLiquid() != null){
			iCrafting.updateCraftingInventoryInfo(this, 0, this.tile.tankMilk.getLiquid().amount);
		}
		else {
			iCrafting.updateCraftingInventoryInfo(this, 0, 0);
    	}

		if(this.tile.tankMilk.getLiquid() != null){
        	iCrafting.updateCraftingInventoryInfo(this, 1, this.tile.tankMilk.getLiquid().itemID);
		} else {
			iCrafting.updateCraftingInventoryInfo(this, 1, 0);
		}
	        
		if(this.tile.tankCandy.getLiquid() != null){
			iCrafting.updateCraftingInventoryInfo(this, 2, this.tile.tankCandy.getLiquid().amount);
		}
		else {
			iCrafting.updateCraftingInventoryInfo(this, 2, 0);
    	}
		
		if(this.tile.tankCandy.getLiquid() != null){
			iCrafting.updateCraftingInventoryInfo(this, 3, this.tile.tankCandy.getLiquid().itemID);
		} else {
			iCrafting.updateCraftingInventoryInfo(this, 3, 0);
		}
		
		if(this.tile.isRunning)
			iCrafting.updateCraftingInventoryInfo(this, 4, 1);
		else
			iCrafting.updateCraftingInventoryInfo(this, 4, 0);

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

			if (this.tile.tankMilk.getLiquid() != null && this.tankMilkAmount != this.tile.tankMilk.getLiquid().amount)
			{
				iCrafting.updateCraftingInventoryInfo(this, 0, this.tile.tankMilk.getLiquid().amount);
			}
			else if(this.tile.tankMilk.getLiquid() == null){
				iCrafting.updateCraftingInventoryInfo(this, 0, 0);
			}

			if (this.tile.tankMilk.getLiquid() != null && this.tankMilkId != this.tile.tankMilk.getLiquid().itemID)
			{
				iCrafting.updateCraftingInventoryInfo(this, 1, this.tile.tankMilk.getLiquid().itemID);
			}
	            
			if (this.tile.tankCandy.getLiquid() != null && this.tankCandyAmount != this.tile.tankCandy.getLiquid().amount)
			{
				iCrafting.updateCraftingInventoryInfo(this, 2, this.tile.tankCandy.getLiquid().amount);
			}
			else if(this.tile.tankCandy.getLiquid() == null){
				iCrafting.updateCraftingInventoryInfo(this, 2, 0);
			}

			if (this.tile.tankCandy.getLiquid() != null && this.tankCandyId != this.tile.tankCandy.getLiquid().itemID)
			{
				iCrafting.updateCraftingInventoryInfo(this, 3, this.tile.tankCandy.getLiquid().itemID);
			}
			
			if(this.isRunning != this.tile.isRunning()){
				if(this.tile.isRunning)
					iCrafting.updateCraftingInventoryInfo(this, 4, 1);
				else
					iCrafting.updateCraftingInventoryInfo(this, 4, 0);
			}
        }
		
		if(this.tile.tankMilk.getLiquid() != null){
	        this.tankMilkAmount = this.tile.tankMilk.getLiquid().amount;
	        this.tankMilkId = this.tile.tankMilk.getLiquid().itemID;
		}
		
		if(this.tile.tankCandy.getLiquid() != null){
	        this.tankCandyAmount = this.tile.tankCandy.getLiquid().amount;
	        this.tankCandyId = this.tile.tankCandy.getLiquid().itemID;
		}
        this.isRunning = this.tile.isRunning;

	}

	    @SideOnly(Side.CLIENT)
	    public void updateProgressBar(int id, int data)
	    {
	        switch(id)
	        {
	        	//Milk
	        
		        case 0:
		        	if(this.tile.tankMilk.getLiquid() != null){
		        		this.tile.tankMilk.getLiquid().amount = data;
		        	} else {
		        		this.tile.tankMilk.setLiquid(new LiquidStack(0, data));
		        	}
		        break;
		        case 1:
		        	if(this.tile.tankMilk.getLiquid() != null){
		        		this.tile.tankMilk.getLiquid().itemID = data;
		        	} else {
		        		this.tile.tankMilk.setLiquid(new LiquidStack(data, 0));
		        	}
	        	break;
	        	
	        	//Candy
	        	
		        case 2:
		        	if(this.tile.tankCandy.getLiquid() != null){
		        		this.tile.tankCandy.getLiquid().amount = data;
		        	} else {
		        		this.tile.tankCandy.setLiquid(new LiquidStack(0, data));
		        	}
	        	break;
		        case 3:
		        	if(this.tile.tankCandy.getLiquid() != null){
		        		this.tile.tankCandy.getLiquid().itemID = data;
		        	} else {
		        		this.tile.tankCandy.setLiquid(new LiquidStack(data, 0));
		        	}
	        	break;
		        case 4:
		        	if(data == 1)
		        		this.tile.isRunning = true;
		        	else
		        		this.tile.isRunning = false;
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

            if (slotID <=6)
            {
                if (!this.mergeItemStack(stack, 7, 43, true))
                {
                    return null;
                }

                var3.onSlotChange(stack, var2);
            } else {
            	if(LiquidHelper.contains(TileMixer.ingredients, stack.getItem())){//if ingredients
	            	if (!this.mergeItemStack(stack, 0, 4, false))
	                {
	                    return null;
	                }
	            	var3.onSlotChange(stack, var2);
            	} else if(LiquidHelper.contains(LiquidHelper.emptyContainers, stack.getItem())){//if empty liquid containers
            		if (!this.mergeItemStack(stack, 5, 6, false))
	                {
	                    return null;
	                }
	            	var3.onSlotChange(stack, var2);
            	} else if(LiquidHelper.contains(LiquidHelper.milkContainers, stack.getItem())){ //filled liquid container
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

            var3.func_82870_a(par1EntityPlayer, stack);
        }

        return var2;
    }
}