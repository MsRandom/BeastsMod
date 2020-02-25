package random.beasts.common.network;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import random.beasts.common.network.packet.PacketTrimolaAttack;

import java.util.function.Supplier;

public class BeastsPacketHandler {

	private static int ID = 1;

	public static void initPackets() {
		registerMessage(PacketTrimolaAttack.Handler::new, PacketTrimolaAttack.class);
	}

	private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Supplier<IMessageHandler<REQ, REPLY>> handler, Class<REQ> message) {
		IMessageHandler<REQ, REPLY> h = handler.get();
		BeastsReference.NETWORK_CHANNEL.registerMessage(h, message, ID, Dist.CLIENT);
		BeastsReference.NETWORK_CHANNEL.registerMessage(h, message, ID, Dist.SERVER);
		ID++;
	}
}
