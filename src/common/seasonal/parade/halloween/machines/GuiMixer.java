package seasonal.parade.halloween.machines;

// These are the imports you will need
import org.lwjgl.opengl.GL11;

import seasonal.parade.halloween.common.DefaultProps;


import net.minecraft.src.*;

// Create a class and extend GuiContainer
public class GuiMixer extends GuiContainer{
	// This is you super constructor it has 2 params,
	// @param InventoryPlayer, this is the players inventory declaration
	// @param TileTutorial this is the TileTutorial declaration
	public GuiMixer(InventoryPlayer player_inventory, TileMixer tile_entity){
		// just supers the a new ContainerTutorial
		super(new ContainerMixer(tile_entity, player_inventory));
	}
	
	 /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.mixer"), 14, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int var4 = this.mc.renderEngine.getTexture(DefaultProps.TEXTURE_PATH_GUI + "/mixer_gui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
    }
}