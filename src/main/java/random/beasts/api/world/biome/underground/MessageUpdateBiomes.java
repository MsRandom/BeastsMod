package random.beasts.api.world.biome.underground;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageUpdateBiomes implements IMessage {
    private UndergroundBiomeBounds bounds;

    public MessageUpdateBiomes(UndergroundBiomeBounds bounds) {
        this.bounds = bounds;
    }

    public MessageUpdateBiomes() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        bounds = new UndergroundBiomeBounds((UndergroundBiome) Biome.getBiome(buf.readByte() & 255), buf.readInt(), buf.readByte(), buf.readInt(), buf.readInt(), buf.readByte(), buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(Biome.getIdForBiome(bounds.biome) & 255);
        buf.writeInt(bounds.minX);
        buf.writeByte(bounds.minY);
        buf.writeInt(bounds.minZ);
        buf.writeInt(bounds.maxX);
        buf.writeByte(bounds.maxY);
        buf.writeInt(bounds.maxZ);
    }

    public static class Handler implements IMessageHandler<MessageUpdateBiomes, IMessage> {
        @Override
        public IMessage onMessage(MessageUpdateBiomes message, MessageContext ctx) {
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
            return null;
        }
    }
}
