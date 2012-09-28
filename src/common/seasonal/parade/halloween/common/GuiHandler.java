package seasonal.parade.halloween.common;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.src.*;

public class GuiHandler implements IGuiHandler{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
//		if(tile_entity instanceof TileFoundry){
//			return new ContainerFoundry((TileFoundry) tile_entity, player.inventory);
//		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

//		if(tile_entity instanceof TileFoundry){
//			return new GuiFoundry(player.inventory, (TileFoundry) tile_entity);
//		}

		return null;
	}
}