package seasonal.parade.halloween.machines;

import org.lwjgl.opengl.GL11;

import seasonal.parade.halloween.common.DefaultProps;


import net.minecraft.src.*;
import net.minecraftforge.client.ForgeHooksClient;

public class GuiMixer extends GuiContainer{
	public TileMixer tile;
	GuiMixer mixer = this;
	
	public GuiMixer(InventoryPlayer player_inventory, TileMixer tile_entity){
		super(new ContainerMixer(tile_entity, player_inventory));
		this.tile = tile_entity;
	}
	
	 /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.mixer"), 14, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        String debug = "null";
        if(tile.canMix())
        	debug = "true";
        else
        	debug = "false";
        
        this.fontRenderer.drawString(debug, 60, 6, 4210752);

    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		int i = mc.renderEngine.getTexture(DefaultProps.TEXTURE_PATH_GUI + "/mixer_gui.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(i);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

		//process arrow
		int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		int var7;

        if (this.tile.isMixing())
        {
            var7 = this.tile.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(var5 + 56, var6 + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
        }

        var7 = this.tile.getCookProgressScaled(24);
        this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 14, var7 + 1, 16);
		
		
//		Will be for displaying liquids
//		TileMixer mixer = (TileMixer) tile;
//		Mixer mixerBlock = ((Mixer) mixer.mixer);
//
//		if (mixer.getScaledBurnTime(58) > 0)
//			displayGauge(j, k, 19, 104, mixer.getScaledBurnTime(58), mixerBlock.liquidId);
//
//		if (mixerBlock.getScaledCoolant(58) > 0)
//			displayGauge(j, k, 19, 122, mixerBlock.getScaledCoolant(58), mixerBlock.coolantId);
	}
    
    private void displayGauge(int j, int k, int line, int col, int squaled, int liquidId) {
		int liquidImgIndex = 0;

		if (liquidId < Block.blocksList.length && Block.blocksList[liquidId] != null) {
			ForgeHooksClient.bindTexture(Block.blocksList[liquidId].getTextureFile(), 0);
			liquidImgIndex = Block.blocksList[liquidId].blockIndexInTexture;
		} else if (Item.itemsList[liquidId] != null) {
			ForgeHooksClient.bindTexture(Item.itemsList[liquidId].getTextureFile(), 0);
			liquidImgIndex = Item.itemsList[liquidId].getIconFromDamage(0);
		} else {
			return;			
		}

		int imgLine = liquidImgIndex / 16;
		int imgColumn = liquidImgIndex - imgLine * 16;

		int start = 0;

		while (true) {
			int x = 0;

			if (squaled > 16) {
				x = 16;
				squaled -= 16;
			} else {
				x = squaled;
				squaled = 0;
			}

			drawTexturedModalRect(j + col, k + line + 58 - x - start, imgColumn * 16, imgLine * 16 + (16 - x), 16, 16 - (16 - x));
			start = start + 16;

			if (x == 0 || squaled == 0)
				break;
		}

		int i = mc.renderEngine.getTexture(DefaultProps.TEXTURE_PATH_GUI + "/mixer_gui.png");

		mc.renderEngine.bindTexture(i);
		drawTexturedModalRect(j + col, k + line, 176, 0, 16, 60);
	}
}