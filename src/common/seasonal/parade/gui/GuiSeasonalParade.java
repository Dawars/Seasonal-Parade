package seasonal.parade.gui;

import java.util.ArrayList;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.IInventory;
import net.minecraft.src.TileEntity;

import org.lwjgl.opengl.GL11;

import seasonal.parade.halloween.common.DefaultProps;

public abstract class GuiSeasonalParade extends GuiContainer {

	private GuiSeasonalParade gui;

	protected TileEntity tile;

	public GuiSeasonalParade(SeasonalParadeContainer container, IInventory inventory) {
		super(container);
		this.gui = this;
		if (inventory instanceof TileEntity)
			tile = (TileEntity) inventory;

	}

}