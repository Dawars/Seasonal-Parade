package seasonal.parade.halloween;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import seasonal.parade.halloween.render.EntityHeadless;
import seasonal.parade.halloween.render.EntityWitch;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.ColorizerFoliage;
import net.minecraft.src.ColorizerGrass;
import net.minecraft.src.SpawnListEntry;

public class BiomeGenHalloween extends BiomeGenBase
{
	protected BiomeGenHalloween(int par1)
	{
		super(par1);
		spawnableCreatureList.clear();
        
		this.topBlock = (byte)Block.grass.blockID;
		this.fillerBlock = (byte)Block.dirt.blockID;
        
		this.setBiomeName("Halloween Land");
		this.setColor(0x631BE0);
		this.canSpawnLightningBolt();
        
        
//        waterColorMultiplier = 0xff00c6;
		waterColorMultiplier = 0x67E01B;
        
		this.theBiomeDecorator = new BiomeHalloweenDecorator(this);
    	
	}
	public int getBiomeGrassColor()
	{
		return 0x392D54;
	}

	/**
	 * Provides the basic foliage color based on the biome temperature and rainfall
	 */
	public int getBiomeFoliageColor()
	{
		double var1 = (double)this.getFloatTemperature();
		double var3 = (double)this.getFloatRainfall();
		return ((ColorizerFoliage.getFoliageColor(var1, var3) & 0x631BE0) + 0xBFFF00) / 2;
    }
    
}
