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
import net.minecraftforge.client.ForgeHooksClient;

public class GuiMixer extends HalloweenGui{

	public GuiMixer(InventoryPlayer inventoryplayer, TileMixer mixer) {
		super(new ContainerMixer(inventoryplayer, mixer), mixer);
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
    	this.fontRenderer.drawString("Mixer", 14, 5, 0x404040);
		this.fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }
    
    protected boolean isMouseOver(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        int var7 = this.guiLeft;
        int var8 = this.guiTop;
        par5 -= var7;
        par6 -= var8;
        return par5 >= par1 - 1 && par5 < par1 + par3 + 1 && par6 >= par2 - 1 && par6 < par2 + par4 + 1;
    }
	
	@Override
    public void drawScreen(int x, int y, float par3){
		super.drawScreen(x, y, par3);
		TileMixer mixer = (TileMixer) tile;
		
        if (this.isMouseOver(59, 21, 16, 47, x, y))//milk
        {
    		
        	if (mixer.tankMilk.getLiquid() != null)
            {
        		this.func_74184_a(mixer.tankMilk.getLiquid().asItemStack(), x, y);
            }
        } 
        else if (this.isMouseOver(116, 21, 16, 47, x, y))//candy
        {
        	if (mixer.tankCandy.getLiquid() != null)
            {
        		this.func_74184_a(mixer.tankCandy.getLiquid().asItemStack(), x, y);
            }
        }
        
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
		//Arrow
		if(mixer.isRunning())
			this.drawTexturedModalRect(j + 85, k + 36, 176, 61, 22, 16);
		
		//Tanks
		
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