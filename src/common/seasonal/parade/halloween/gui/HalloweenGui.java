package seasonal.parade.halloween.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.src.Container;
import net.minecraft.src.ContainerDispenser;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityDispenser;

@SideOnly(Side.CLIENT)
public class HalloweenGui extends GuiContainer{

	protected TileEntity tile;

	public HalloweenGui(HalloweenContainer container, IInventory inventory) {
		super(container);

		if (inventory instanceof TileEntity)
			tile = (TileEntity) inventory;
	}

	/**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(){}

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3){}

}
