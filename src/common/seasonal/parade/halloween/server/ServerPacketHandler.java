package seasonal.parade.halloween.server;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ServerPacketHandler implements IPacketHandler {
	/**
	 * Only packets sent from clients to a server arrive here.
	 */
	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload payload, Player player){
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(payload.data));
		EntityPlayer sender = (EntityPlayer) player;
	
		/*
		 * Now do data.read... to retrieve your packet data It would be wise to start all your packets with an Int to distinguish them
		 * 
		 * to send a response packet to the player who sent the packet to the server, use manager.addToSendQueue
		 * 
		 * you can also use FMLCommonHandler.instance().getMinecraftServerInstance ().getConfigurationManager().sendToAllNear or one of the
		 * similar methods there to send packets to ALL players
		 */
	}
}