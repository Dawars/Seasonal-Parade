package seasonal.parade.halloween;

import seasonal.parade.halloween.gui.ContainerMixer;
import seasonal.parade.halloween.gui.GuiMixer;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.src.*;

public class GuiHandler implements IGuiHandler{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){

		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

		TileMixer mixer = (TileMixer)tile_entity;
		
		if(tile_entity instanceof TileMixer){
			// If it is it returns a new ContainerTutorial instance
			return new ContainerMixer(player.inventory, mixer);
		}

		return null;
	}

@Override
public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){

	TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
	

	if(tile_entity instanceof TileMixer){
		// If it is it returns a new GuiTutorial instance
		return new GuiMixer(player.inventory, (TileMixer) tile_entity);
	}
		return null;
	}
}