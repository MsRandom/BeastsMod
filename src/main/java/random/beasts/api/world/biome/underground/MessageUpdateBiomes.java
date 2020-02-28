package random.beasts.api.world.biome.underground;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

//TODO is there a way to do this using forge registries instead of the deprecated mc ones?
public class MessageUpdateBiomes {
    private UndergroundBiomeBounds bounds;

    public MessageUpdateBiomes(UndergroundBiomeBounds bounds) {
        this.bounds = bounds;
    }

    public MessageUpdateBiomes() {
    }

    public static MessageUpdateBiomes fromBytes(PacketBuffer buf) {
        MessageUpdateBiomes msg = new MessageUpdateBiomes();
        msg.bounds = new UndergroundBiomeBounds((UndergroundBiome) Registry.BIOME.getByValue(buf.readInt()), buf.readInt(), buf.readByte(), buf.readInt(), buf.readInt(), buf.readByte(), buf.readInt());
        return msg;
    }

    public static void toBytes(MessageUpdateBiomes msg, PacketBuffer buf) {
        buf.writeInt(Registry.BIOME.getId(msg.bounds.biome));
        buf.writeInt(msg.bounds.minX);
        buf.writeByte(msg.bounds.minY);
        buf.writeInt(msg.bounds.minZ);
        buf.writeInt(msg.bounds.maxX);
        buf.writeByte(msg.bounds.maxY);
        buf.writeInt(msg.bounds.maxZ);
    }

    public static void onMessage(MessageUpdateBiomes message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            UndergroundBiomeBounds bounds = message.bounds;
            for (int x = bounds.minX; x < bounds.maxX; x++) {
                for (int z = bounds.minZ; z < bounds.maxZ; z++) {
                    ChunkPos pos = new ChunkPos(x, z);
                    UndergroundBiome[] biomes;
                    if (UndergroundBiome.GENERATED.containsKey(pos)) biomes = UndergroundBiome.GENERATED.get(pos);
                    else {
                        biomes = new UndergroundBiome[8];
                        UndergroundBiome.GENERATED.put(pos, biomes);
                    }
                    for (int y = bounds.minY; y < bounds.maxY; y++) biomes[y] = bounds.biome;
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
