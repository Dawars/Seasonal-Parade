package seasonal.parade.halloween;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import seasonal.parade.halloween.network.ISynchronizedTile;
import seasonal.parade.halloween.network.PacketPayload;
import seasonal.parade.halloween.network.PacketTileUpdate;
import seasonal.parade.halloween.network.PacketUpdate;
import seasonal.parade.halloween.network.TilePacketWrapper;
import seasonal.parade.halloween.proxy.CoreProxy;
import seasonal.parade.halloween.utils.Utils;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;

public abstract class HalloweenTile extends TileEntity implements ISynchronizedTile {

	@SuppressWarnings("rawtypes")
	private static Map<Class, TilePacketWrapper> updateWrappers = new HashMap<Class, TilePacketWrapper>();
	@SuppressWarnings("rawtypes")
	private static Map<Class, TilePacketWrapper> descriptionWrappers = new HashMap<Class, TilePacketWrapper>();

	private final TilePacketWrapper descriptionPacket;
	private final TilePacketWrapper updatePacket;

	private boolean init = false;

	public HalloweenTile() {
		if (!updateWrappers.containsKey(this.getClass()))
			updateWrappers.put(this.getClass(), new TilePacketWrapper(this.getClass()));

		if (!descriptionWrappers.containsKey(this.getClass()))
			descriptionWrappers.put(this.getClass(), new TilePacketWrapper(this.getClass()));

		updatePacket = updateWrappers.get(this.getClass());
		descriptionPacket = descriptionWrappers.get(this.getClass());

	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this == player
				.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}
	@Override
	public void updateEntity() {
		if (!init && !isInvalid()) {
			initialize();
			init = true;
		}
	}

	@Override
	public void invalidate()
	{
		init = false;
		super.invalidate();
	}

	public void initialize() {
		Utils.handleBufferedDescription(this);
	}

	public void destroy() {

	}

	public void sendNetworkUpdate() {
		if(CoreProxy.proxy.isSimulating(worldObj))
			CoreProxy.proxy.sendToPlayers(getUpdatePacket(), worldObj, xCoord, yCoord, zCoord, DefaultProps.NETWORK_UPDATE_RANGE);
	}

	@Override
	public Packet getDescriptionPacket() {
		return new PacketTileUpdate(this).getPacket();
	}

	@Override
	public PacketPayload getPacketPayload() {
		return updatePacket.toPayload(this);
	}

	@Override
	public Packet getUpdatePacket() {
		return new PacketTileUpdate(this).getPacket();
	}

	@Override
	public void handleDescriptionPacket(PacketUpdate packet) {
		descriptionPacket.fromPayload(this, packet.payload);
	}

	@Override
	public void handleUpdatePacket(PacketUpdate packet) {
		updatePacket.fromPayload(this, packet.payload);
	}

	@Override
	public void postPacketHandling(PacketUpdate packet) {

	}

}