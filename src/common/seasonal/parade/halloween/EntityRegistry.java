package seasonal.parade.halloween;

import seasonal.parade.halloween.render.EntityAshman;
import seasonal.parade.halloween.render.EntityEvilPumpkin;
import seasonal.parade.halloween.render.EntityHeadless;
import seasonal.parade.halloween.render.EntityWitch;
import seasonal.parade.halloween.render.ModelWitch;
import net.minecraft.src.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class EntityRegistry{
	public static void registerEntities(){
		cpw.mods.fml.common.registry.EntityRegistry.registerGlobalEntityID(EntityAshman.class, "Ashman", cpw.mods.fml.common.registry.EntityRegistry.findGlobalUniqueEntityId(), 0x45454B, 0x62D420);
		cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntityAshman.class, "Ashman", 0, Halloween.instance, 64, 1, true);

		cpw.mods.fml.common.registry.EntityRegistry.registerGlobalEntityID(EntityHeadless.class, "Headless", cpw.mods.fml.common.registry.EntityRegistry.findGlobalUniqueEntityId(), 0x000000, 0xE28D1C);
		cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntityHeadless.class, "Headless", 1, Halloween.instance, 64, 1, true);

		cpw.mods.fml.common.registry.EntityRegistry.registerGlobalEntityID(EntityWitch.class, "Witch", cpw.mods.fml.common.registry.EntityRegistry.findGlobalUniqueEntityId(), 0xA3B372, 0x463A52);
		cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntityWitch.class, "Witch", 2, Halloween.instance, 64, 1, true);

//		cpw.mods.fml.common.registry.EntityRegistry.registerGlobalEntityID(EntityEvilPumpkin.class, "EvilPumpkin", cpw.mods.fml.common.registry.EntityRegistry.findGlobalUniqueEntityId(), 0xE28D20, 0xA141A1);
//		cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntityEvilPumpkin.class, "EvilPumpkin", 3, Halloween.instance, 64, 1, true);


//	    //Localize mob name
//	    //Add mob spawn
	    ModLoader.addSpawn(EntityWitch.class, 10, 1, 2, EnumCreatureType.creature, Halloween.Halloween);
	    ModLoader.addSpawn(EntityHeadless.class, 10, 4, 5, EnumCreatureType.monster, Halloween.Halloween);
	}
}