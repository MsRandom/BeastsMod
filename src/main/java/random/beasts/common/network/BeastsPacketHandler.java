package random.beasts.common.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import random.beasts.api.main.BeastsReference;
import random.beasts.common.network.packet.PacketTrimolaAttack;

public class BeastsPacketHandler {

	public static SimpleNetworkWrapper net;
	private static int ID = 0;

	public static void initPackets() {
		net = NetworkRegistry.INSTANCE.newSimpleChannel(BeastsReference.ID.toUpperCase());
		registerMessage(PacketTrimolaAttack.Handler.class, PacketTrimolaAttack.class);
	}

	private static void registerMessage(Class packet, Class message) {
		net.registerMessage(packet, message, ID, Side.CLIENT);
		net.registerMessage(packet, message, ID, Side.SERVER);
		ID++;
	}
}