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

import seasonal.parade.halloween.render.TextureRawCandyFX;
import seasonal.parade.halloween.DefaultProps;
import seasonal.parade.halloween.Halloween;
import seasonal.parade.halloween.render.EntityAshman;
import seasonal.parade.halloween.render.EntityEvilPumpkin;
import seasonal.parade.halloween.render.EntityHeadless;
import seasonal.parade.halloween.render.EntityWitch;
import seasonal.parade.halloween.render.RenderAshman;
import seasonal.parade.halloween.render.RenderEvilPumpkin;
import seasonal.parade.halloween.render.RenderHeadless;
import seasonal.parade.halloween.render.RenderWitch;
import seasonal.parade.halloween.render.TextureMilkFX;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.FMLTextureFX;
import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAuraFX;
import net.minecraft.src.EntityBreakingFX;
import net.minecraft.src.EntityBubbleFX;
import net.minecraft.src.EntityCloudFX;
import net.minecraft.src.EntityCritFX;
import net.minecraft.src.EntityDiggingFX;
import net.minecraft.src.EntityDropParticleFX;
import net.minecraft.src.EntityEnchantmentTableParticleFX;
import net.minecraft.src.EntityExplodeFX;
import net.minecraft.src.EntityFX;
import net.minecraft.src.EntityFlameFX;
import net.minecraft.src.EntityFootStepFX;
import net.minecraft.src.EntityHeartFX;
import net.minecraft.src.EntityHugeExplodeFX;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLargeExplodeFX;
import net.minecraft.src.EntityLavaFX;
import net.minecraft.src.EntityNoteFX;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPortalFX;
import net.minecraft.src.EntityReddustFX;
import net.minecraft.src.EntitySmokeFX;
import net.minecraft.src.EntitySnowShovelFX;
import net.minecraft.src.EntitySpellParticleFX;
import net.minecraft.src.EntitySplashFX;
import net.minecraft.src.EntitySuspendFX;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.TextureFX;
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
//		RenderingRegistry.registerEntityRenderingHandler(EntityEvilPumpkin.class, new RenderEvilPumpkin());
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

	
	@Override
	public void addAnimation() {
		TextureFXManager.instance().addAnimation((FMLTextureFX)new TextureRawCandyFX());
		TextureFXManager.instance().addAnimation((FMLTextureFX)new TextureMilkFX());
	}
	@Override
	public void spawnParticle(String particle, double x, double y, double z, double motionX, double motionY, double motionZ){
		Minecraft mc = Minecraft.getMinecraft();
		
		if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null)
        {
            int var14 = mc.gameSettings.particleSetting;

            if (var14 == 1 && mc.theWorld.rand.nextInt(3) == 0)
            {
                var14 = 2;
            }

            double var15 = mc.renderViewEntity.posX - x;
            double var17 = mc.renderViewEntity.posY - y;
            double var19 = mc.renderViewEntity.posZ - z;
            EntityFX var21 = null;
            Object effectObject = null;

            if (particle.equals("hugeexplosion"))
            {
                mc.effectRenderer.addEffect(var21 = new EntityHugeExplodeFX(mc.theWorld, x, y, z, motionX, motionY, motionZ));
            }
            else if (particle.equals("largeexplode"))
            {
                mc.effectRenderer.addEffect(var21 = new EntityLargeExplodeFX(mc.renderEngine, mc.theWorld, x, y, z, motionX, motionY, motionZ));
            }

            if (var21 != null)
            {
                return;// (EntityFX)var21;
            }
            else
            {
                double var22 = 16.0D;

                if (var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22)
                {
                    return;
                }
                else if (var14 > 1)
                {
                    return;
                }
                else
                {
                    if (particle.equals("bubble"))
                    {
                        var21 = new EntityBubbleFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("suspended"))
                    {
                        var21 = new EntitySuspendFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("depthsuspend"))
                    {
                        var21 = new EntityAuraFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("townaura"))
                    {
                        var21 = new EntityAuraFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("crit"))
                    {
                        var21 = new EntityCritFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("magicCrit"))
                    {
                        var21 = new EntityCritFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                        ((EntityFX)var21).setRBGColorF(((EntityFX)var21).getRedColorF() * 0.3F, ((EntityFX)var21).getGreenColorF() * 0.8F, ((EntityFX)var21).getBlueColorF());
                        ((EntityFX)var21).setParticleTextureIndex(((EntityFX)var21).getParticleTextureIndex() + 1);
                    }
                    else if (particle.equals("smoke"))
                    {
                        var21 = new EntitySmokeFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("mobSpell"))
                    {
                        var21 = new EntitySpellParticleFX(mc.theWorld, x, y, z, 0.0D, 0.0D, 0.0D);
                        ((EntityFX)var21).setRBGColorF((float)motionX, (float)motionY, (float)motionZ);
                    }
                    else if (particle.equals("spell"))
                    {
                        var21 = new EntitySpellParticleFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("instantSpell"))
                    {
                        var21 = new EntitySpellParticleFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                        ((EntitySpellParticleFX)var21).func_70589_b(144);
                    }
                    else if (particle.equals("note"))
                    {
                        var21 = new EntityNoteFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("portal"))
                    {
                        var21 = new EntityPortalFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("enchantmenttable"))
                    {
                        var21 = new EntityEnchantmentTableParticleFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("explode"))
                    {
                        var21 = new EntityExplodeFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("flame"))
                    {
                        var21 = new EntityFlameFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("lava"))
                    {
                        var21 = new EntityLavaFX(mc.theWorld, x, y, z);
                    }
                    else if (particle.equals("footstep"))
                    {
                        var21 = new EntityFootStepFX(mc.renderEngine, mc.theWorld, x, y, z);
                    }
                    else if (particle.equals("splash"))
                    {
                        var21 = new EntitySplashFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("largesmoke"))
                    {
                        var21 = new EntitySmokeFX(mc.theWorld, x, y, z, motionX, motionY, motionZ, 2.5F);
                    }
                    else if (particle.equals("cloud"))
                    {
                        var21 = new EntityCloudFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("reddust"))
                    {
                        var21 = new EntityReddustFX(mc.theWorld, x, y, z, (float)motionX, (float)motionY, (float)motionZ);
                    }
                    else if (particle.equals("snowballpoof"))
                    {
                        var21 = new EntityBreakingFX(mc.theWorld, x, y, z, Item.snowball);
                        effectObject = Item.snowball;
                    }
                    else if (particle.equals("dripWater"))
                    {
                        var21 = new EntityDropParticleFX(mc.theWorld, x, y, z, Material.water);
                    }
                    else if (particle.equals("dripLava"))
                    {
                        var21 = new EntityDropParticleFX(mc.theWorld, x, y, z, Material.lava);
                    }
                    else if (particle.equals("snowshovel"))
                    {
                        var21 = new EntitySnowShovelFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else if (particle.equals("slime"))
                    {
                        var21 = new EntityBreakingFX(mc.theWorld, x, y, z, Item.slimeBall);
                        effectObject = Item.slimeBall;
                    }
                    else if (particle.equals("heart"))
                    {
                        var21 = new EntityHeartFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
                    }
                    else
                    {
                        int var24;

                        if (particle.startsWith("iconcrack_"))
                        {
                            var24 = Integer.parseInt(particle.substring(particle.indexOf("_") + 1));
                            var21 = new EntityBreakingFX(mc.theWorld, x, y, z, motionX, motionY, motionZ, Item.itemsList[var24]);
                            effectObject = Item.itemsList[var24];
                        }
                        else if (particle.startsWith("tilecrack_"))
                        {
                            var24 = Integer.parseInt(particle.substring(particle.indexOf("_") + 1));
                            var21 = new EntityDiggingFX(mc.theWorld, x, y, z, motionX, motionY, motionZ, Block.blocksList[var24], 0, 0);
                            effectObject = Block.blocksList[var24];
                        }
                    }

                    if (var21 != null)
                    {
                        mc.effectRenderer.addEffect((EntityFX)var21, effectObject);
                    }

                    return;// (EntityFX)var21;
                }
            }
        }
        else
        {
            return;
        }
	}

}