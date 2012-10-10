package seasonal.parade.halloween.client;

import java.util.Arrays;

import seasonal.parade.halloween.common.CommonProxy;
import seasonal.parade.halloween.common.DefaultProps;
import seasonal.parade.halloween.render.RenderAshman;
import seasonal.parade.halloween.render.RenderHeadless;
import seasonal.parade.halloween.render.TextureRawCandyFX;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModelSnowMan;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderThings(){
		for (String texture : Arrays.asList(
				DefaultProps.TEXTURE_BLOCKS,
				DefaultProps.TEXTURE_ITEMS,
				"/seasonal/parade/halloween/gfx/mob/Horseman.png",
				DefaultProps.TEXTURE_PATH_GUI + "mixer_gui.png"))
			MinecraftForgeClient.preloadTexture(texture);
		
		RenderingRegistry.registerEntityRenderingHandler(EntityAshman.class, new RenderAshman());
		RenderingRegistry.registerEntityRenderingHandler(EntityHeadless.class, new RenderHeadless());
		ModLoader.addAnimation(new TextureRawCandyFX());
	}
	
	@Override
	public boolean isSimulating(World world){
		return world.isRemote;
	}
}