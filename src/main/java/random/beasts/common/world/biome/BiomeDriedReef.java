package random.beasts.common.world.biome;

import com.google.common.collect.Iterables;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import random.beasts.api.world.biome.BeastsBiome;
import random.beasts.common.block.BlockCoral;
import random.beasts.common.block.CoralColor;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsStructures;
import random.beasts.common.world.gen.feature.WorldGenAnemone;
import random.beasts.common.world.gen.feature.WorldGenJellyfishTrees;
import random.beasts.common.world.gen.feature.WorldGenPalmTrees;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BiomeDriedReef extends BeastsBiome {
    private static final WorldGenRockBlob ROCK_GENERATOR = new WorldGenRockBlob(Blocks.STONE.getDefaultState(), 3);
    private static final WorldGenRockBlob ANDESITE_GENERATOR = new WorldGenRockBlob(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), 3);
    private static final WorldGenRockBlob CORAL_BLOCK_GENERATOR = new WorldGenCoralBlock();
    private static final WorldGenAnemone ANEMONE_GENERATOR = new WorldGenAnemone();
    private static final WorldGenCoralPlant CORAL_PLANT_GENERATOR = new WorldGenCoralPlant();
    private static final WorldGenJellyfishTrees JELLYFISH_TREE_GENERATOR = new WorldGenJellyfishTrees(false);
    private static final WorldGenPalmTrees PALM_TREE_GENERATOR = new WorldGenPalmTrees(false);
    private static final WorldGenRockCluster ROCK_CLUSTER = new WorldGenRockCluster();
    private static final WorldGenRockBlob[] ROCKS = {ROCK_GENERATOR, ANDESITE_GENERATOR};
    private static final List<WeightedGenerator> GENERATORS = Stream.concat(Stream.of(new WeightedGenerator(10, CORAL_PLANT_GENERATOR), new WeightedGenerator(30, ROCK_CLUSTER), new WeightedGenerator(50, JELLYFISH_TREE_GENERATOR), new WeightedGenerator(20, PALM_TREE_GENERATOR), new WeightedGenerator(45, ANEMONE_GENERATOR)), Stream.of(BeastsStructures.SHELLS).map(s -> new WeightedGenerator(20, s))).collect(Collectors.toList());

    public BiomeDriedReef() {
        super("dried_reef", new BiomeProperties("Dried Reef").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(2.0F).setRainfall(0.0F).setTemperature(2).setRainDisabled().setWaterColor(0x00FFFF));
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.SAND.getDefaultState();
        this.fillerBlock = Blocks.SAND.getDefaultState();
        this.decorator.treesPerChunk = -999;
        this.decorator.deadBushPerChunk = 0;
        this.decorator.reedsPerChunk = 0;
        this.decorator.cactiPerChunk = 0;
        this.decorator.generateFalls = false;
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        super.decorate(worldIn, rand, pos);
        /*int k = 0;
        for(int i = 0; i < 16; i += 4) {
            for (int j = 0; j < 16; j += 4) {
                BlockPos shell = pos.add(i, 0, j);
                if (rand.nextInt(6) == 0) {
                    shell = worldIn.getTopSolidOrLiquidBlock(shell);
                    if (worldIn.getBlockState(shell).getMaterial() != Material.WATER && !(worldIn.getBlockState(shell.down()).getBlock() instanceof BlockShellPiece)) {
                        worldIn.setBlockState(shell, BeastsBlocks.SHELL_PIECES[rand.nextInt(BeastsBlocks.SHELL_BLOCKS.length)].getDefaultState());
                        if(k++ == 3) break;
                    }
                }
            }
        }
        WeightedGenerator item = WeightedRandom.getRandomItem(rand, GENERATORS);
        if(item != null) item.generator.generate(worldIn, rand, pos);*/
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
            for (int i = 0; i < 6; ++i) {
                int j = rand.nextInt(2 + size);
                int k = rand.nextInt(2 + size);
                int l = rand.nextInt(2 + size);
                float f = (float) (j + k + l) / 3 + 0.5F;
                for (BlockPos blockpos : BlockPos.getAllInBox(position.add(-j, -k, -l), position.add(j, k, l)))
                    if (blockpos.distanceSq(position) <= (double) (f * f)) worldIn.setBlockState(blockpos, state, 4);
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

    public static class WeightedGenerator extends WeightedRandom.Item {
        public final WorldGenerator generator;

        public WeightedGenerator(int itemWeightIn, WorldGenerator generator) {
            super(itemWeightIn);
            this.generator = generator;
        }
    }
}
