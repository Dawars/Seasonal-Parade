package seasonal.parade.halloween.common;

// These are all the imports you will need
import seasonal.parade.halloween.machines.ContainerMixer;
import seasonal.parade.halloween.machines.GuiMixer;
import seasonal.parade.halloween.machines.TileMixer;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.src.*;

// Create a class and implement IGuiHandler
public class GuiHandler implements IGuiHandler{
	// This is a required method to open you Gui and has 6 params
	// @param int id, this is the Gui Id
	// @param EntityPlayer, this is the player declaration
	// @param World, this is the world declaration
	// @param int x, y, z this is the players current x, y, z coords
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
		// This gets the TileEntity the player is currently activating
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		// This checks if the TileEntity is the TileTutorial
		if(tile_entity instanceof TileMixer){
			// If it is it returns a new ContainerTutorial instance
			return new ContainerMixer((TileMixer) tile_entity, player.inventory);
		}

		// Returns null if not
		return null;
	}

// This is another required method to open the Gui and has 6 params
// @param int id, this is the Gui Id
// @param EntityPlayer, this is the player declaration
// @param World, this is the world declaration,
// @param int x, y, z this is the players current x, y, z coords
@Override
public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
	// This gets the TIleEntity the player is currently activating
	TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
	
	// This checks if the TileEntity is the TileTutorial
	if(tile_entity instanceof TileMixer){
		// If it is it returns a new GuiTutorial instance
		return new GuiMixer(player.inventory, (TileMixer) tile_entity);
	}
	
	// Returns null if not
	return null;
	}
}