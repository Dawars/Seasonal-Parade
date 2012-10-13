package seasonal.parade.halloween;

import forestry.api.core.ItemInterface;
import forestry.api.recipes.RecipeManagers;
import ic2.api.Items;

import java.io.File;
import java.util.TreeMap;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;

import buildcraft.api.liquids.LiquidData;
import buildcraft.api.liquids.LiquidManager;
import buildcraft.api.liquids.LiquidStack;

import seasonal.parade.halloween.network.PacketHandler;
import seasonal.parade.halloween.network.PacketUpdate;
import seasonal.parade.halloween.proxy.CoreProxy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(name="Halloween mod", version=Version.VERSION, useMetadata = false, modid = "SeasonalParade|Halloween")
//, dependencies="required-after:Forge@[4.1.4.285,)")
@NetworkMod(channels = {DefaultProps.NET_CHANNEL_NAME}, packetHandler = PacketHandler.class, clientSideRequired = true, serverSideRequired = true)
public class Halloween {

	public static boolean debugMode = false;
	public static boolean modifyWorld = false;
	public static boolean trackNetworkUsage = false;
	
	
	public static int updateFactor = 10;

	public static HalloweenConfiguration mainConfiguration;

	
	public static int basketModel;
	public static int mixerModel;
	
	@Instance
	public static Halloween instance;
	
	// The Handler For Opening Guis
	private GuiHandler guiHandler = new GuiHandler();
	
	//Mods loaded
	public static boolean modForestry = false;
	public static boolean modIC2 = false;

	// Block Registry
    public static Block evilPumpkin;
    public static Block evilLantern;
    public static Block ash;
    public static Block blockAsh;
//    public static Block candyBasket;
    public static Block Mixer;
    
 // Item Registry
 	public static Item ashItem;
 	public static Item rawCandy;
 	public static Item rawCandyBucket;
 	public static Item rawCandyCell;
 	public static Item rawCandyCan;
 	public static Item rawCandyWaxCapsule;
 	public static Item rawCandyRefractoryCapsule;
 	
 	//IC2
 	public static Item cell;
 	
 	//Forestry
 	public static Item canEmpty;
 	public static Item wax_capsule;
 	public static Item refractory_capsule;
	
	
	@PreInit
	public void loadConfiguration(FMLPreInitializationEvent evt) {

		Version.versionCheck();


		mainConfiguration = new HalloweenConfiguration(new File(evt.getModConfigurationDirectory(), "halloween/main.conf"));
		try
		{
			mainConfiguration.load();
			NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
			GameRegistry.registerTileEntity(TileMixer.class, "Mixer");

			
			Property trackNetwork = Halloween.mainConfiguration.getOrCreateBooleanProperty("trackNetworkUsage", Configuration.CATEGORY_GENERAL, false);
			trackNetworkUsage = trackNetwork.getBoolean(false);


			Property factor = Halloween.mainConfiguration.getOrCreateIntProperty("network.updateFactor", Configuration.CATEGORY_GENERAL, 10);
			factor.comment = "increasing this number will decrease network update frequency, useful for overloaded servers";
			updateFactor = factor.getInt(10);
			
			// Block Registry
			evilPumpkin = (new EvilPumpkin(DefaultProps.EVIL_PUMPKIN_BLOCK_ID, 0, false)).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setBlockName("evilPumpkin");
			evilLantern = (new EvilPumpkin(DefaultProps.EVIL_LANTERN_BLOCK_ID, 0, true)).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setLightValue(1.0F).setBlockName("evilLantern");
		    ash = (new BlockAsh(DefaultProps.ASH_LAYER_ID, 6)).setHardness(0.1F).setStepSound(Block.soundClothFootstep).setBlockName("ash").setLightOpacity(0);
		    blockAsh = (new BlockAshBlock(DefaultProps.ASH_BLOCK_ID, 6)).setHardness(0.2F).setStepSound(Block.soundClothFootstep).setBlockName("ashBlock");
//		    candyBasket = (new CandyBasket(DefaultProps.CANDY_BASKET_ID, Material.pumpkin)).setHardness(0.2F).setStepSound(Block.soundWoodFootstep).setBlockName("candyBasket");
			Mixer = new Mixer(DefaultProps.MIXER_BLOCK_ID, 1).setHardness(3F).setResistance(5F).setLightValue(.0F).setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabs.tabBlock).setBlockName("mixer");

			
			
		 // Item Registry
			ashItem = (new HalloweenItem(DefaultProps.ASH_ITEM_ID)).setIconIndex(1).setMaxStackSize(16).setTabToDisplayOn(CreativeTabs.tabDeco).setItemName("ashItem");
			rawCandy = new HalloweenItem(DefaultProps.RAW_CANDY_ID).setIconIndex(0).setTabToDisplayOn(CreativeTabs.tabMisc).setItemName("rawCandy");
			rawCandyBucket = new HalloweenItem(DefaultProps.RAW_CANDY_BUCKET_ID).setIconIndex(8).setMaxStackSize(1).setTabToDisplayOn(CreativeTabs.tabMisc).setItemName("rawCandyBucket");
			rawCandyCell = new HalloweenItem(DefaultProps.RAW_CANDY_CELL_ID).setIconIndex(7).setTabToDisplayOn(CreativeTabs.tabMisc).setItemName("rawCandyCell");
			rawCandyCan = new HalloweenItem(DefaultProps.RAW_CANDY_CAN_ID).setIconIndex(2).setTabToDisplayOn(CreativeTabs.tabMisc).setItemName("rawCandyCan");
			rawCandyWaxCapsule = new HalloweenItem(DefaultProps.RAW_CANDY_WAX_CAPSULE_ID).setIconIndex(3).setTabToDisplayOn(CreativeTabs.tabMisc).setItemName("rawCandyWaxCapsule");
			rawCandyRefractoryCapsule = new HalloweenItem(DefaultProps.RAW_CANDY_REFRACTORY_CAPSULE_ID).setIconIndex(6).setTabToDisplayOn(CreativeTabs.tabMisc).setItemName("rawCandyRefractoryCapsule");
			
			
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
//			LanguageRegistry.addName(candyBasket, "Candy Basket");
			LanguageRegistry.addName(Mixer, "Mixer");
			
			// Item Names
			LanguageRegistry.addName(ashItem, "Ash");
			LanguageRegistry.addName(rawCandy, "Raw Candy");
			LanguageRegistry.addName(rawCandyBucket, "Raw Candy Bucket");
			LanguageRegistry.addName(rawCandyCell, "Raw Candy Cell");
			LanguageRegistry.addName(rawCandyCan, "Raw Candy Can");
			LanguageRegistry.addName(rawCandyWaxCapsule, "Raw Candy Capsule");
			LanguageRegistry.addName(rawCandyRefractoryCapsule, "Raw Candy Capsule");
			
//			LanguageRegistry.addName(rawCandy, "Raw Candy");
//			LanguageRegistry.addName(rawCandy, "Raw Candy");
			
			// Containers - Mobs
			LanguageRegistry.instance().addStringLocalization("container.candybasket", "en_US", "Candy Basket");
			LanguageRegistry.instance().addStringLocalization("container.mixer", "en_US", "Mixer");
			

			LanguageRegistry.instance().addStringLocalization("entity.Ashman.name", "en_US", "Ashman");
			LanguageRegistry.instance().addStringLocalization("entity.Headless.name", "en_US", "Headless Horseman");
			LanguageRegistry.instance().addStringLocalization("entity.Witch.name", "en_US", "Witch");

			// --------------------------------------------------------------------
			RecipeRegistry.registerRecipes();
			EntityRegistry.registerEntities();
			
			
			MinecraftForge.setBlockHarvestLevel(ash, "shovel", 0);
		 	MinecraftForge.setBlockHarvestLevel(blockAsh, "shovel", 0);
		}
		finally
		{
			mainConfiguration.save();
		}
	}
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
	@Init
	public void initialize(FMLInitializationEvent evt) {
		new LiquidStacks();
		
		LiquidManager.liquids.add(new LiquidData(LiquidStacks.LSRawCandy, new LiquidStack(rawCandy, LiquidManager.BUCKET_VOLUME), new ItemStack(rawCandyBucket), new ItemStack(Item.bucketEmpty)));

//		MinecraftForge.registerConnectionHandler(new ConnectionHandler());
	
		CoreProxy.proxy.initializeRendering();
		CoreProxy.proxy.initializeEntityRendering();

	}

	
	// Mods-Loaded Method
		@PostInit
		public void modsLoaded(FMLPostInitializationEvent evt){

			modIC2 = Loader.isModLoaded("IC2");
			modForestry = Loader.isModLoaded("Forestry");

		    plugins();
		}
		
		public void plugins(){
			if(modIC2){
		 		cell = Items.getItem("cell").getItem();
				LiquidManager.liquids.add(new LiquidData(LiquidStacks.LSRawCandy, new LiquidStack(rawCandy, LiquidManager.BUCKET_VOLUME), new ItemStack(rawCandyCell), new ItemStack(Item.bucketEmpty)));

		 		
		 		GameRegistry.addShapelessRecipe(new ItemStack(rawCandyCell), new Object[]{cell, rawCandyBucket});
		 	}
			if(modForestry){
				canEmpty = ItemInterface.getItem("canEmpty").getItem();
				wax_capsule = ItemInterface.getItem("waxCapsule").getItem();
				refractory_capsule = ItemInterface.getItem("refractoryEmpty").getItem();
				
				
				LiquidManager.liquids.add(new LiquidData(LiquidStacks.LSRawCandy, new LiquidStack(rawCandy, LiquidManager.BUCKET_VOLUME), new ItemStack(rawCandyCan), new ItemStack(canEmpty)));
				LiquidManager.liquids.add(new LiquidData(LiquidStacks.LSRawCandy, new LiquidStack(rawCandy, LiquidManager.BUCKET_VOLUME), new ItemStack(rawCandyWaxCapsule), new ItemStack(wax_capsule)));
				LiquidManager.liquids.add(new LiquidData(LiquidStacks.LSRawCandy, new LiquidStack(rawCandy, LiquidManager.BUCKET_VOLUME), new ItemStack(rawCandyRefractoryCapsule), new ItemStack(refractory_capsule)));

				
				RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.LSRawCandy, new ItemStack(Item.bucketEmpty), new ItemStack(rawCandyBucket));
				RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.LSRawCandy, new ItemStack(cell), new ItemStack(rawCandyCell));
				RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.LSRawCandy, new ItemStack(canEmpty), new ItemStack(rawCandyCan));
				RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.LSRawCandy, new ItemStack(wax_capsule), new ItemStack(rawCandyWaxCapsule));
				RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.LSRawCandy, new ItemStack(refractory_capsule), new ItemStack(rawCandyRefractoryCapsule));		
			}

		}
	
	public String getPriorities() {
		return "after:mod_IC2;after:mod_BuildCraftCore;after:mod_BuildCraftEnergy;after:mod_BuildCraftFactory;after:mod_BuildCraftSilicon;after:mod_BuildCraftTransport;after:mod_RedPowerWorld";
	}
}
