package random.beasts.api.world.biome.underground;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import random.beasts.api.main.BeastsReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = BeastsReference.ID)
public class UndergroundGenerationEvents {
    private static final Map<ChunkPos, BlockPos> QUEUED = new HashMap<>();

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Biome> event) {
        BeastsReference.NETWORK_CHANNEL.registerMessage(new MessageUpdateBiomes.Handler(), MessageUpdateBiomes.class, 0, Side.CLIENT);
        CapabilityManager.INSTANCE.register(UndergroundGenerationCapabilities.UndergroundBiomes.class, new UndergroundGenerationCapabilities.UndergroundBiomeStorage(), UndergroundGenerationCapabilities.UndergroundBiomes::new);
    }

    @SubscribeEvent
    public static void init(AttachCapabilitiesEvent<Chunk> event) {
        event.addCapability(UndergroundBiome.KEY, new UndergroundGenerationCapabilities());
    }

    @SubscribeEvent
    public static void generate(PopulateChunkEvent.Post event) {
        World world = event.getWorld();
        Random rand = event.getRand();
        if (!world.isRemote && world.provider.getDimension() == 0) {
            ChunkPos current = new ChunkPos(event.getChunkX(), event.getChunkZ());
            BlockPos pos = new BlockPos(current.x * 16, 0, current.z * 16);
            for (UndergroundBiome undergroundBiome : UndergroundBiome.getRegistered()) {
                if (undergroundBiome.getBiome() == null || undergroundBiome.getBiome() == world.getBiome(pos)) {
                    if (rand.nextInt(undergroundBiome.getRarity()) == 0 && (undergroundBiome.getCondition() == null || undergroundBiome.getCondition().test(event.getWorld(), pos))) {
                        int height = undergroundBiome.getHeight() == null ? rand.nextInt(45) + 10 : undergroundBiome.getHeight().generateInt(rand);
                        pos = new BlockPos(pos.getX(), height, pos.getZ());
                        if (QUEUED.containsKey(current)) {
                            generate(world, QUEUED.get(current), undergroundBiome, height, rand);
                            QUEUED.remove(current);
                        }
                        Consumer<BlockPos> generate = p -> {
                            ChunkPos c = new ChunkPos(p);
                            if (current.equals(c)) generate(world, p, undergroundBiome, height, rand);
                            else QUEUED.put(c, p);
                        };
                        Supplier<Integer> size = () -> undergroundBiome.getSize() == null ? rand.nextInt(35) + 48 : undergroundBiome.getSize().generateInt(rand);
                        generate.accept(pos);
                        for (int i = 1; i < size.get() / 16; ++i)
                            for (int j = 1; j < size.get() / 16; ++j)
                                generate.accept(pos.add(i * 16, 0, j * 16));
                    }
                }
            }
        }
    }

    private static void generate(World world, BlockPos p, UndergroundBiome undergroundBiome, int height, Random rand) {
        Chunk chunk = world.getChunkFromBlockCoords(p);
        UndergroundGenerationCapabilities.UndergroundBiomes biomes = chunk.getCapability(UndergroundGenerationCapabilities.CAPABILITY, null);
        if (biomes != null) {
            byte biome = (byte) (Biome.getIdForBiome(undergroundBiome) & 255);
            biomes.blockBiomeArray[Math.min(height, 255) >> 5] = biome;
            BeastsReference.NETWORK_CHANNEL.sendToAll(new MessageUpdateBiomes(p, biome));
            MinecraftForge.EVENT_BUS.post(new UndergroundBiomeEvent.Generate(world, rand, p));
            undergroundBiome.populate(world, rand, p);
            undergroundBiome.decorate(world, rand, p);
            chunk.markDirty();
        }
    }
}
