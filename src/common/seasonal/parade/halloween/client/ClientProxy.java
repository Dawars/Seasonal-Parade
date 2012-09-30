package seasonal.parade.halloween.client;

import java.util.Arrays;

import seasonal.parade.halloween.common.CommonProxy;
import seasonal.parade.halloween.common.DefaultProps;
import net.minecraft.src.ModelSnowMan;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderThings(){
		for (String texture : Arrays.asList(
				DefaultProps.TEXTURE_BLOCKS,
				DefaultProps.TEXTURE_ITEMS))
			MinecraftForgeClient.preloadTexture(texture);
		
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilSnowman.class, new RenderEvilSnowman());
	}
}