/**
 * Copyright (c) SpaceToad, 2011
 * http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package seasonal.parade.halloween.utils;

import java.util.Arrays;
import java.util.LinkedList;

import seasonal.parade.halloween.BlockIndex;
import seasonal.parade.halloween.Halloween;
import seasonal.parade.halloween.network.ISynchronizedTile;
import seasonal.parade.halloween.network.PacketUpdate;
import buildcraft.api.core.IAreaProvider;
import buildcraft.api.core.LaserKind;
import buildcraft.api.core.Orientations;
import buildcraft.api.core.Position;
import buildcraft.api.liquids.ILiquid;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeEntry;
import buildcraft.api.transport.IPipedItem;
import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryLargeChest;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;

public class Utils {

	public static final float pipeMinPos = 0.25F;
	public static final float pipeMaxPos = 0.75F;
	public static float pipeNormalSpeed = 0.01F;

	

	
	public static void dropItems(World world, ItemStack stack, int i, int j, int k) {
		if(stack.stackSize <= 0)
			return;

		float f1 = 0.7F;
		double d = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
		double d1 = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
		double d2 = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
		EntityItem entityitem = new EntityItem(world, i + d, j + d1, k + d2, stack);
		entityitem.delayBeforeCanPickup = 10;

		world.spawnEntityInWorld(entityitem);
	}

	public static void dropItems(World world, IInventory inventory, int i, int j, int k) {
		for (int l = 0; l < inventory.getSizeInventory(); ++l) {
			ItemStack items = inventory.getStackInSlot(l);

			if (items != null && items.stackSize > 0)
				dropItems(world, inventory.getStackInSlot(l).copy(), i, j, k);
		}
	}

	public static TileEntity getTile(World world, Position pos, Orientations step) {
		Position tmp = new Position(pos);
		tmp.orientation = step;
		tmp.moveForwards(1.0);

		return world.getBlockTileEntity((int) tmp.x, (int) tmp.y, (int) tmp.z);
	}

	/**
	 * Ensures that the given inventory is the full inventory, i.e. takes double chests into account.
	 * @param inv
	 * @return Modified inventory if double chest, unmodified otherwise.
	 */
	public static IInventory getInventory(IInventory inv) {
		if (inv instanceof TileEntityChest) {
			TileEntityChest chest = (TileEntityChest) inv;
			Position pos = new Position(chest.xCoord, chest.yCoord, chest.zCoord);
			TileEntity tile;
			IInventory chest2 = null;
			tile = Utils.getTile(chest.worldObj, pos, Orientations.XNeg);
			if (tile instanceof TileEntityChest)
				chest2 = (IInventory) tile;
			tile = Utils.getTile(chest.worldObj, pos, Orientations.XPos);
			if (tile instanceof TileEntityChest)
				chest2 = (IInventory) tile;
			tile = Utils.getTile(chest.worldObj, pos, Orientations.ZNeg);
			if (tile instanceof TileEntityChest)
				chest2 = (IInventory) tile;
			tile = Utils.getTile(chest.worldObj, pos, Orientations.ZPos);
			if (tile instanceof TileEntityChest)
				chest2 = (IInventory) tile;
			if (chest2 != null)
				return new InventoryLargeChest("", inv, chest2);
		}
		return inv;
	}

	public static IAreaProvider getNearbyAreaProvider(World world, int i, int j, int k) {
		TileEntity a1 = world.getBlockTileEntity(i + 1, j, k);
		TileEntity a2 = world.getBlockTileEntity(i - 1, j, k);
		TileEntity a3 = world.getBlockTileEntity(i, j, k + 1);
		TileEntity a4 = world.getBlockTileEntity(i, j, k - 1);
		TileEntity a5 = world.getBlockTileEntity(i, j + 1, k);
		TileEntity a6 = world.getBlockTileEntity(i, j - 1, k);

		if (a1 instanceof IAreaProvider)
			return (IAreaProvider) a1;

		if (a2 instanceof IAreaProvider)
			return (IAreaProvider) a2;

		if (a3 instanceof IAreaProvider)
			return (IAreaProvider) a3;

		if (a4 instanceof IAreaProvider)
			return (IAreaProvider) a4;

		if (a5 instanceof IAreaProvider)
			return (IAreaProvider) a5;

		if (a6 instanceof IAreaProvider)
			return (IAreaProvider) a6;

		return null;
	}



	public static void handleBufferedDescription(ISynchronizedTile tileSynch) {
		TileEntity tile = (TileEntity) tileSynch;
		BlockIndex index = new BlockIndex(tile.xCoord, tile.yCoord, tile.zCoord);

		if (Halloween.bufferedDescriptions.containsKey(index)) {

			PacketUpdate payload = Halloween.bufferedDescriptions.get(index);
			Halloween.bufferedDescriptions.remove(index);

			tileSynch.handleDescriptionPacket(payload);
			tileSynch.postPacketHandling(payload);
		}
	}

	public static int liquidId(int blockId) {
		if (blockId == Block.waterStill.blockID || blockId == Block.waterMoving.blockID)
			return Block.waterStill.blockID;
		else if (blockId == Block.lavaStill.blockID || blockId == Block.lavaMoving.blockID)
			return Block.lavaStill.blockID;
		else if (Block.blocksList[blockId] instanceof ILiquid)
			return ((ILiquid) Block.blocksList[blockId]).stillLiquidId();
		else
			return 0;
	}

	
	

	public static void readStacksFromNBT(NBTTagCompound nbt, String name, ItemStack[] stacks) {
		NBTTagList nbttaglist = nbt.getTagList(name);

		for (int i = 0; i < stacks.length; ++i)
			if (i < nbttaglist.tagCount()) {
				NBTTagCompound nbttagcompound2 = (NBTTagCompound) nbttaglist.tagAt(i);

				stacks[i] = ItemStack.loadItemStackFromNBT(nbttagcompound2);
			} else
				stacks[i] = null;
	}

	public static void writeStacksToNBT(NBTTagCompound nbt, String name, ItemStack[] stacks) {
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < stacks.length; ++i) {
			NBTTagCompound cpt = new NBTTagCompound();
			nbttaglist.appendTag(cpt);
			if (stacks[i] != null)
				stacks[i].writeToNBT(cpt);

		}

		nbt.setTag(name, nbttaglist);
	}

	public static ItemStack consumeItem(ItemStack stack) {
		if (stack.stackSize == 1) {
			if (stack.getItem().getContainerItem() != null)
				return new ItemStack(stack.getItem().getContainerItem(), 1);
			else
				return null;
		} else {
			stack.splitStack(1);

			return stack;
		}
	}

	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public static int[] concat(int[] first, int[] second) {
		int[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public static float[] concat(float[] first, float[] second) {
		float[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

}