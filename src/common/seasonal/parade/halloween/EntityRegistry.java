package seasonal.parade.halloween;

import seasonal.parade.halloween.render.EntityAshman;
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

		cpw.mods.fml.common.registry.EntityRegistry.registerGlobalEntityID(EntityHeadless.class, "Headless Horseman", cpw.mods.fml.common.registry.EntityRegistry.findGlobalUniqueEntityId(), 0xE28D1C, 0xA1A1A1);
		cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntityHeadless.class, "Headless Horseman", 1, Halloween.instance, 64, 1, true);

		cpw.mods.fml.common.registry.EntityRegistry.registerGlobalEntityID(EntityWitch.class, "Witch", cpw.mods.fml.common.registry.EntityRegistry.findGlobalUniqueEntityId(), 0xE28D2C, 0xA1A1A1);
		cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntityWitch.class, "Witch", 2, Halloween.instance, 64, 1, true);


//	    //Localize mob name
//	    //Add mob spawn
////	    ModLoader.addSpawn(EntityRedDragon.class, 2, 1, 2, EnumCreatureType.monster, BiomeGenBase.plains);
////	    ModLoader.addSpawn(EntityRedDragon.class, 5, 1, 2, EnumCreatureType.monster, BiomeGenBase.extremeHills);
	}
}