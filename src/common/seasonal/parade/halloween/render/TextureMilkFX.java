/**
 * Copyright (c) SpaceToad, 2011
 * http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package seasonal.parade.halloween.render;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import seasonal.parade.halloween.Halloween;
@SideOnly(Side.CLIENT)
public class TextureMilkFX extends TextureLiquidsFX {

	public TextureMilkFX() {
		super(235, 255, 235, 255, 235, 255, Halloween.milk.getIconFromDamage(0), Halloween.milk.getTextureFile());
	}
}