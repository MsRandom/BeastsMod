package rando.beasts.common.world.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import rando.beasts.common.init.BeastsBlocks;

public class BiomeDriedReef extends BeastsBiome {
	
	//this defines chance to generate biome in the world
	public static final int WEIGTH = 50;

    private static final WorldGenBlob ROCK_GENERATOR = new WorldGenBlob(Blocks.STONE.getDefaultState());
    private static final WorldGenBlob ANDESITE_GENERATOR = new WorldGenBlob(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE));
    private static final WorldGenBlob CORAL_BLOCK_GENERATOR = new WorldGenBlob(BeastsBlocks.CORAL_BLOCKS);
    private static final WorldGenCoralPlant CORAL_PLANT_GENERATOR = new WorldGenCoralPlant();
    private static final WorldGenerator[] GENERATORS = {ROCK_GENERATOR, ANDESITE_GENERATOR, CORAL_BLOCK_GENERATOR, CORAL_PLANT_GENERATOR};
    public BiomeDriedReef() {
        super("dried_reef", new BiomeProperties("Dried Reef").setBaseBiome("dried_reef").setRainDisabled().setTemperature(2).setWaterColor(0x00FFFF));
        this.spawnableCreatureList.clear();
        this.topBlock = BeastsBlocks.WHITE_SAND.getDefaultState();
        this.fillerBlock = BeastsBlocks.WHITE_SAND.getDefaultState();
        this.decorator.treesPerChunk = -999;
        this.decorator.deadBushPerChunk = 0;
        this.decorator.reedsPerChunk = 0;
        this.decorator.cactiPerChunk = 0;
        this.decorator.generateFalls = false;
    }

    @Override
    public boolean canRain() {
        return super.canRain();
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        int i = rand.nextInt(3);
        for (int j = 0; j < i; ++j) {
            int k = rand.nextInt(16) + 8;
            int l = rand.nextInt(16) + 8;
            BlockPos blockpos = worldIn.getHeight(pos.add(k, 0, l));
            GENERATORS[rand.nextInt(GENERATORS.length)].generate(worldIn, rand, blockpos);
        }
        super.decorate(worldIn, rand, pos);
    }

    private static class WorldGenBlob extends WorldGenerator {
        private IBlockState block;
        private Block[] blocks;

        WorldGenBlob(IBlockState blockIn) {
            super(false);
            this.block = blockIn;
        }

        WorldGenBlob(Block[] blockIn) {
            super(false);
            this.blocks = blockIn;
        }

        public boolean generate(World worldIn, Random rand, BlockPos position) {
            IBlockState state = blocks == null ? this.block : blocks[rand.nextInt(blocks.length)].getDefaultState();
            for (int i = 0; i < 3; ++i) {
                int j = rand.nextInt(2);
                int k = rand.nextInt(2);
                int l = rand.nextInt(2);
                float f = (float) (j + k + l) * 0.333F + 0.5F;
                for (BlockPos blockpos : BlockPos.getAllInBox(position.add(-j, -k, -l), position.add(j, k, l))) if (blockpos.distanceSq(position) <= (double) (f * f) && (worldIn.getBlockState(blockpos.down()).getBlock() == BeastsBlocks.WHITE_SAND || worldIn.getBlockState(blockpos.down()).getBlock() == state.getBlock()) && worldIn.getBlockState(blockpos.up()) == Blocks.AIR) worldIn.setBlockState(blockpos, state, 4);
                position = position.add(rand.nextInt(4) - 1, -rand.nextInt(2), rand.nextInt(4) - 1);
            }
            return true;
        }
    }

    private static class WorldGenCoralPlant extends WorldGenerator {
        WorldGenCoralPlant() {
            super(false);
        }

        public boolean generate(World worldIn, Random rand, BlockPos position) {
            return BeastsBlocks.CORAL_PLANTS[rand.nextInt(BeastsBlocks.CORAL_PLANTS.length)].generatePlant(worldIn, position, rand);
        }
    }
}
