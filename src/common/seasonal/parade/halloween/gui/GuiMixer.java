package seasonal.parade.halloween.gui;

import org.lwjgl.opengl.GL11;

import seasonal.parade.halloween.DefaultProps;
import seasonal.parade.halloween.TileMixer;

import net.minecraft.src.Block;
import net.minecraft.src.Container;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraftforge.client.ForgeHooksClient;

public class GuiMixer extends HalloweenGui{

	public GuiMixer(InventoryPlayer inventoryplayer, TileMixer mixer) {
		super(new ContainerMixer(inventoryplayer, mixer), mixer);
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		super.drawGuiContainerForegroundLayer();
		
		fontRenderer.drawString("Mixer", 14, 6, 0x404040);
		fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		int i = mc.renderEngine.getTexture(DefaultProps.TEXTURE_PATH_GUI + "/mixer_gui.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(i);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

		TileMixer mixer = (TileMixer) tile;
		if(mixer.tankMilk.getLiquid() != null)
			displayGauge(j, k, 21, 59, mixer.getScaledMilk(47), mixer.tankMilk.getLiquid().itemID);
		if(mixer.tankCandy.getLiquid() != null)
			displayGauge(j, k, 21, 116, mixer.getScaledCandy(47), mixer.tankCandy.getLiquid().itemID);
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

		int i = mc.renderEngine.getTexture(DefaultProps.TEXTURE_PATH_GUI + "/mixer_gui.png");

		mc.renderEngine.bindTexture(i);
		drawTexturedModalRect(j + col, k + line, 176, 0, 16, 60);
	}
}