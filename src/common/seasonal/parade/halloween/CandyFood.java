package seasonal.parade.halloween;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.PotionHelper;

public class CandyFood extends ItemFood{

	public CandyFood(int id, int heal, float last) {
		super(id, heal, last, false);
		this.setTextureFile(DefaultProps.TEXTURE_ITEMS);
		this.setAlwaysEdible();
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemStack)
	{
		return true;
//		return itemStack.getItem().shiftedIndex == Halloween.candyPumpkin.shiftedIndex;
	}

}
