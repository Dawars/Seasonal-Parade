package seasonal.parade.halloween;

import seasonal.parade.halloween.gui.ContainerCandyMaker;
import seasonal.parade.halloween.gui.ContainerMixer;
import seasonal.parade.halloween.gui.GuiCandyMaker;
import seasonal.parade.halloween.gui.GuiMixer;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.src.*;

public class GuiHandler implements IGuiHandler{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){

		TileEntity tile = world.getBlockTileEntity(x, y, z);

		switch (id) {

			case GuiIds.MIXER:
				if (!(tile instanceof TileMixer))
					return null;
				return new ContainerMixer(player.inventory, (TileMixer)tile);
			
			case GuiIds.CANDY_MAKER:
				if (!(tile instanceof TileCandyMaker))
					return null;
				return new ContainerCandyMaker(player.inventory, (TileCandyMaker)tile);
			
			
			default:
				return null;
		}
	}

@Override
public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){

	TileEntity tile = world.getBlockTileEntity(x, y, z);

		switch (id) {
	
			case GuiIds.MIXER:
				if (!(tile instanceof TileMixer))
					return null;
				return new GuiMixer(player.inventory, (TileMixer)tile);
			
			case GuiIds.CANDY_MAKER:
				if (!(tile instanceof TileCandyMaker))
					return null;
				return new GuiCandyMaker(player.inventory, (TileCandyMaker)tile);
			
			
			default:
				return null;
		}
	}
}