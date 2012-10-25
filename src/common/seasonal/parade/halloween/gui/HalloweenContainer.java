package seasonal.parade.halloween.gui;

import java.util.ArrayList;
import java.util.List;

import buildcraft.api.liquids.ILiquidTank;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.TileEntityDispenser;

public abstract class HalloweenContainer extends Container {

	private List gauges = new ArrayList();
	
	private int inventorySize;

	public HalloweenContainer(int inventorySize) {
		this.inventorySize = inventorySize;
	}

	public List getGauges()
	  {
	    return this.gauges;
	  }
	
	public void addGauge(ILiquidTank gauge)
	  {
	    this.gauges.add(gauge);
	  }
	
	@Override
	public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int i){
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < inventorySize) {
				if (!mergeItemStack(itemstack1, inventorySize, inventorySlots.size(), true))
					return null;
			} else if (!mergeItemStack(itemstack1, 0, inventorySize, false))
				return null;
			if (itemstack1.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();
		}
		return itemstack;
	}

	public int getInventorySize(){
		return inventorySize;
	}
}