package seasonal.parade.halloween.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import seasonal.parade.halloween.DefaultProps;
import seasonal.parade.halloween.LiquidHelper;
import seasonal.parade.halloween.TileCandyMaker;
import seasonal.parade.halloween.TileMixer;

import net.minecraft.src.Block;
import net.minecraft.src.Container;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.Slot;
import net.minecraft.src.StatCollector;
import net.minecraft.src.TileEntityFurnace;
import net.minecraftforge.client.ForgeHooksClient;

public class GuiCandyMaker extends HalloweenGui{
    private TileCandyMaker inventory;

	public GuiCandyMaker(InventoryPlayer inventoryplayer, TileCandyMaker candyMaker) {
		super(new ContainerCandyMaker(inventoryplayer, candyMaker), candyMaker);
		this.inventory = candyMaker;
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
        if (this.isMouseOver(67, 22, 16, 47, x, y))//if mouse over
        {
    		TileCandyMaker candyMaker = (TileCandyMaker) tile;
    		
        	if(candyMaker.tankCandy.getLiquid() != null && candyMaker.tankCandy.getLiquid().itemID != 0)
            {
        		ItemStack liquidItem = new ItemStack(candyMaker.tankCandy.getLiquid().asItemStack().getItem());
        		this.drawTooltip(liquidItem, x, y);
            }
        }
	}
	protected void drawTooltip(ItemStack item, int x, int y)
    {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        List text = new ArrayList();
        text.add(item.getItem().getItemDisplayName(item));

        if (!text.isEmpty())
        {
            int var5 = 0;
            Iterator var6 = text.iterator();

            while (var6.hasNext())
            {
                String var7 = (String)var6.next();
                int var8 = this.fontRenderer.getStringWidth(var7);

                if (var8 > var5)
                {
                    var5 = var8;
                }
            }

            int drawX = x + 12;
            int drawY = y - 12;
            int var9 = 8;

            if (text.size() > 1)
            {
                var9 += 2 + (text.size() - 1) * 10;
            }

            this.zLevel = 300.0F;
            itemRenderer.zLevel = 300.0F;
            int var10 = -267386864;
            this.drawGradientRect(drawX - 3, drawY - 4, drawX + var5 + 3, drawY - 3, var10, var10);
            this.drawGradientRect(drawX - 3, drawY + var9 + 3, drawX + var5 + 3, drawY + var9 + 4, var10, var10);
            this.drawGradientRect(drawX - 3, drawY - 3, drawX + var5 + 3, drawY + var9 + 3, var10, var10);
            this.drawGradientRect(drawX - 4, drawY - 3, drawX - 3, drawY + var9 + 3, var10, var10);
            this.drawGradientRect(drawX + var5 + 3, drawY - 3, drawX + var5 + 4, drawY + var9 + 3, var10, var10);
            int var11 = 1347420415;
            int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
            this.drawGradientRect(drawX - 3, drawY - 3 + 1, drawX - 3 + 1, drawY + var9 + 3 - 1, var11, var12);
            this.drawGradientRect(drawX + var5 + 2, drawY - 3 + 1, drawX + var5 + 3, drawY + var9 + 3 - 1, var11, var12);
            this.drawGradientRect(drawX - 3, drawY - 3, drawX + var5 + 3, drawY - 3 + 1, var11, var11);
            this.drawGradientRect(drawX - 3, drawY + var9 + 2, drawX + var5 + 3, drawY + var9 + 3, var12, var12);

            for (int var13 = 0; var13 < text.size(); ++var13)
            {
                String var14 = (String)text.get(var13);

                if (var13 == 0)
                {
                    var14 = "\u00a7" + Integer.toHexString(item.getRarity().rarityColor) + var14;
                }
                else
                {
                    var14 = "\u00a77" + var14;
                }

                this.fontRenderer.drawStringWithShadow(var14, drawX, drawY, -1);

                if (var13 == 0)
                {
                    drawY += 2;
                }

                drawY += 10;
            }

            this.zLevel = 0.0F;
            itemRenderer.zLevel = 0.0F;
        }
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