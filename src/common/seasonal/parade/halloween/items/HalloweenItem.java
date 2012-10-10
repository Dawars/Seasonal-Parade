package seasonal.parade.halloween.items;

import seasonal.parade.halloween.common.DefaultProps;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class HalloweenItem extends Item{

	public HalloweenItem(int par1) {
		super(par1);
		setTextureFile(DefaultProps.TEXTURE_ITEMS);
	}
}
