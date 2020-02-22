package random.beasts.api.world.biome.underground;

import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import random.beasts.api.main.BeastsReference;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = BeastsReference.ID)
public class UndergroundGenerationEvents {
    private static final Map<ChunkPos, UndergroundBiomeBounds> QUEUED = new HashMap<>();
    private static final List<Tuple<ChunkPos, UndergroundBiomeBounds>> FINAL = new ArrayList<>();
    private static int gen = 0;
    private static int rem = 0;

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Biome> event) {
        BeastsReference.NETWORK_CHANNEL.registerMessage(new MessageUpdateBiomes.Handler(), MessageUpdateBiomes.class, 0, Side.CLIENT);
        CapabilityManager.INSTANCE.register(UndergroundGenerationCapabilities.UndergroundBiomes.class, new UndergroundGenerationCapabilities.UndergroundBiomeStorage(), UndergroundGenerationCapabilities.UndergroundBiomes::new);
    }

    @SubscribeEvent
    public static void init(AttachCapabilitiesEvent<World> event) {
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

                    int centerX = current.x % 9 == 0 ? current.x : current.x % 9 < 5 ? current.x - current.x % 9 : current.x + current.x % 9;
                    int centerZ = current.z % 9 == 0 ? current.z : current.z % 9 < 5 ? current.z - current.z % 9 : current.z + current.z % 9;
                    Random random = new Random(new BlockPos(centerX, 0, centerZ).toLong());
                    int height = undergroundBiome.getHeight() == null ? random.nextInt(45) + 10 : undergroundBiome.getHeight().generateInt(random);
                    Supplier<Integer> size = () -> undergroundBiome.getSize() == null ? random.nextInt(35) + 48 : undergroundBiome.getSize().generateInt(random);
                    UndergroundBiomeBounds bounds = new UndergroundBiomeBounds(undergroundBiome, centerX, (byte) (height >> 5), centerZ, centerX + (size.get() >> 4), (byte) (height >> 5), centerZ + (size.get() >> 4));

                    if (random.nextInt(undergroundBiome.getRarity()) == 0 && (undergroundBiome.getCondition() == null || undergroundBiome.getCondition().test(event.getWorld(), pos))
                            && current.x >= bounds.minX && current.x <= bounds.maxX && current.z >= bounds.minZ && current.z <= bounds.maxZ) {
                        pos = new BlockPos(pos.getX(), height, pos.getZ());
                        BlockPos f = pos;
                        Consumer<ChunkPos> generate = c -> {
                            if (current.equals(c)) generate(world, f, bounds, undergroundBiome, rand);
                        };
                        generate.accept(current);
                        for (int i = 1; i < size.get() / 16; ++i)
                            for (int j = 1; j < size.get() / 16; ++j)
                                generate.accept(new ChunkPos(current.x + i, current.z + j));
                    }
                }
            }
        }
    }

    private static void generate(World world, BlockPos pos, UndergroundBiomeBounds bounds, UndergroundBiome undergroundBiome, Random rand) {
        UndergroundGenerationCapabilities.UndergroundBiomes biomes = world.getCapability(UndergroundGenerationCapabilities.CAPABILITY, null);
        if (biomes != null) {
            byte biome = (byte) (Biome.getIdForBiome(undergroundBiome) & 255);
            biomes.biomeList.add(bounds);
            BeastsReference.NETWORK_CHANNEL.sendToAll(new MessageUpdateBiomes(bounds));
            MinecraftForge.EVENT_BUS.post(new UndergroundBiomeEvent.Generate(world, rand, pos, bounds));
            undergroundBiome.populate(world, rand, pos, bounds);
            undergroundBiome.decorate(world, rand, pos, bounds);
        }
    }
}
