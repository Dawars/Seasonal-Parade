package seasonal.parade.halloween.gui.slots;

import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotPaper extends Slot {

	public SlotPaper(IInventory tile, int id, int x, int y) {
		super(tile, id, x, y);
	}

	/**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() == Item.paper;
    }
}
