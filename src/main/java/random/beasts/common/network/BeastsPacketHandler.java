package random.beasts.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import random.beasts.common.BeastsMod;
import random.beasts.common.network.packet.PacketTrimolaAttack;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class BeastsPacketHandler {

	private static int ID = 1;

	public static void initPackets() {
		registerMessage(PacketTrimolaAttack.class, (msg, buf) -> buf.writeInt(msg.entityId), buf -> new PacketTrimolaAttack(buf.readInt()), PacketTrimolaAttack::onMessage);
	}

	private static <MSG> void registerMessage(Class<MSG> message, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer) {
		BeastsMod.NETWORK_CHANNEL.registerMessage(ID, message, encoder, decoder, messageConsumer);
		ID++;
	}
}
