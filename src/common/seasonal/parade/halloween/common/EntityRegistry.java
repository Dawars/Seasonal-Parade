package seasonal.parade.halloween.common;

import seasonal.parade.halloween.client.EntityEvilSnowman;
import net.minecraft.src.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class EntityRegistry{
	public static void registerEntities(){
		cpw.mods.fml.common.registry.EntityRegistry.registerGlobalEntityID(EntityEvilSnowman.class, "Ashman", cpw.mods.fml.common.registry.EntityRegistry.findGlobalUniqueEntityId(), 0x45454B, 0x62D420);
		cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntityEvilSnowman.class, "Ashman", 0, Halloween.instance, 64, 1, true);

		LanguageRegistry.instance().addStringLocalization("entity.Ashman.name", "en_US", "Ashman");

//	    //Localize mob name
//	    //Add mob spawn
////	    ModLoader.addSpawn(EntityRedDragon.class, 2, 1, 2, EnumCreatureType.monster, BiomeGenBase.plains);
////	    ModLoader.addSpawn(EntityRedDragon.class, 5, 1, 2, EnumCreatureType.monster, BiomeGenBase.extremeHills);
	}
}