package seasonal.parade.halloween;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.src.*;

public class RecipeRegistry{
	public static void registerRecipes(){
		GameRegistry.addRecipe(new ItemStack(Halloween.blockAsh, 1), new Object[]{
			"AA", "AA", Character.valueOf('A'), Halloween.ashItem
		});
		
//		
//		GameRegistry.addSmelting(AitS.hematiteOre.blockID, new ItemStack(Item.ingotIron, 1), 0F);
		
	}
}