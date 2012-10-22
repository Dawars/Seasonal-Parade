package seasonal.parade.halloween;

import buildcraft.api.liquids.LiquidData;
import buildcraft.api.liquids.LiquidDictionary;
import buildcraft.api.liquids.LiquidManager;
import buildcraft.api.liquids.LiquidStack;

public class LiquidStacks {
	public static LiquidStack milk = LiquidDictionary.getOrCreateLiquid("Milk", new LiquidStack(Halloween.milk.shiftedIndex, LiquidManager.BUCKET_VOLUME));
	public static LiquidStack rawCandy = LiquidDictionary.getOrCreateLiquid("Raw Candy", new LiquidStack(Halloween.rawCandy.shiftedIndex, LiquidManager.BUCKET_VOLUME));

}
