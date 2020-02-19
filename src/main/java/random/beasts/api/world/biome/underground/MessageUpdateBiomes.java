package random.beasts.api.world.biome.underground;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageUpdateBiomes implements IMessage {
    private BlockPos pos;
    private byte biome;

    public MessageUpdateBiomes(BlockPos pos, byte biome) {
        this.pos = pos;
        this.biome = biome;
    }

    public MessageUpdateBiomes() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readByte(), buf.readInt());
        biome = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeByte(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeByte(biome);
    }

    public static class Handler implements IMessageHandler<MessageUpdateBiomes, IMessage> {
        @Override
        public IMessage onMessage(MessageUpdateBiomes message, MessageContext ctx) {
            int x = message.pos.getX() & 15;
            int z = message.pos.getZ() & 15;
            Chunk chunk = Minecraft.getMinecraft().world.getChunkFromBlockCoords(message.pos);
            UndergroundGenerationCapabilities.UndergroundBiomes biomes = chunk.getCapability(UndergroundGenerationCapabilities.CAPABILITY, null);
            if (biomes != null) {
                biomes.blockBiomeArray[Math.min(message.pos.getY(), 255) >> 4][x << 4 | z] = message.biome;
                chunk.markDirty();
            }
            return null;
        }
    }
}
