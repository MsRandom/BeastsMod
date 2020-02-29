package random.beasts.api.world.biome.underground;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraftforge.common.MinecraftForge;
import random.beasts.api.world.biome.BeastsBiome;
import random.beasts.common.BeastsMod;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UndergroundBiome extends BeastsBiome {
    public static final ResourceLocation KEY = new ResourceLocation(BeastsMod.MOD_ID, "underground_biomes");
    public static final Map<ChunkPos, UndergroundBiome[]> GENERATED = new HashMap<>();
    private static final List<UndergroundBiome> REGISTERED = new ArrayList<>();
    private static ImmutableList<UndergroundBiome> biomeCache;
    private final List<Biome.SpawnListEntry> spawnableCreatureList = new ArrayList<>();
    private final AtomicInteger rarity;
    private final Biome biome;
    private final BiPredicate<World, BlockPos> condition;
    private final RandomValueRange size;
    private final RandomValueRange height;
    private final String name;

    public UndergroundBiome(String name, AtomicInteger rarity) {
        this(name, rarity, null, null, null, null);
    }

    public UndergroundBiome(String name, AtomicInteger rarity, RandomValueRange height) {
        this(name, rarity, null, null, null, height);
    }

    public UndergroundBiome(String name, AtomicInteger rarity, BiPredicate<World, BlockPos> conditions) {
        this(name, rarity, conditions, null);
    }

    public UndergroundBiome(String name, AtomicInteger rarity, BiPredicate<World, BlockPos> conditions, RandomValueRange size) {
        this(name, rarity, null, conditions, size, null);
    }

    public UndergroundBiome(String name, AtomicInteger rarity, BiPredicate<World, BlockPos> conditions, RandomValueRange size, RandomValueRange height) {
        this(name, rarity, null, conditions, size, height);
    }

    public UndergroundBiome(String name, AtomicInteger rarity, Biome baseBiome) {
        this(name, rarity, baseBiome, null, null);
    }

    public UndergroundBiome(String name, AtomicInteger rarity, Biome baseBiome, RandomValueRange height) {
        this(name, rarity, baseBiome, null, height);
    }

    public UndergroundBiome(String name, AtomicInteger rarity, Biome baseBiome, RandomValueRange size, RandomValueRange height) {
        this(name, rarity, baseBiome, null, size, height);
    }

    public UndergroundBiome(String name, AtomicInteger rarity, Biome baseBiome, BiPredicate<World, BlockPos> conditions, RandomValueRange size, RandomValueRange height) {
        super(name.replace(' ', '_').toLowerCase(), new Biome.Builder());
        this.name = Objects.requireNonNull(getRegistryName()).getPath();
        this.rarity = rarity;
        this.biome = baseBiome;
        this.condition = conditions;
        this.size = size;
        this.height = height;
        register(this);
    }

    public static void addSpawns(Biome.SpawnListEntry entry, UndergroundBiome... biomes) {
        for (UndergroundBiome biome : biomes) biome.addSpawns(new Biome.SpawnListEntry[]{entry});
    }

    public static List<UndergroundBiome> getRegistered() {
        return biomeCache == null || biomeCache.size() != REGISTERED.size() ? biomeCache = ImmutableList.copyOf(REGISTERED) : biomeCache;
    }

    private static void register(UndergroundBiome biome) {
        if (!REGISTERED.contains(biome) && REGISTERED.stream().noneMatch(v -> v.name.equals(biome.name)))
            REGISTERED.add(biome);
        else throw new IllegalArgumentException("Underground Biome \"" + biome.name + "\" is already registered.");
    }

    @SuppressWarnings("unused")
    public static Biome getBiome(BlockPos pos, Object object) {
        if (!(object instanceof IChunk))
            throw new IllegalArgumentException("Illegal argument for parameter object in UndergroundBiome::getBiome");
        ChunkPos chunk = ((IChunk) object).getPos();
        if (GENERATED.containsKey(chunk)) return GENERATED.get(chunk)[Math.max(Math.min(pos.getY(), 255), 0) >> 5];
        return null;
    }

    public void populate(World world, Random rand, BlockPos pos, UndergroundBiomeBounds bounds) {
        MinecraftForge.EVENT_BUS.post(new UndergroundBiomeEvent.Populate(world, rand, pos, bounds));
    }

    public void decorate(World world, Random rand, BlockPos pos, UndergroundBiomeBounds bounds) {
        MinecraftForge.EVENT_BUS.post(new UndergroundBiomeEvent.Decorate(world, rand, pos, bounds));
    }

    @Deprecated
    @Override
    public final void decorate(GenerationStage.Decoration stage, ChunkGenerator<? extends GenerationSettings> chunkGenerator, IWorld worldIn, long seed, SharedSeedRandom random, BlockPos pos) {
    }

    public List<Biome.SpawnListEntry> getSpawns() {
        return getSpawns(biome);
    }

    public List<Biome.SpawnListEntry> getSpawns(World world, BlockPos pos) {
        return getSpawns(biome == null ? world.getBiome(pos) : biome);
    }

    public List<Biome.SpawnListEntry> getSpawns(Biome biome) {
        return biome == null ? spawnableCreatureList : Stream.concat(spawnableCreatureList.stream(), biome.getSpawns(EntityClassification.AMBIENT).stream()).collect(Collectors.toList());
    }

    public void addSpawns(Biome.SpawnListEntry... entries) {
        spawnableCreatureList.addAll(Arrays.asList(entries));
    }

    public Biome getBiome() {
        return biome;
    }

    public int getRarity() {
        return rarity.get();
    }

    public BiPredicate<World, BlockPos> getCondition() {
        return condition;
    }

    public RandomValueRange getSize() {
        return size;
    }

    public RandomValueRange getHeight() {
        return height;
    }
}
