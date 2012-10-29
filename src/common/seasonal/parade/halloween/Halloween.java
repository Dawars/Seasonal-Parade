package seasonal.parade.halloween;

import forestry.api.core.ItemInterface;
import forestry.api.fuels.EngineBronzeFuel;
import forestry.api.fuels.EngineCopperFuel;
import forestry.api.recipes.RecipeManagers;
import ic2.api.Items;

import java.io.File;
import java.util.TreeMap;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GameWindowListener;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Potion;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;

import buildcraft.api.liquids.LiquidData;
import buildcraft.api.liquids.LiquidManager;
import buildcraft.api.liquids.LiquidStack;

import seasonal.parade.halloween.network.PacketHandler;
import seasonal.parade.halloween.network.PacketUpdate;
import seasonal.parade.halloween.proxy.CoreProxy;
import seasonal.parade.halloween.render.TextureMilkFX;
import seasonal.parade.halloween.render.TextureRawCandyFX;
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

@Mod(name="Halloween mod", version=Version.VERSION, useMetadata = false, modid = "SeasonalParade|Halloween", dependencies="required-after:Forge@[6.0.0,)")
@NetworkMod(channels = {DefaultProps.NET_CHANNEL_NAME}, packetHandler = PacketHandler.class, clientSideRequired = true, serverSideRequired = true)
public class Halloween {

	@Instance
	public static Halloween instance;
	
	static boolean modifyWorld = false;//add biomes to OverWorld
	public static boolean trackNetworkUsage = false;
	public static int updateFactor = 10;
	
	public static TreeMap<BlockIndex, PacketUpdate> bufferedDescriptions = new TreeMap<BlockIndex, PacketUpdate>();
	
//	public static HalloweenConfiguration mainConfiguration;

	
	public static int basketModel;
	public static int mixerModel;
	
	
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
    public static Block candyMaker;
    
 // Item Registry
 	public static Item ashItem;
 	public static Item rawCandy;
 	public static Item rawCandyBucket;
 	public static Item rawCandyCell;
 	public static Item rawCandyCan;
 	public static Item rawCandyWaxCapsule;
 	public static Item rawCandyRefractoryCapsule;
 	
 	public static Item milk;
 	public static Item milkCell;
 	public static Item milkCan;
 	public static Item milkWaxCapsule;
 	public static Item milkRefractoryCapsule;
 	
 	public static Item candyRed;
 	public static Item candyGreen;
 	public static Item candyBlue;
 	public static Item candyYellow;
 	public static Item candyOrange;
 	public static Item candyPumpkin;
 	public static Item candyPink;
 	
 	
 	//IC2
 	public static Item cell;
 	
 	//Forestry
 	public static Item canEmpty;
 	public static Item wax_capsule;
 	public static Item refractory_capsule;
 	
 	
	public static final BiomeGenBase Halloween = (new BiomeGenHalloween(24));

	
	@PreInit
	public void loadConfiguration(FMLPreInitializationEvent evt) {
		NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
		GameRegistry.registerTileEntity(TileMixer.class, "Mixer");
		GameRegistry.registerTileEntity(TileCandyMaker.class, "Candy Maker");
		
		
		GameRegistry.registerWorldGenerator(new HalloweenWorldGenerator());
		
		GameRegistry.addBiome(Halloween);
		
//		Version.versionCheck();


//		mainConfiguration = new HalloweenConfiguration(new File(evt.getModConfigurationDirectory(), "halloween/main.conf"));
		try
		{
//			mainConfiguration.load();

			
//			Property trackNetwork = Halloween.mainConfiguration.getOrCreateBooleanProperty("trackNetworkUsage", Configuration.CATEGORY_GENERAL, false);
//			trackNetworkUsage = trackNetwork.getBoolean(false);
//
//
//			Property factor = Halloween.mainConfiguration.getOrCreateIntProperty("network.updateFactor", Configuration.CATEGORY_GENERAL, 10);
//			factor.comment = "increasing this number will decrease network update frequency, useful for overloaded servers";
//			updateFactor = factor.getInt(10);
			
			// Block Registry
			evilPumpkin = (new EvilPumpkin(DefaultProps.EVIL_PUMPKIN_BLOCK_ID, 0, false)).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setBlockName("evilPumpkin");
			evilLantern = (new EvilPumpkin(DefaultProps.EVIL_LANTERN_BLOCK_ID, 0, true)).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setLightValue(1.0F).setBlockName("evilLantern");
		    ash = (new BlockAsh(DefaultProps.ASH_LAYER_ID, 6)).setHardness(0.1F).setStepSound(Block.soundClothFootstep).setBlockName("ash").setLightOpacity(0);
		    blockAsh = (new BlockAshBlock(DefaultProps.ASH_BLOCK_ID, 6)).setHardness(0.2F).setStepSound(Block.soundClothFootstep).setBlockName("ashBlock");
//		    candyBasket = (new CandyBasket(DefaultProps.CANDY_BASKET_ID, Material.pumpkin)).setHardness(0.2F).setStepSound(Block.soundWoodFootstep).setBlockName("candyBasket");
	 		Mixer = new BlockMixer(DefaultProps.MIXER_BLOCK_ID, 7).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setCreativeTab(CreativeTabs.tabBlock).setBlockName("mixer");
	 		candyMaker = new BlockCandyMaker(DefaultProps.CANDY_MAKER_ID, 3).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setCreativeTab(CreativeTabs.tabBlock).setBlockName("candyMaker");

			
			
	 		// Item Registry
			ashItem = (new HalloweenItem(DefaultProps.ASH_ITEM_ID)).setIconIndex(1).setMaxStackSize(16).setCreativeTab(CreativeTabs.tabDecorations).setItemName("ashItem");
			rawCandy = new HalloweenItem(DefaultProps.RAW_CANDY_ID).setIconIndex(0).setCreativeTab(CreativeTabs.tabMisc).setItemName("rawCandy");
			rawCandyBucket = new HalloweenItem(DefaultProps.RAW_CANDY_BUCKET_ID).setIconIndex(8).setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc).setItemName("rawCandyBucket");
			rawCandyCell = new HalloweenItem(DefaultProps.RAW_CANDY_CELL_ID).setIconIndex(7).setCreativeTab(CreativeTabs.tabMisc).setItemName("rawCandyCell");
			rawCandyCan = new HalloweenItem(DefaultProps.RAW_CANDY_CAN_ID).setIconIndex(2).setCreativeTab(CreativeTabs.tabMisc).setItemName("rawCandyCan");
			rawCandyWaxCapsule = new HalloweenItem(DefaultProps.RAW_CANDY_WAX_CAPSULE_ID).setIconIndex(3).setCreativeTab(CreativeTabs.tabMisc).setItemName("rawCandyWaxCapsule");
			rawCandyRefractoryCapsule = new HalloweenItem(DefaultProps.RAW_CANDY_REFRACTORY_CAPSULE_ID).setIconIndex(6).setCreativeTab(CreativeTabs.tabMisc).setItemName("rawCandyRefractoryCapsule");
			
			milk = new HalloweenItem(DefaultProps.MILK_ID).setIconIndex(15).setCreativeTab(CreativeTabs.tabMisc).setItemName("milk");
			milkCell = new HalloweenItem(DefaultProps.MILK_CELL_ID).setIconIndex(14).setCreativeTab(CreativeTabs.tabMisc).setItemName("milkCell");
			milkCan = new HalloweenItem(DefaultProps.MILK_CAN_ID).setIconIndex(9).setCreativeTab(CreativeTabs.tabMisc).setItemName("milkCan");
			milkWaxCapsule = new HalloweenItem(DefaultProps.MILK_WAX_CAPSULE_ID).setIconIndex(10).setCreativeTab(CreativeTabs.tabMisc).setItemName("milkWaxCapsule");
			milkRefractoryCapsule = new HalloweenItem(DefaultProps.MILK_REFRACTORY_CAPSULE_ID).setIconIndex(13).setCreativeTab(CreativeTabs.tabMisc).setItemName("milkRefractoryCapsule");

			candyRed = (new CandyFood(DefaultProps.CANDY1, 0, 2F)).setPotionEffect(Potion.fireResistance.getId(), 30, 2, 1F).setCreativeTab(CreativeTabs.tabFood).setIconIndex(1 * 16 + 0).setItemName("candyRed");
			candyGreen = (new CandyFood(DefaultProps.CANDY2, 0, 2F)).setPotionEffect(Potion.regeneration.getId(), 30, 2, 1F).setCreativeTab(CreativeTabs.tabFood).setIconIndex(1 * 16 + 1).setItemName("candyGreen");
			candyBlue = (new CandyFood(DefaultProps.CANDY3, 0, 2F)).setPotionEffect(Potion.waterBreathing.getId(), 30, 2, 1F).setCreativeTab(CreativeTabs.tabFood).setIconIndex(1 * 16 + 2).setItemName("candyBlue");
			candyYellow = (new CandyFood(DefaultProps.CANDY4, 0, 2F)).setPotionEffect(Potion.nightVision.getId(), 30, 2, 1F).setCreativeTab(CreativeTabs.tabFood).setIconIndex(1 * 16 + 3).setItemName("candyYellow");
			candyOrange = (new CandyFood(DefaultProps.CANDY5, 0, 2F)).setPotionEffect(Potion.digSpeed.getId(), 30, 2, 1F).setCreativeTab(CreativeTabs.tabFood).setIconIndex(1 * 16 + 4).setItemName("candyOrange");
			candyPumpkin = (new CandyFood(DefaultProps.CANDY6, 0, 2F)).setPotionEffect(Potion.invisibility.getId(), 30, 2, 1F).setCreativeTab(CreativeTabs.tabFood).setIconIndex(1 * 16 + 5).setItemName("candyPumpkin");
			candyPink = (new CandyFood(DefaultProps.CANDY7, 0, 2F)).setPotionEffect(Potion.heal.getId(), 30, 2, 1F).setCreativeTab(CreativeTabs.tabFood).setIconIndex(1 * 16 + 6).setItemName("candyPink");


			
		    RegisterBlocks(new Block[] {
					evilPumpkin, evilLantern, ash, blockAsh, Mixer, candyMaker
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
			LanguageRegistry.addName(candyMaker, "Candy Maker");
			
			// Item Names
			LanguageRegistry.addName(ashItem, "Ash");
			LanguageRegistry.addName(rawCandy, "Raw Candy");
			LanguageRegistry.addName(rawCandyBucket, "Raw Candy Bucket");
			LanguageRegistry.addName(rawCandyCell, "Raw Candy Cell");
			LanguageRegistry.addName(rawCandyCan, "Raw Candy Can");
			LanguageRegistry.addName(rawCandyWaxCapsule, "Raw Candy Capsule");
			LanguageRegistry.addName(rawCandyRefractoryCapsule, "Raw Candy Capsule");

			LanguageRegistry.addName(milkCell, "Milk Cell");
			LanguageRegistry.addName(milkCan, "Milk Can");
			LanguageRegistry.addName(milkWaxCapsule, "Milk Capsule");
			LanguageRegistry.addName(milkRefractoryCapsule, "Milk Capsule");
			
			
			LanguageRegistry.addName(candyBlue, "Blue Candy");
			LanguageRegistry.addName(candyGreen, "Green Candy");
			LanguageRegistry.addName(candyOrange, "Orange Candy");
			LanguageRegistry.addName(candyPumpkin, "Pumpkin Candy");
			LanguageRegistry.addName(candyPink, "Pink Candy");
			LanguageRegistry.addName(candyRed, "Red Candy");
			LanguageRegistry.addName(candyYellow, "Yellow Candy");
			
//			LanguageRegistry.addName(milkRefractoryCapsule, "Milk Capsule");
			
			// Containers - Mobs
			LanguageRegistry.instance().addStringLocalization("container.candybasket", "en_US", "Candy Basket");
			LanguageRegistry.instance().addStringLocalization("container.mixer", "en_US", "Mixer");
			

			LanguageRegistry.instance().addStringLocalization("entity.Ashman.name", "en_US", "Ashman");
			LanguageRegistry.instance().addStringLocalization("entity.Headless.name", "en_US", "Headless Horseman");
			LanguageRegistry.instance().addStringLocalization("entity.Witch.name", "en_US", "Witch");
//			LanguageRegistry.instance().addStringLocalization("entity.EvilPumpkin.name", "en_US", "Evil Pumpkin");

			// --------------------------------------------------------------------
			RecipeRegistry.registerRecipes();
			EntityRegistry.registerEntities();
			
			
			MinecraftForge.setBlockHarvestLevel(ash, "shovel", 0);
		 	MinecraftForge.setBlockHarvestLevel(blockAsh, "shovel", 0);
		}
		finally
		{
//			mainConfiguration.save();
		}
	}
	@Init
	public void initialize(FMLInitializationEvent evt) {
		new LiquidStacks();
		
		LiquidManager.liquids.add(new LiquidData(LiquidStacks.rawCandy, new ItemStack(rawCandyBucket), new ItemStack(Item.bucketEmpty)));
		LiquidManager.liquids.add(new LiquidData(LiquidStacks.milk, new ItemStack(Item.bucketMilk), new ItemStack(Item.bucketEmpty)));

		CoreProxy.proxy.initializeRendering();
		CoreProxy.proxy.initializeEntityRendering();

	}

	
	// Mods-Loaded Method
	@PostInit
	public void modsLoaded(FMLPostInitializationEvent evt){
		CoreProxy.proxy.addAnimation();
				
		modIC2 = Loader.isModLoaded("IC2");
		modForestry = Loader.isModLoaded("Forestry");

 	    plugins();
	}
		
	public void plugins(){
		if(modIC2){
	 		cell = Items.getItem("cell").getItem();
			LiquidManager.liquids.add(new LiquidData(LiquidStacks.rawCandy, new ItemStack(rawCandyCell), new ItemStack(cell)));
			LiquidManager.liquids.add(new LiquidData(LiquidStacks.milk, new ItemStack(milkCell), new ItemStack(cell)));


	 		GameRegistry.addShapelessRecipe(new ItemStack(rawCandyCell), new Object[]{cell, rawCandyBucket});
	 		GameRegistry.addShapelessRecipe(new ItemStack(milkCell), new Object[]{cell, Item.bucketMilk});
	 	}
		if(modForestry){
			canEmpty = ItemInterface.getItem("canEmpty").getItem();
			wax_capsule = ItemInterface.getItem("waxCapsule").getItem();
			refractory_capsule = ItemInterface.getItem("refractoryEmpty").getItem();


			LiquidManager.liquids.add(new LiquidData(LiquidStacks.rawCandy, new ItemStack(rawCandyCan), new ItemStack(canEmpty)));
			LiquidManager.liquids.add(new LiquidData(LiquidStacks.rawCandy, new ItemStack(rawCandyWaxCapsule), new ItemStack(wax_capsule)));
			LiquidManager.liquids.add(new LiquidData(LiquidStacks.rawCandy, new ItemStack(rawCandyRefractoryCapsule), new ItemStack(refractory_capsule)));

			LiquidManager.liquids.add(new LiquidData(LiquidStacks.milk, new ItemStack(milkCan), new ItemStack(canEmpty)));
			LiquidManager.liquids.add(new LiquidData(LiquidStacks.milk, new ItemStack(milkWaxCapsule), new ItemStack(wax_capsule)));
			LiquidManager.liquids.add(new LiquidData(LiquidStacks.milk, new ItemStack(milkRefractoryCapsule), new ItemStack(refractory_capsule)));


			//Forestry Bottler
			RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.rawCandy, new ItemStack(Item.bucketEmpty), new ItemStack(rawCandyBucket));
			RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.rawCandy, new ItemStack(cell), new ItemStack(rawCandyCell));
			RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.rawCandy, new ItemStack(canEmpty), new ItemStack(rawCandyCan));
			RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.rawCandy, new ItemStack(wax_capsule), new ItemStack(rawCandyWaxCapsule));
			RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.rawCandy, new ItemStack(refractory_capsule), new ItemStack(rawCandyRefractoryCapsule));		

			RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.milk, new ItemStack(Item.bucketEmpty), new ItemStack(Item.bucketMilk));
			RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.milk, new ItemStack(cell), new ItemStack(milkCell));
			RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.milk, new ItemStack(canEmpty), new ItemStack(milkCan));
			RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.milk, new ItemStack(wax_capsule), new ItemStack(milkWaxCapsule));
			RecipeManagers.bottlerManager.addRecipe(5, LiquidStacks.milk, new ItemStack(refractory_capsule), new ItemStack(milkRefractoryCapsule));
			
			//Forestry Bioengine
			new EngineBronzeFuel(new ItemStack(milk), 1, 40000, 1);
			new EngineBronzeFuel(new ItemStack(rawCandy), 1, 40000, 1);
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
	public String getPriorities() {
		return "after:mod_IC2;after:mod_BuildCraftCore;after:mod_BuildCraftEnergy;after:mod_BuildCraftFactory;after:mod_BuildCraftSilicon;after:mod_BuildCraftTransport;after:mod_RedPowerWorld;" +
				"before:Railcraft";
	}
}
