package random.beasts.world.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import random.beasts.main.BeastsReference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class UndergroundBiome {
    private static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(BeastsReference.ID + ":underground_biomes");
    private static final List<UndergroundBiome> REGISTERED = new ArrayList<>();
    private static ImmutableList<UndergroundBiome> biomeCache;
    private final List<Biome.SpawnListEntry> spawnableCreatureList = new ArrayList<>();
    private final int rarity;
    private final Biome biome;
    private final BiPredicate<World, BlockPos> condition;
    private final Range<Integer> size;
    private String name;

    public UndergroundBiome(int rarity) {
        this(rarity, null, null, null);
    }

    public UndergroundBiome(int rarity, BiPredicate<World, BlockPos> conditions) {
        this(rarity, conditions, null);
    }

    public UndergroundBiome(int rarity, BiPredicate<World, BlockPos> conditions, Range<Integer> size) {
        this(rarity, null, conditions, size);
    }

    public UndergroundBiome(int rarity, Biome baseBiome) {
        this(rarity, baseBiome, null);
    }

    public UndergroundBiome(int rarity, Biome baseBiome, Range<Integer> size) {
        this(rarity, baseBiome, null, size);
    }

    public UndergroundBiome(int rarity, Biome baseBiome, BiPredicate<World, BlockPos> conditions, Range<Integer> size) {
        this.rarity = rarity;
        this.biome = baseBiome;
        this.condition = conditions;
        this.size = size;
    }

    public static void addSpawns(Biome.SpawnListEntry entry, UndergroundBiome... biomes) {
        for (UndergroundBiome biome : biomes) biome.addSpawns(new Biome.SpawnListEntry[]{entry});
    }

    public static UndergroundBiome getBiome(World world, BlockPos pos) {
        if (world.isRemote) {
            //get from server
            return null;
        } else {
            Chunk chunk = world.getChunkFromBlockCoords(pos);
            if (chunk.hasCapability(GenerationCapabilities.CAPABILITY, null)) {
                int biome = Objects.requireNonNull(chunk.getCapability(GenerationCapabilities.CAPABILITY, null)).blockBiomeArray[(pos.getX() >> 4) << 4 | (pos.getZ() >> 4)][pos.getY() / 16];
                return REGISTERED.stream().filter(v -> v.name.hashCode() == biome).findAny().orElse(null);
            }
            return null;
        }
    }

    public static List<UndergroundBiome> getRegistered() {
        return biomeCache == null || biomeCache.size() != REGISTERED.size() ? biomeCache = ImmutableList.copyOf(REGISTERED) : biomeCache;
    }

    public static void register(UndergroundBiome biome, String name) {
        biome.name = name;
        if (name.hashCode() == -1) throw new IllegalArgumentException("Invalid Underground Biome registry name");
        if (!REGISTERED.contains(biome) && REGISTERED.stream().noneMatch(v -> v.name.equals(name)))
            REGISTERED.add(biome);
        else throw new IllegalArgumentException("Underground Biome \"" + name + "\" is already registered.");
    }

    protected void populate(World world, Random rand, BlockPos pos) {
        MinecraftForge.EVENT_BUS.post(new UndergroundBiomeEvent.Populate(world, rand, pos));
    }

    protected void decorate(World world, Random rand, BlockPos pos) {
        MinecraftForge.EVENT_BUS.post(new UndergroundBiomeEvent.Decorate(world, rand, pos));
    }

    protected int getHeight(World world, Random rand, BlockPos pos) {
        return rand.nextInt(50) + 10;
    }

    public List<Biome.SpawnListEntry> getSpawnableList() {
        return getSpawnableList(biome);
    }

    public List<Biome.SpawnListEntry> getSpawnableList(World world, BlockPos pos) {
        return getSpawnableList(biome == null ? world.getBiome(pos) : biome);
    }

    public List<Biome.SpawnListEntry> getSpawnableList(Biome biome) {
        return biome == null ? getSpawnableList() : Stream.concat(spawnableCreatureList.stream(), biome.getSpawnableList(EnumCreatureType.AMBIENT).stream()).collect(Collectors.toList());
    }

    public void addSpawns(Biome.SpawnListEntry... entries) {
        spawnableCreatureList.addAll(Arrays.asList(entries));
    }

    public static class UndergroundBiomeEvent extends Event {

        private final World world;
        private final Random rand;
        private final BlockPos pos;

        public UndergroundBiomeEvent(World world, Random rand, BlockPos pos) {
            this.world = world;
            this.rand = rand;
            this.pos = pos;
        }

        public World getWorld() {
            return this.world;
        }

        public Random getRandom() {
            return this.rand;
        }

        public BlockPos getPosition() {
            return this.pos;
        }

        public static class Populate extends UndergroundBiomeEvent {

            public Populate(World world, Random rand, BlockPos pos) {
                super(world, rand, pos);
            }
        }

        public static class Decorate extends UndergroundBiomeEvent {

            public Decorate(World world, Random rand, BlockPos pos) {
                super(world, rand, pos);
            }
        }

        public static class Generate extends UndergroundBiomeEvent {

            public Generate(World world, Random rand, BlockPos pos) {
                super(world, rand, pos);
            }
        }
    }

    @Mod.EventBusSubscriber(modid = BeastsReference.ID)
    public static class GenerationEvents {
        @SubscribeEvent
        public static void init(AttachCapabilitiesEvent<Chunk> event) {
            event.addCapability(GenerationCapabilities.KEY, new GenerationCapabilities());
        }

        @SubscribeEvent
        public static void generate(PopulateChunkEvent.Post event) {
            World world = event.getWorld();
            Random rand = event.getRand();
            if (!world.isRemote) {
                BlockPos pos = new BlockPos(event.getChunkX() * 16, 0, event.getChunkZ() * 16);
                for (UndergroundBiome undergroundBiome : REGISTERED) {
                    if (undergroundBiome.biome == null || undergroundBiome.biome == world.getBiome(pos)) {
                        if (rand.nextInt(undergroundBiome.rarity) == 0 && undergroundBiome.condition.test(event.getWorld(), pos)) {
                            int height = undergroundBiome.getHeight(world, rand, pos);
                            pos = new BlockPos(pos.getX(), height, pos.getZ());
                            Chunk chunk = world.getChunkFromBlockCoords(pos);
                            GenerationCapabilities.UndergroundBiomes biomes = chunk.getCapability(GenerationCapabilities.CAPABILITY, null);
                            if (biomes != null) {
                                biomes.blockBiomeArray[height / 16][event.getChunkX() << 4 | event.getChunkZ()] = undergroundBiome.name.hashCode();
                                MinecraftForge.EVENT_BUS.post(new UndergroundBiomeEvent.Generate(world, rand, pos));
                                undergroundBiome.populate(world, rand, pos);
                                undergroundBiome.decorate(world, rand, pos);
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static class GenerationCapabilities implements ICapabilityProvider {

        @CapabilityInject(UndergroundBiome.class)
        public static final Capability<UndergroundBiomes> CAPABILITY = null;
        public static final ResourceLocation KEY = new ResourceLocation(BeastsReference.ID, "underground_biomes");

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CAPABILITY;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return hasCapability(capability, facing) ? CAPABILITY.cast(CAPABILITY.getDefaultInstance()) : null;
        }

        public static class UndergroundBiomes {
            private static final int WORLD_HEIGHT = Byte.MAX_VALUE * 2 + 1;
            private int[][] blockBiomeArray = new int[WORLD_HEIGHT][(WORLD_HEIGHT + 1) / 16];

            public UndergroundBiomes() {
                for (int[] row : blockBiomeArray) Arrays.fill(row, -1);
            }
        }
    }
}
