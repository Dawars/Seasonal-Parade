/**
 * Copyright (c) SpaceToad, 2011
 * http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package seasonal.parade.halloween.proxy;

import java.io.File;
import java.util.List;

import seasonal.parade.halloween.DefaultProps;
import seasonal.parade.halloween.Halloween;
import seasonal.parade.halloween.render.EntityAshman;
import seasonal.parade.halloween.render.EntityHeadless;
import seasonal.parade.halloween.render.EntityWitch;
import seasonal.parade.halloween.render.RenderAshman;
import seasonal.parade.halloween.render.RenderHeadless;
import seasonal.parade.halloween.render.RenderWitch;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.WorldClient;
import net.minecraftforge.client.MinecraftForgeClient;

public class CoreProxyClient extends CoreProxy {

	/* INSTANCES */
	public Object getClient() {
		return FMLClientHandler.instance().getClient();
	}

	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

	/* ENTITY HANDLING */
	@Override
	public void removeEntity(Entity entity) {
		super.removeEntity(entity);

		if (isRenderWorld(entity.worldObj))
			((WorldClient) entity.worldObj).removeEntityFromWorld(entity.entityId);
	}

	/* WRAPPER */
	public void feedSubBlocks(int id, CreativeTabs tab, List itemList) {
		if(Block.blocksList[id] == null)
			return;

		Block.blocksList[id].getSubBlocks(id, tab, itemList);
	}

	/* LOCALIZATION */
	@Override
	public String getCurrentLanguage() {
		return StringTranslate.getInstance().getCurrentLanguage();
	}
	@Override
	public void addName(Object obj, String s) {
		LanguageRegistry.addName(obj, s);
	}
	@Override
	public void addLocalization(String s1, String string) {
		LanguageRegistry.instance().addStringLocalization(s1, string);
	}
	@Override
	public String getItemDisplayName(ItemStack stack){
		if (Item.itemsList[stack.itemID] == null) return "";

		return Item.itemsList[stack.itemID].getItemDisplayName(stack);
	}

	/* GFX */

	@Override
	public void initializeRendering() {
		
		Halloween.basketModel = RenderingRegistry.getNextAvailableRenderId();
		Halloween.mixerModel = RenderingRegistry.getNextAvailableRenderId();

//		RenderingRegistry.registerBlockHandler(new RenderingEntityBlocks());
//		RenderingRegistry.registerBlockHandler(Halloween.legacyPipeModel, new RenderingEntityBlocks());
//		RenderingRegistry.registerBlockHandler(new RenderingOil());
//		RenderingRegistry.registerBlockHandler(new RenderingMarkers());

		MinecraftForgeClient.preloadTexture(DefaultProps.TEXTURE_BLOCKS);
		MinecraftForgeClient.preloadTexture(DefaultProps.TEXTURE_ITEMS);
	}

	@Override
	public void initializeEntityRendering() {
		RenderingRegistry.registerEntityRenderingHandler(EntityAshman.class, new RenderAshman());
		RenderingRegistry.registerEntityRenderingHandler(EntityHeadless.class, new RenderHeadless(2));
		RenderingRegistry.registerEntityRenderingHandler(EntityWitch.class, new RenderWitch());
	}


	/* NETWORKING */
	@Override
	public void sendToServer(Packet packet) {
		FMLClientHandler.instance().getClient().getSendQueue().addToSendQueue(packet);
	}

	/* FILE SYSTEM */
	public File getBuildCraftBase() {
		return Minecraft.getMinecraftDir();
	}

	/* BUILDCRAFT PLAYER */
	@Override
	public String playerName() {
		return FMLClientHandler.instance().getClient().thePlayer.username;
	}

	private EntityPlayer createNewPlayer(World world) {
		return new EntityPlayer(world) {
			@Override public void sendChatToPlayer(String var1) {}
			@Override public boolean canCommandSenderUseCommand(String var1) { return false; }
		};
	}

	@Override
	public EntityPlayer getBuildCraftPlayer(World world) {
		if (CoreProxy.buildCraftPlayer == null) {
			CoreProxy.buildCraftPlayer = createNewPlayer(world);
		}

		return CoreProxy.buildCraftPlayer;
	}

}