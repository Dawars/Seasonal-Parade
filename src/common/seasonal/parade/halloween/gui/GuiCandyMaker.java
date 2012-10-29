package seasonal.parade.halloween.gui;

import org.lwjgl.opengl.GL11;

import seasonal.parade.halloween.DefaultProps;
import seasonal.parade.halloween.TileCandyMaker;
import seasonal.parade.halloween.TileMixer;

import net.minecraft.src.Block;
import net.minecraft.src.Container;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.StatCollector;
import net.minecraft.src.TileEntityFurnace;
import net.minecraftforge.client.ForgeHooksClient;

public class GuiCandyMaker extends HalloweenGui{
    private TileCandyMaker inventory;

	public GuiCandyMaker(InventoryPlayer inventoryplayer, TileCandyMaker candyMaker) {
		super(new ContainerCandyMaker(inventoryplayer, candyMaker), candyMaker);
		this.inventory = candyMaker;
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		super.drawGuiContainerForegroundLayer();
	}
	/**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	this.fontRenderer.drawString("Candy Maker", 8, 5, 0x404040);
		this.fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		int i = mc.renderEngine.getTexture(DefaultProps.TEXTURE_PATH_GUI + "/candy_maker_gui.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(i);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		TileCandyMaker candyMaker = (TileCandyMaker) tile;
		//Arrow
		int var7 = this.inventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(j + 92, k + 48, 176, 68, var7 + 1, 16);
		
		//Tanks
		
		if(candyMaker.tankCandy.getLiquid() != null)
			displayGauge(j, k, 22, 67, candyMaker.getScaledCandy(47), candyMaker.tankCandy.getLiquid().itemID);
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

			drawTexturedModalRect(j + col, k + line + 47 - x - start, imgColumn * 16, imgLine * 16 + (16 - x), 16, 16 - (16 - x));
			start = start + 16;

			if (x == 0 || squaled == 0)
				break;
		}

		int i = mc.renderEngine.getTexture(DefaultProps.TEXTURE_PATH_GUI + "/candy_maker_gui.png");

		mc.renderEngine.bindTexture(i);
		drawTexturedModalRect(j + col, k + line, 176, 0, 16, 60);
	}
}