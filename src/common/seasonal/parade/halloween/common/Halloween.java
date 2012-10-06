package seasonal.parade.halloween.common;

import java.io.File;
import java.util.logging.Logger;

import seasonal.parade.halloween.blocks.BlockAsh;
import seasonal.parade.halloween.blocks.BlockAshBlock;
import seasonal.parade.halloween.blocks.EvilPumpkin;
import seasonal.parade.halloween.client.ClientPacketHandler;
import seasonal.parade.halloween.client.EntityAshman;
import seasonal.parade.halloween.items.HalloweenItem;
import seasonal.parade.halloween.machines.Mixer;
import seasonal.parade.halloween.machines.TileMixer;
import seasonal.parade.halloween.server.ServerPacketHandler;

import net.minecraft.src.Block;
import net.minecraft.src.BlockPumpkin;
import net.minecraft.src.BlockSnow;
import net.minecraft.src.BlockSnowBlock;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.src.*;
import net.minecraftforge.common.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarted;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.NetworkMod.*;
import cpw.mods.fml.common.registry.*;
//block ids 3200-3500

@Mod(name="Halloween", version="1.0", useMetadata = false, modid = "SeasonalParade|Halloween")
@NetworkMod(clientSideRequired = true, serverSideRequired = true, clientPacketHandlerSpec =
@SidedPacketHandler(channels = { "halloweenMain" }, packetHandler = ClientPacketHandler.class), serverPacketHandlerSpec =
@SidedPacketHandler(channels = { "halloweenMain" }, packetHandler = ServerPacketHandler.class))
public class Halloween {
	@Instance
	// The Halloween Instance
	public static Halloween instance = new Halloween();

	// The Handler For Opening Guis
	private GuiHandler guiHandler = new GuiHandler();

	// Instance For Creating The Config File
	public static Configuration easterConfig;

	// Proxy
	@SidedProxy(clientSide = "seasonal.parade.halloween.client.ClientProxy", serverSide = "seasonal.parade.halloween.common.CommonProxy")
	public static CommonProxy proxy;


	public static int candyModel = 35;

	
	@PreInit
	public void load(FMLPreInitializationEvent evt) {
		
		
		proxy.registerRenderThings();
		
		GameRegistry.registerTileEntity(TileMixer.class, "Mixer");
		
		NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
		
		GameRegistry.registerWorldGenerator(new HalloweenWorldGenerator());
		
		
		
		RegisterBlocks(new Block[] {
				evilPumpkin, evilLantern, ash, blockAsh, Mixer
		});
		
	

		// Register Names
		// -------------------------------------------------------------------
		// Block Names
		LanguageRegistry.addName(evilPumpkin, "Evil Pumpkin");
		LanguageRegistry.addName(evilLantern, "Evil 'o' Lantern");
		LanguageRegistry.addName(ash, "Ash");
		LanguageRegistry.addName(blockAsh, "Ash Block");
//		LanguageRegistry.addName(candyBasket, "Candy Basket");
		LanguageRegistry.addName(Mixer, "Mixer");
		
		// Item Names
		LanguageRegistry.addName(ashItem, "Ash");
		
		// Containers - Mobs
		LanguageRegistry.instance().addStringLocalization("container.candybasket", "en_US", "Candy Basket");
		LanguageRegistry.instance().addStringLocalization("container.mixer", "en_US", "Mixer");

		// --------------------------------------------------------------------
		RecipeRegistry.registerRecipes();
		EntityRegistry.registerEntities();
		
		
		MinecraftForge.setBlockHarvestLevel(ash, "shovel", 0);
	 	MinecraftForge.setBlockHarvestLevel(blockAsh, "shovel", 0);
	 	
		

	}

	// Mods-Loaded Method
	@PostInit
	public void modsLoaded(FMLPostInitializationEvent evt){
//		Halloween.easterConfig.save();
	}

	// Server Started Method
	@ServerStarted
	public void serverStarted(FMLServerStartedEvent event) {

	}

	// Block Registry
    public static Block evilPumpkin = (new EvilPumpkin(DefaultProps.EVIL_PUMPKIN_BLOCK_ID, 0, false)).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setBlockName("evilPumpkin");
    public static Block evilLantern = (new EvilPumpkin(DefaultProps.EVIL_LANTERN_BLOCK_ID, 0, true)).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setLightValue(1.0F).setBlockName("evilLantern");
    public static Block ash = (new BlockAsh(DefaultProps.ASH_LAYER_ID, 6)).setHardness(0.1F).setStepSound(Block.soundClothFootstep).setBlockName("ash").setLightOpacity(0);
    public static Block blockAsh = (new BlockAshBlock(DefaultProps.ASH_BLOCK_ID, 6)).setHardness(0.2F).setStepSound(Block.soundClothFootstep).setBlockName("ashBlock");
//    public static Block candyBasket = (new CandyBasket(DefaultProps.CANDY_BASKET_ID, Material.pumpkin)).setHardness(0.2F).setStepSound(Block.soundWoodFootstep).setBlockName("candyBasket");
	public static Block Mixer = new Mixer(DefaultProps.MIXER_BLOCK_ID, 1).setHardness(3F).setResistance(5F).setLightValue(.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabs.tabBlock).setBlockName("mixer");

    
    
	public void RegisterBlocks(Block ablock[])
    {
        Block ablock1[] = ablock;
        int i = ablock1.length;
        for(int j = 0; j < i; j++)
        {
            Block block = ablock1[j];
            GameRegistry.registerBlock(block);
        }

    }

	// Item Registry
	public static Item ashItem = (new HalloweenItem(DefaultProps.ASH_ITEM_ID)).setIconIndex(0).setMaxStackSize(16).setTabToDisplayOn(CreativeTabs.tabDeco).setItemName("ashItem");
}