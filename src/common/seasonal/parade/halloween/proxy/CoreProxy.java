package seasonal.parade.halloween.proxy;

import java.util.List;
import java.io.File;
import java.util.Random;

import seasonal.parade.halloween.network.HalloweenPacket;
import seasonal.parade.halloween.render.TextureRawCandyFX;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.FMLTextureFX;
import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class CoreProxy {
	
	@SidedProxy(clientSide="seasonal.parade.halloween.proxy.CoreProxyClient", serverSide="seasonal.parade.halloween.proxy.CoreProxy")
	public static CoreProxy proxy;

	public Minecraft getClientInstance() {
	    return FMLClientHandler.instance().getClient();
    }
	
	public String getMinecraftVersion() { return "1.3.2"; }

	/* INSTANCES */
	public Object getClient() { return null; }
	public World getClientWorld() { return null; }

	/* SIMULATION */
	public boolean isSimulating(World world) {
		return !world.isRemote;
	}

	public boolean isRenderWorld(World world) {
		return world.isRemote;
	}

	public String getCurrentLanguage() {
		return null;
	}

	/* ENTITY HANDLING */
	public void removeEntity(Entity entity) {
		entity.setDead();
	}

	/* WRAPPER */
	public void feedSubBlocks(int id, CreativeTabs tab, List itemList) {}

	/* LOCALIZATION */
	public void addName(Object obj, String s) {}
	public void addLocalization(String s1, String string) {}
	public String getItemDisplayName(ItemStack newStack) { return ""; }

	/* GFX */
	public void obsidianPipePickup(World world, EntityItem item, TileEntity tile) {}
	public void initializeRendering() {}
	public void initializeEntityRendering() {}

	public void registerTileEntity(Class clas, String ident) {
		GameRegistry.registerTileEntity(clas, ident);
	}

	public void onCraftingPickup(World world, EntityPlayer player, ItemStack stack) {
		stack.onCrafting(world, player, stack.stackSize);
	}

	public void addCraftingRecipe(ItemStack result, Object[] recipe) {
		GameRegistry.addRecipe(result, recipe);
	}

	public void addShapelessRecipe(ItemStack result, Object[] recipe) {
		GameRegistry.addShapelessRecipe(result, recipe);
	}

	public void sendToPlayers(Packet packet, World world, int x, int y, int z, int maxDistance) {
		if (packet != null) {
			for (int j = 0; j < world.playerEntities.size(); j++) {
				EntityPlayerMP player = (EntityPlayerMP) world.playerEntities.get(j);

				if (Math.abs(player.posX - x) <= maxDistance && Math.abs(player.posY - y) <= maxDistance
						&& Math.abs(player.posZ - z) <= maxDistance){
//					player.playerNetServerHandler.sendPacketToPlayer(packet);
				}
			}
		}
	}

	public void sendToPlayer(EntityPlayer entityplayer, HalloweenPacket packet) {
		EntityPlayerMP player = (EntityPlayerMP) entityplayer;
//		player.playerNetServerHandler.sendPacketToPlayer(packet.getPacket());
	}

	public void sendToServer(Packet packet) {}

	/* FILE SYSTEM */
	public File getBuildCraftBase() {
		return new File("./");
	}

	public int addCustomTexture(String pathToTexture) {
		return 0;
	}

	public void TakenFromCrafting(EntityPlayer thePlayer, ItemStack itemstack, IInventory craftMatrix) {
		GameRegistry.onItemCrafted(thePlayer, itemstack, craftMatrix);
	}

	public Random createNewRandom(World world) {
		return new Random(world.getSeed());
	}

	/* BUILDCRAFT PLAYER */
	protected static EntityPlayer buildCraftPlayer;

	public String playerName() { return ""; }


	
	public void addAnimation(Object anim) {}
	
	public void spawnParticle(String particle, double x, double y, double z, double motionX, double motionY, double motionZ){}
}
