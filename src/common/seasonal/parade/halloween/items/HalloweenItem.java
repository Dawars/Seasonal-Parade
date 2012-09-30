package seasonal.parade.halloween.items;

import seasonal.parade.halloween.common.DefaultProps;
import net.minecraft.src.Item;

public class HalloweenItem extends Item{

	public HalloweenItem(int par1) {
		super(par1);
	}

	public String getTextureFile(){
		return DefaultProps.TEXTURE_ITEMS;
	}
}
