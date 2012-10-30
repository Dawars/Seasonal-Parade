package seasonal.parade.halloween;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import buildcraft.api.liquids.LiquidData;
import buildcraft.api.liquids.LiquidDictionary;
import buildcraft.api.liquids.LiquidManager;
import buildcraft.api.liquids.LiquidStack;

public class LiquidStacks {
	public static LiquidStack milk = LiquidDictionary.getOrCreateLiquid("Milk", new LiquidStack(Halloween.milk.shiftedIndex, LiquidManager.BUCKET_VOLUME));
	public static LiquidStack rawCandy = new LiquidStack(Halloween.rawCandy, LiquidManager.BUCKET_VOLUME);
}
