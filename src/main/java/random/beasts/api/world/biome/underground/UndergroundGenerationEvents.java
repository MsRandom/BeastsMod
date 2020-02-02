package random.beasts.api.world.biome.underground;

import net.minecraft.util.math.BlockPos;
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
import random.beasts.api.main.BeastsReference;

import java.util.Random;
import java.util.function.Consumer;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = BeastsReference.ID)
public class UndergroundGenerationEvents {
    @SubscribeEvent
    public static void register(RegistryEvent.Register<Biome> event) {
        CapabilityManager.INSTANCE.register(UndergroundGenerationCapabilities.UndergroundBiomes.class, new UndergroundGenerationCapabilities.UndergroundBiomeStorage(), UndergroundGenerationCapabilities.UndergroundBiomes::new);
    }

    @SubscribeEvent
    public static void init(AttachCapabilitiesEvent<Chunk> event) {
        event.addCapability(UndergroundGenerationCapabilities.KEY, new UndergroundGenerationCapabilities());
        UndergroundGenerationCapabilities.UndergroundBiomes biomes = event.getObject().getCapability(UndergroundGenerationCapabilities.CAPABILITY, null);
        if (biomes != null) {
            for (int i = 0; i < 16; ++i) {
                biomes.blockBiomeArray[i] = event.getObject().getBiomeArray();
            }
        }
    }

    @SubscribeEvent
    public static void generate(PopulateChunkEvent.Post event) {
        World world = event.getWorld();
        Random rand = event.getRand();
        if (!world.isRemote) {
            BlockPos pos = new BlockPos(event.getChunkX() * 16, 0, event.getChunkZ() * 16);
            for (UndergroundBiome undergroundBiome : UndergroundBiome.getRegistered()) {
                if (undergroundBiome.getBiome() == null || undergroundBiome.getBiome() == world.getBiome(pos)) {
                    if (rand.nextInt(undergroundBiome.getRarity()) == 0 && (undergroundBiome.getCondition() == null || undergroundBiome.getCondition().test(event.getWorld(), pos))) {
                        int height = undergroundBiome.getHeight() == null ? rand.nextInt(50) + 10 : undergroundBiome.getHeight().generateInt(rand);
                        pos = new BlockPos(pos.getX(), height, pos.getZ());
                        Consumer<BlockPos> generate = p -> {
                            Chunk chunk = world.getChunkFromBlockCoords(p);
                            UndergroundGenerationCapabilities.UndergroundBiomes biomes = chunk.getCapability(UndergroundGenerationCapabilities.CAPABILITY, null);
                            if (biomes != null) {
                                biomes.blockBiomeArray[event.getChunkX() << 4 | event.getChunkZ()][height / 16] = (byte) (Biome.getIdForBiome(undergroundBiome) & 255);
                                MinecraftForge.EVENT_BUS.post(new UndergroundBiomeEvent.Generate(world, rand, p));
                                undergroundBiome.populate(world, rand, p);
                                undergroundBiome.decorate(world, rand, p);
                            }
                        };
                        int size = undergroundBiome.getSize() == null ? rand.nextInt(48) + 16 : undergroundBiome.getSize().generateInt(rand);
                        generate.accept(pos);
                        for (int i = 1; i < size / 16; ++i) generate.accept(pos.add(i * 16, 0, i * 16));
                    }
                }
            }
        }
    }
}