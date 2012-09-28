/**
 * Copyright (c) SpaceToad, 2011
 * http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package seasonal.parade.halloween.common;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class HalloweenConfiguration extends Configuration {

	public HalloweenConfiguration(File file) {
		super(file);
	}

	@Override
	public void save() {
		Property versionProp = null;

		if (!generalProperties.containsKey("version")) {
			versionProp = new Property();
			versionProp.setName("version");
			generalProperties.put("version", versionProp);
		} else
			versionProp = generalProperties.get("version");

		versionProp.value = DefaultProps.VERSION;

		super.save();
	}

}