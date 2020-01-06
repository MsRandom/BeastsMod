package random.beasts.api.world.biome;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import random.beasts.api.main.BeastsReference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class UndergroundBiome extends BeastsBiome {
    private static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(BeastsReference.ID + ":underground_biomes");
    private static final List<UndergroundBiome> REGISTERED = new ArrayList<>();
    private static ImmutableList<UndergroundBiome> biomeCache;
    private final List<Biome.SpawnListEntry> spawnableCreatureList = new ArrayList<>();
    private final int rarity;
    private final Biome biome;
    private final BiPredicate<World, BlockPos> condition;
    private final RandomValueRange size;
    private final RandomValueRange height;
    private final String name;

    public UndergroundBiome(String name, int rarity) {
        this(name, rarity, null, null, null, null);
    }

    public UndergroundBiome(String name, int rarity, RandomValueRange height) {
        this(name, rarity, null, null, null, height);
    }

    public UndergroundBiome(String name, int rarity, BiPredicate<World, BlockPos> conditions) {
        this(name, rarity, conditions, null);
    }

    public UndergroundBiome(String name, int rarity, BiPredicate<World, BlockPos> conditions, RandomValueRange size) {
        this(name, rarity, null, conditions, size, null);
    }

    public UndergroundBiome(String name, int rarity, BiPredicate<World, BlockPos> conditions, RandomValueRange size, RandomValueRange height) {
        this(name, rarity, null, conditions, size, height);
    }

    public UndergroundBiome(String name, int rarity, Biome baseBiome) {
        this(name, rarity, baseBiome, null, null);
    }

    public UndergroundBiome(String name, int rarity, Biome baseBiome, RandomValueRange height) {
        this(name, rarity, baseBiome, null, height);
    }

    public UndergroundBiome(String name, int rarity, Biome baseBiome, RandomValueRange size, RandomValueRange height) {
        this(name, rarity, baseBiome, null, size, height);
    }

    public UndergroundBiome(String name, int rarity, Biome baseBiome, BiPredicate<World, BlockPos> conditions, RandomValueRange size, RandomValueRange height) {
        super(name.replace(' ', '_').toLowerCase(), new BiomeProperties(name));
        this.name = name;
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

    public void populate(World world, Random rand, BlockPos pos) {
        MinecraftForge.EVENT_BUS.post(new UndergroundBiomeEvent.Populate(world, rand, pos));
    }

    public void decorate(World world, Random rand, BlockPos pos) {
        MinecraftForge.EVENT_BUS.post(new UndergroundBiomeEvent.Decorate(world, rand, pos));
    }

    public List<Biome.SpawnListEntry> getSpawnableList() {
        return getSpawnableList(biome);
    }

    public List<Biome.SpawnListEntry> getSpawnableList(World world, BlockPos pos) {
        return getSpawnableList(biome == null ? world.getBiome(pos) : biome);
    }

    public List<Biome.SpawnListEntry> getSpawnableList(Biome biome) {
        return biome == null ? spawnableCreatureList : Stream.concat(spawnableCreatureList.stream(), biome.getSpawnableList(EnumCreatureType.AMBIENT).stream()).collect(Collectors.toList());
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

    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = BeastsReference.ID)
    public static class GenerationEvents {
        @SubscribeEvent
        public static void register(RegistryEvent.Register<Biome> event) {
            CapabilityManager.INSTANCE.register(GenerationCapabilities.UndergroundBiomes.class, new UndergroundBiome.GenerationCapabilities.UndergroundBiomeStorage(), GenerationCapabilities.UndergroundBiomes::new);
        }

        @SubscribeEvent
        public static void init(AttachCapabilitiesEvent<Chunk> event) {
            event.addCapability(GenerationCapabilities.KEY, new GenerationCapabilities());
            GenerationCapabilities.UndergroundBiomes biomes = event.getObject().getCapability(GenerationCapabilities.CAPABILITY, null);
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
                for (UndergroundBiome undergroundBiome : REGISTERED) {
                    if (undergroundBiome.biome == null || undergroundBiome.biome == world.getBiome(pos)) {
                        if (rand.nextInt(undergroundBiome.rarity) == 0 && undergroundBiome.condition.test(event.getWorld(), pos)) {
                            int height = undergroundBiome.height == null ? rand.nextInt(50) + 10 : undergroundBiome.height.generateInt(rand);
                            pos = new BlockPos(pos.getX(), height, pos.getZ());
                            Consumer<BlockPos> generate = p -> {
                                Chunk chunk = world.getChunkFromBlockCoords(p);
                                GenerationCapabilities.UndergroundBiomes biomes = chunk.getCapability(GenerationCapabilities.CAPABILITY, null);
                                if (biomes != null) {
                                    biomes.blockBiomeArray[event.getChunkX() << 4 | event.getChunkZ()][height / 16] = (byte) Biome.getIdForBiome(undergroundBiome);
                                    MinecraftForge.EVENT_BUS.post(new UndergroundBiomeEvent.Generate(world, rand, p));
                                    undergroundBiome.populate(world, rand, p);
                                    undergroundBiome.decorate(world, rand, p);
                                }
                            };
                            int size = undergroundBiome.size == null ? rand.nextInt(48) + 16 : undergroundBiome.size.generateInt(rand);
                            generate.accept(pos);
                            for (int i = 1; i < size / 16; ++i) {
                                generate.accept(pos.add(i * 16, 0, i * 16));
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
            private byte[][] blockBiomeArray = new byte[16][255];
        }

        private static class UndergroundBiomeStorage implements Capability.IStorage<UndergroundBiomes> {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<UndergroundBiomes> capability, UndergroundBiomes instance, EnumFacing side) {
                NBTTagList list = new NBTTagList();
                for (int i = 0; i < 16; ++i) {
                    list.set(i, new NBTTagByteArray(instance.blockBiomeArray[i]));
                }
                return list;
            }

            @Override
            public void readNBT(Capability<UndergroundBiomes> capability, UndergroundBiomes instance, EnumFacing side, NBTBase nbt) {
                NBTTagList list = (NBTTagList) nbt;
                for (int i = 0; i < 16; ++i) {
                    instance.blockBiomeArray[i] = ((NBTTagByteArray) list.get(i)).getByteArray();
                }
            }
        }
    }
}

//orignal chunk getBiome source code
   /*
  public getBiome(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/biome/BiomeProvider;)Lnet/minecraft/world/biome/Biome;
   L0
    LINENUMBER 1298 L0
    ALOAD 1
    INVOKEVIRTUAL net/minecraft/util/math/BlockPos.getX ()I
    BIPUSH 15
    IAND
    ISTORE 3
   L1
    LINENUMBER 1299 L1
    ALOAD 1
    INVOKEVIRTUAL net/minecraft/util/math/BlockPos.getZ ()I
    BIPUSH 15
    IAND
    ISTORE 4
   L2
    LINENUMBER 1300 L2
    ALOAD 0
    GETFIELD net/minecraft/world/chunk/Chunk.blockBiomeArray : [B
    ILOAD 4
    ICONST_4
    ISHL
    ILOAD 3
    IOR
    BALOAD
    SIPUSH 255
    IAND
    ISTORE 5
   L3
    LINENUMBER 1302 L3
    ILOAD 5
    SIPUSH 255
    IF_ICMPNE L4
   L5
    LINENUMBER 1304 L5
    ALOAD 2
    ALOAD 1
    GETSTATIC net/minecraft/init/Biomes.PLAINS : Lnet/minecraft/world/biome/Biome;
    INVOKEVIRTUAL net/minecraft/world/biome/BiomeProvider.getBiome (Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/biome/Biome;)Lnet/minecraft/world/biome/Biome;
    ASTORE 6
   L6
    LINENUMBER 1305 L6
    ALOAD 6
    INVOKESTATIC net/minecraft/world/biome/Biome.getIdForBiome (Lnet/minecraft/world/biome/Biome;)I
    ISTORE 5
   L7
    LINENUMBER 1306 L7
    ALOAD 0
    GETFIELD net/minecraft/world/chunk/Chunk.blockBiomeArray : [B
    ILOAD 4
    ICONST_4
    ISHL
    ILOAD 3
    IOR
    ILOAD 5
    SIPUSH 255
    IAND
    I2B
    BASTORE
   L4
    LINENUMBER 1309 L4
   FRAME APPEND [I I I]
    ILOAD 5
    INVOKESTATIC net/minecraft/world/biome/Biome.getBiome (I)Lnet/minecraft/world/biome/Biome;
    ASTORE 6
   L8
    LINENUMBER 1310 L8
    ALOAD 6
    IFNONNULL L9
    GETSTATIC net/minecraft/init/Biomes.PLAINS : Lnet/minecraft/world/biome/Biome;
    GOTO L10
   L9
   FRAME APPEND [net/minecraft/world/biome/Biome]
    ALOAD 6
   L10
   FRAME SAME1 net/minecraft/world/biome/Biome
    ARETURN
   L11
    LOCALVARIABLE biome Lnet/minecraft/world/biome/Biome; L6 L4 6
    LOCALVARIABLE this Lnet/minecraft/world/chunk/Chunk; L0 L11 0
    LOCALVARIABLE pos Lnet/minecraft/util/math/BlockPos; L0 L11 1
    LOCALVARIABLE provider Lnet/minecraft/world/biome/BiomeProvider; L0 L11 2
    LOCALVARIABLE i I L1 L11 3
    LOCALVARIABLE j I L2 L11 4
    LOCALVARIABLE k I L3 L11 5
    LOCALVARIABLE biome1 Lnet/minecraft/world/biome/Biome; L8 L11 6
    MAXSTACK = 4
    MAXLOCALS = 7
    */
//what it needs to be changed into
    /*
  public getBiome(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/biome/BiomeProvider;)Lnet/minecraft/world/biome/Biome;
   L0
    LINENUMBER 190 L0
    ALOAD 1
    INVOKEVIRTUAL net/minecraft/util/math/BlockPos.getX ()I
    BIPUSH 15
    IAND
    ISTORE 3
   L1
    LINENUMBER 191 L1
    ALOAD 1
    INVOKEVIRTUAL net/minecraft/util/math/BlockPos.getZ ()I
    BIPUSH 15
    IAND
    ISTORE 4
   L2
    LINENUMBER 192 L2
    ALOAD 0
    GETSTATIC random/beasts/api/world/biome/UndergroundBiome$GenerationCapabilities.CAPABILITY : Lnet/minecraftforge/common/capabilities/Capability;
    ACONST_NULL
    INVOKEVIRTUAL net/minecraftforge/common/capabilities/ICapabilityProvider.getCapability (Lnet/minecraftforge/common/capabilities/Capability;Lnet/minecraft/util/EnumFacing;)Ljava/lang/Object;
    CHECKCAST random/beasts/api/world/biome/UndergroundBiome$GenerationCapabilities$UndergroundBiomes
    ASTORE 5
   L3
    LINENUMBER 193 L3
    ALOAD 5
    IFNONNULL L4
   L5
    LINENUMBER 194 L5
    ALOAD 0
    GETFIELD net/minecraft/world/chunk/Chunk.blockBiomeArray : [B
    ILOAD 4
    ICONST_4
    ISHL
    ILOAD 3
    IOR
    BALOAD
    SIPUSH 255
    IAND
    ISTORE 6
   L6
    LINENUMBER 196 L6
    ILOAD 6
    SIPUSH 255
    IF_ICMPNE L7
   L8
    LINENUMBER 197 L8
    ALOAD 2
    ALOAD 1
    GETSTATIC net/minecraft/init/Biomes.PLAINS : Lnet/minecraft/world/biome/Biome;
    INVOKEVIRTUAL net/minecraft/world/biome/BiomeProvider.getBiome (Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/biome/Biome;)Lnet/minecraft/world/biome/Biome;
    ASTORE 7
   L9
    LINENUMBER 198 L9
    ALOAD 7
    INVOKESTATIC net/minecraft/world/biome/Biome.getIdForBiome (Lnet/minecraft/world/biome/Biome;)I
    ISTORE 6
   L10
    LINENUMBER 199 L10
    ALOAD 0
    GETFIELD net/minecraft/world/chunk/Chunk.blockBiomeArray : [B
    ILOAD 4
    ICONST_4
    ISHL
    ILOAD 3
    IOR
    ILOAD 6
    SIPUSH 255
    IAND
    I2B
    BASTORE
   L7
    LINENUMBER 202 L7
   FRAME FULL [net/minecraft/world/chunk/Chunk net/minecraft/util/math/BlockPos net/minecraft/world/biome/BiomeProvider I I random/beasts/api/world/biome/UndergroundBiome$GenerationCapabilities$UndergroundBiomes I] []
    ILOAD 6
    INVOKESTATIC net/minecraft/world/biome/Biome.getBiome (I)Lnet/minecraft/world/biome/Biome;
    ASTORE 7
   L11
    LINENUMBER 203 L11
    ALOAD 7
    IFNONNULL L12
    GETSTATIC net/minecraft/init/Biomes.PLAINS : Lnet/minecraft/world/biome/Biome;
    GOTO L13
   L12
   FRAME APPEND [net/minecraft/world/biome/Biome]
    ALOAD 7
   L13
   FRAME SAME1 net/minecraft/world/biome/Biome
    ARETURN
   L4
    LINENUMBER 206 L4
   FRAME CHOP 2
    ALOAD 5
    INVOKESTATIC random/beasts/api/world/biome/UndergroundBiome$GenerationCapabilities$UndergroundBiomes.access$000 (Lrandom/beasts/api/world/biome/UndergroundBiome$GenerationCapabilities$UndergroundBiomes;)[[B
    ALOAD 1
    INVOKEVIRTUAL net/minecraft/util/math/BlockPos.getY ()I
    BIPUSH 16
    IDIV
    AALOAD
    ILOAD 4
    ICONST_4
    ISHL
    ILOAD 3
    IOR
    BALOAD
    SIPUSH 255
    IAND
    ISTORE 6
   L14
    LINENUMBER 208 L14
    ILOAD 6
    SIPUSH 255
    IF_ICMPNE L15
   L16
    LINENUMBER 209 L16
    ALOAD 2
    ALOAD 1
    GETSTATIC net/minecraft/init/Biomes.PLAINS : Lnet/minecraft/world/biome/Biome;
    INVOKEVIRTUAL net/minecraft/world/biome/BiomeProvider.getBiome (Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/biome/Biome;)Lnet/minecraft/world/biome/Biome;
    ASTORE 7
   L17
    LINENUMBER 210 L17
    ALOAD 7
    INVOKESTATIC net/minecraft/world/biome/Biome.getIdForBiome (Lnet/minecraft/world/biome/Biome;)I
    ISTORE 6
   L18
    LINENUMBER 211 L18
    ALOAD 5
    INVOKESTATIC random/beasts/api/world/biome/UndergroundBiome$GenerationCapabilities$UndergroundBiomes.access$000 (Lrandom/beasts/api/world/biome/UndergroundBiome$GenerationCapabilities$UndergroundBiomes;)[[B
    ALOAD 1
    INVOKEVIRTUAL net/minecraft/util/math/BlockPos.getY ()I
    BIPUSH 16
    IDIV
    AALOAD
    ILOAD 4
    ICONST_4
    ISHL
    ILOAD 3
    IOR
    ILOAD 6
    SIPUSH 255
    IAND
    I2B
    BASTORE
   L15
    LINENUMBER 214 L15
   FRAME APPEND [I]
    ILOAD 6
    INVOKESTATIC net/minecraft/world/biome/Biome.getBiome (I)Lnet/minecraft/world/biome/Biome;
    ASTORE 7
   L19
    LINENUMBER 215 L19
    ALOAD 7
    IFNONNULL L20
    GETSTATIC net/minecraft/init/Biomes.PLAINS : Lnet/minecraft/world/biome/Biome;
    GOTO L21
   L20
   FRAME APPEND [net/minecraft/world/biome/Biome]
    ALOAD 7
   L21
   FRAME SAME1 net/minecraft/world/biome/Biome
    ARETURN
   L22
    LOCALVARIABLE biome Lnet/minecraft/world/biome/Biome; L9 L7 7
    LOCALVARIABLE k I L6 L4 6
    LOCALVARIABLE biome1 Lnet/minecraft/world/biome/Biome; L11 L4 7
    LOCALVARIABLE biome Lnet/minecraft/world/biome/Biome; L17 L15 7
    LOCALVARIABLE k I L14 L22 6
    LOCALVARIABLE biome1 Lnet/minecraft/world/biome/Biome; L19 L22 7
    LOCALVARIABLE this Lnet/minecraft/world/chunk/Chunk; L0 L22 0
    LOCALVARIABLE pos Lnet/minecraft/util/math/BlockPos; L0 L22 1
    LOCALVARIABLE provider Lnet/minecraft/world/biome/BiomeProvider; L0 L22 2
    LOCALVARIABLE i I L1 L22 3
    LOCALVARIABLE j I L2 L22 4
    LOCALVARIABLE biomes Lrandom/beasts/api/world/biome/UndergroundBiome$GenerationCapabilities$UndergroundBiomes; L3 L22 5
    MAXSTACK = 4
    MAXLOCALS = 8
     */