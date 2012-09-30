package seasonal.parade.halloween.common;

import java.util.Random;
import net.minecraft.src.*;
import cpw.mods.fml.common.IWorldGenerator;

public class HalloweenWorldGenerator implements IWorldGenerator{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
		switch(world.provider.dimensionId){
			case -1:
				generateNether(world, random, chunkX*16, chunkZ*16);
			break;
			case 0:
				generateSurface(world, random, chunkX*16, chunkZ*16);
			break;
			case 1:
				generateEnd(world, random, chunkX*16, chunkZ*16);
			break;
		}

	}

	public void generateSurface(World world, Random random, int blockX, int blockZ){
//			if(getBiomeAt(x, z, world) instanceof BiomeGenBeach ||
//			   getBiomeAt(x, z, world) instanceof BiomeGenOcean){
//				(new WorldGenMinable(AitS.haliteOre.blockID, 10 + random.nextInt(6)))
//					.generate(world, random, x, random.nextInt(55) + 1, z);
//			}
//		}
	}


	public void generateNether(World world, Random random, int blockX, int blockZ){}
	public void generateEnd(World world, Random random, int blockX, int blockZ){}

	public static BiomeGenBase getBiomeAt(int posX, int posY, World world){
		return world.getBiomeGenForCoords(posX, posY);
	}
}