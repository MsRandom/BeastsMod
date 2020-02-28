package random.beasts.api.world.biome.underground;

import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import random.beasts.common.BeastsMod;

import java.util.*;
import java.util.function.Supplier;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = BeastsMod.MOD_ID)
public class UndergroundGenerationEvents {
    private static final Map<ChunkPos, UndergroundBiomeBounds> QUEUED = new HashMap<>();
    private static final List<Tuple<ChunkPos, UndergroundBiomeBounds>> FINAL = new ArrayList<>();
    private static int gen = 0;
    private static int rem = 0;

    @SubscribeEvent
    public static void serverStarting(FMLServerStartingEvent event) {
        BeastsMod.NETWORK_CHANNEL.registerMessage(0, MessageUpdateBiomes.class, MessageUpdateBiomes::toBytes, MessageUpdateBiomes::fromBytes, MessageUpdateBiomes::onMessage);
    }

    @SubscribeEvent
    public static void init(AttachCapabilitiesEvent<World> event) {
        event.addCapability(UndergroundBiome.KEY, new UndergroundGenerationCapabilities());
    }

    //not sure if this event will work for what we need here
    @SubscribeEvent
    public static void generate(ChunkEvent.Load event) {
        if (event.getWorld() instanceof World) {
            World world = (World) event.getWorld();
            Random rand = world.getRandom();
            if (!world.isRemote() && world.getDimension().getType() == DimensionType.OVERWORLD) {
                ChunkPos current = new ChunkPos(event.getChunk().getPos().x, event.getChunk().getPos().z);
                BlockPos pos = new BlockPos(current.x * 16, 0, current.z * 16);
                for (UndergroundBiome undergroundBiome : UndergroundBiome.getRegistered()) {
                    if (undergroundBiome.getBiome() == null || undergroundBiome.getBiome() == world.getBiome(pos)) {
                        int gridMax = undergroundBiome.getSize() == null ? 6 : ((int) undergroundBiome.getSize().getMax() >> 4) + 1;
                        int centerX = (current.x / gridMax) * gridMax + gridMax / 2;
                        int centerZ = (current.z / gridMax) * gridMax + gridMax / 2;
                        Random random = new Random(new BlockPos(centerX, 0, centerZ).toLong());
                        int height = undergroundBiome.getHeight() == null ? random.nextInt(45) + 10 : undergroundBiome.getHeight().generateInt(random);
                        Supplier<Integer> size = () -> undergroundBiome.getSize() == null ? random.nextInt(35) + 48 : undergroundBiome.getSize().generateInt(random);
                        int sizeX = (size.get() >> 5);
                        int sizeZ = (size.get() >> 5);
                        UndergroundBiomeBounds bounds = new UndergroundBiomeBounds(undergroundBiome, centerX - sizeX, (byte) (height >> 5), centerZ - sizeZ, centerX + sizeX, (byte) (height >> 5), centerZ + sizeZ);

                        if (random.nextInt(undergroundBiome.getRarity()) == 0 && (undergroundBiome.getCondition() == null || undergroundBiome.getCondition().test(world, pos))
                                && current.x >= bounds.minX && current.x <= bounds.maxX && current.z >= bounds.minZ && current.z <= bounds.maxZ) {
                            generate(world, new BlockPos(pos.getX(), height, pos.getZ()), bounds, undergroundBiome, rand);
                        }
                    }
                }
            }
        }
    }

    private static void generate(World world, BlockPos pos, UndergroundBiomeBounds bounds, UndergroundBiome undergroundBiome, Random rand) {
        world.getCapability(UndergroundGenerationCapabilities.CAPABILITY, null).ifPresent(biomes -> {
            biomes.biomeList.add(bounds);
            BeastsMod.NETWORK_CHANNEL.send(PacketDistributor.DIMENSION.noArg(), new MessageUpdateBiomes(bounds));
            MinecraftForge.EVENT_BUS.post(new UndergroundBiomeEvent.Generate(world, rand, pos, bounds));
            undergroundBiome.populate(world, rand, pos, bounds);
            undergroundBiome.decorate(world, rand, pos, bounds);
        });
    }
}
