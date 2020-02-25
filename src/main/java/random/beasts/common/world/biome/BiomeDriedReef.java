package random.beasts.common.world.biome;

import com.google.common.collect.Iterables;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import random.beasts.api.world.biome.BeastsBiome;
import random.beasts.common.block.BlockCoral;
import random.beasts.common.block.CoralColor;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.world.gen.feature.WorldGenAnemone;
import random.beasts.common.world.gen.feature.WorldGenJellyfishTrees;
import random.beasts.common.world.gen.feature.WorldGenPalmTrees;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class BiomeDriedReef extends BeastsBiome {
    private static final WorldGenRockBlob ROCK_GENERATOR = new WorldGenRockBlob(Blocks.STONE.getDefaultState(), 3);
    private static final WorldGenRockBlob ANDESITE_GENERATOR = new WorldGenRockBlob(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), 3);
    private static final WorldGenRockBlob CORAL_BLOCK_GENERATOR = new WorldGenCoralBlock();
    private static final WorldGenAnemone ANEMONE_GENERATOR = new WorldGenAnemone();
    private static final WorldGenCoralPlant CORAL_PLANT_GENERATOR = new WorldGenCoralPlant();
    private static final WorldGenJellyfishTrees JELLYFISH_TREE_GENERATOR = new WorldGenJellyfishTrees(false);
    private static final WorldGenPalmTrees PALM_TREE_GENERATOR = new WorldGenPalmTrees(false);
    private static final WorldGenRockCluster ROCK_CLUSTER = new WorldGenRockCluster();
    private static final WorldGenGroundCover GROUND_COVER = new WorldGenGroundCover();
    private static final WorldGenRockBlob[] ROCKS = {ROCK_GENERATOR, ANDESITE_GENERATOR};
    private static Set<StructurePosition> COORDS = new HashSet<>();

    public BiomeDriedReef() {
        super("dried_reef", new Biome.Builder().surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.SAND_SAND_GRAVEL_CONFIG).precipitation(Biome.RainType.NONE).category(Biome.Category.DESERT).depth(0.125F).scale(0.05F).temperature(2.0F).downfall(0.0F).waterColor(4159204).waterFogColor(329011));
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return rand.nextInt(10) == 0 ? JELLYFISH_TREE_GENERATOR : PALM_TREE_GENERATOR;
    }

    @Override
    public void decorate(GenerationStage.Decoration stage, ChunkGenerator<? extends GenerationSettings> chunkGenerator, IWorld worldIn, long seed, SharedSeedRandom random, BlockPos pos) {
        super.decorate(stage, chunkGenerator, worldIn, seed, random, pos);
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        super.decorate(worldIn, rand, pos);
        for (int j5 = 0; j5 < 2; ++j5) {
            int x = rand.nextInt(16) + 8;
            int y = rand.nextInt(16) + 8;
            GROUND_COVER.generate(worldIn, rand, worldIn.getHeight(pos.add(x, 0, y)));
        }

        //I hate this so much but I'm too lazy to make a better system
        int x = rand.nextInt(16) + 8;
        int z = rand.nextInt(16) + 8;
        BlockPos blockPos = worldIn.getHeight(pos.add(x, 0, z));
        ChunkPos chunk = new ChunkPos(blockPos);
        if (rand.nextBoolean())
            generate(CORAL_PLANT_GENERATOR, worldIn, rand, blockPos);
        else if (rand.nextBoolean() && TerrainGen.decorate(worldIn, rand, chunk, DecorateBiomeEvent.Decorate.EventType.TREE))
            generate(getRandomTreeFeature(rand), worldIn, rand, blockPos);
        else if (rand.nextBoolean()) {
            //Both of these cause cascading worldgen lag
            if (rand.nextBoolean()) {
                //if (worldIn.getBlockState(blockPos.down()).getBlock() == Blocks.SAND) BeastsStructures.SHELLS[rand.nextInt(BeastsStructures.SHELLS.length)].generate(worldIn, rand, blockPos.down(2));
            } else {
                //generate(ROCK_CLUSTER, worldIn, rand, blockPos);
            }
        } else if (rand.nextBoolean())
            generate(ANEMONE_GENERATOR, worldIn, rand, blockPos);
    }

    private void generate(WorldGenerator generator, World world, Random rand, BlockPos pos) {
        StructurePosition structure = new StructurePosition(pos);
        if (!COORDS.contains(structure)) {
            generator.generate(world, rand, pos);
            COORDS.add(structure);
        }
    }

    private BlockPos center(ChunkPos chunk, BlockPos pos) {
        return new BlockPos(chunk.getXStart() + 8, pos.getY(), chunk.getZStart() + 8);
    }

    private static class WorldGenRockCluster extends WorldGenerator {
        private final WorldGenDisc[] discs = new WorldGenDisc[2];
        private int clusterSize;

        WorldGenRockCluster() {
            super(false);
            for (int i = 0; i < discs.length; i++) discs[i] = new WorldGenDisc(i + 1);
        }

        public boolean generate(World worldIn, Random rand, BlockPos position) {
            clusterSize = 3 + rand.nextInt(9);
            generateScattered(worldIn, rand, position);
            generateTop(worldIn, rand, position);
            generateCoral(worldIn, rand, position);
            return true;
        }

        private void generateCoral(World worldIn, Random rand, BlockPos position) {
            BlockPos[] positions = Iterables.toArray(BlockPos.getAllInBox(position.add(-clusterSize, 0, -clusterSize), position.add(clusterSize, 0, clusterSize)), BlockPos.class);
            for (int i = 0; i < 2 + rand.nextInt(5) + rand.nextInt(3); i++)
                CORAL_BLOCK_GENERATOR.generate(worldIn, rand, positions[rand.nextInt(positions.length)]);
        }

        private void generateTop(World worldIn, Random rand, BlockPos position) {
            BlockPos[] positions = Iterables.toArray(BlockPos.getAllInBox(position.add(-clusterSize, 0, -clusterSize), position.add(clusterSize, 0, clusterSize)), BlockPos.class);
            for (int i = 0; i < 4 + rand.nextInt(13); i++) {
                if (rand.nextInt(1) == 0)
                    generateDisc(worldIn, rand, 1 + rand.nextInt(clusterSize / 2), 1, positions[rand.nextInt(positions.length)]);
                else
                    ROCKS[rand.nextInt(ROCKS.length)].generate(worldIn, rand, positions[rand.nextInt(positions.length)]);
            }

        }

        private void generateScattered(World worldIn, Random rand, BlockPos position) {
            for (int i = 0; i < 3 + rand.nextInt(5) + rand.nextInt(5); i++) {
                int distance = clusterSize + rand.nextInt(6);
                float angle = (float) (rand.nextDouble() * 2 * Math.PI);
                int x = (int) (position.getX() + MathHelper.cos(angle) * distance);
                int z = (int) (position.getZ() + MathHelper.sin(angle) * distance);
                BlockPos pos = position.add(x, 0, z);
                if (rand.nextInt() == 0) generateDisc(worldIn, rand, 1 + rand.nextInt(3), 2, pos);
                else ROCKS[rand.nextInt(ROCKS.length)].generate(worldIn, rand, pos);
            }
        }

        private void generateDisc(World worldIn, Random rand, int size, int height, BlockPos pos) {
            WorldGenDisc disc = discs[height - 1];
            disc.block = Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, getRandomStone(rand));
            disc.size = size;
            disc.generate(worldIn, rand, pos);
        }

        private BlockStone.EnumType getRandomStone(Random rand) {
            return rand.nextBoolean() ? BlockStone.EnumType.ANDESITE : BlockStone.EnumType.STONE;
        }
    }

    private static class WorldGenDisc extends WorldGenerator {
        public IBlockState block;
        public int size;
        private int height;

        WorldGenDisc(int height) {
            super(false);
            this.height = height;
        }

        public boolean generate(World worldIn, Random rand, BlockPos position) {
            position = worldIn.getTopSolidOrLiquidBlock(position).down(rand.nextInt(1 + height));
            for (BlockPos pos : BlockPos.getAllInBox(position.add(-size - 3, -height - 3, -size - 3), position.add(size + 3, height + 3, size + 3))) {
                int x = pos.getX() - position.getX();
                int y = pos.getY() - position.getY();
                int z = pos.getZ() - position.getZ();
                int distance = (x * x + z * z) / (size * size) + (y * y) / (height * height);
                if (distance <= 1) worldIn.setBlockState(pos, block, 4);
            }
            return true;
        }
    }

    private static class WorldGenRockBlob extends WorldGenerator {
        private IBlockState block;
        private int size;

        WorldGenRockBlob(IBlockState blockIn, int size) {
            super(false);
            this.block = blockIn;
            this.size = size;
        }

        WorldGenRockBlob(IBlockState blockIn) {
            this(blockIn, 0);
        }

        public boolean generate(World worldIn, Random rand, BlockPos position) {
            IBlockState state = getBlock(rand);
            position = worldIn.getTopSolidOrLiquidBlock(position).down(rand.nextInt(1 + size));
            ChunkPos pos = new ChunkPos(position);
            for (int i = 0; i < 6; ++i) {
                int j = rand.nextInt(2 + size);
                int k = rand.nextInt(2 + size);
                int l = rand.nextInt(2 + size);
                float f = (float) (j + k + l) / 3 + 0.5F;
                for (BlockPos blockpos : BlockPos.getAllInBox(position.add(-j, -k, -l), position.add(j, k, l)))
                    if (pos.x == blockpos.getX() >> 4 && pos.z == blockpos.getZ() >> 4 && blockpos.distanceSq(position) <= (double) (f * f))
                        worldIn.setBlockState(blockpos, state, 4);
                position = position.add(rand.nextInt(4) - 1, -rand.nextInt(2), rand.nextInt(4) - 1);
            }

            return true;
        }

        protected IBlockState getBlock(Random rand) {
            return block;
        }
    }

    private static class WorldGenCoralBlock extends WorldGenRockBlob {
        private IBlockState[] states = new IBlockState[CoralColor.values().length];

        WorldGenCoralBlock() {
            super(null);
        }

        @Override
        protected IBlockState getBlock(Random rand) {
            int i = rand.nextInt(states.length);
            if (states[i] == null)
                states[i] = BeastsBlocks.CORAL_BLOCK.getDefaultState().withProperty(BlockCoral.COLOR, CoralColor.values()[i]);
            return states[i];
        }
    }

    private static class WorldGenCoralPlant extends WorldGenerator {
        WorldGenCoralPlant() {
            super(false);
        }

        public boolean generate(World worldIn, Random rand, BlockPos position) {
            return BeastsBlocks.CORAL_PLANTS.get(CoralColor.getRandom(rand)).generatePlant(worldIn, position, rand);
        }
    }

    public static class WorldGenGroundCover extends WorldGenerator {
        public boolean generate(World worldIn, Random rand, BlockPos position) {
            for (int i = 0; i < 10; ++i) {
                BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
                if (worldIn.isAirBlock(blockpos) && worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.SAND) {
                    worldIn.setBlockState(blockpos, BeastsBlocks.SHELL_PIECES[rand.nextInt(BeastsBlocks.SHELL_BLOCKS.length)].getDefaultState(), 2);
                }
            }

            return true;
        }
    }

    private static class StructurePosition {
        private final int x;
        private final int z;

        public StructurePosition(BlockPos pos) {
            this.x = pos.getX() >> 2;
            this.z = pos.getZ() >> 2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StructurePosition)) return false;
            StructurePosition that = (StructurePosition) o;
            return x == that.x &&
                    z == that.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, z);
        }
    }
}
