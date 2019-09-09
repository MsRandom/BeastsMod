package rando.beasts.common.world.biome;

import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import rando.beasts.common.block.BlockCoral;
import rando.beasts.common.block.CoralColor;
import rando.beasts.common.entity.monster.EntityWhippingBarnacle;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.world.gen.feature.WorldGenJellyfishTrees;
import rando.beasts.common.world.gen.feature.WorldGenPalmTrees;

public class BiomeDriedReef extends BeastsBiome {

    private static final WorldGenBlob ROCK_GENERATOR = new WorldGenBlob(Blocks.STONE.getDefaultState(), 6);
    private static final WorldGenBlob ANDESITE_GENERATOR = new WorldGenBlob(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), 6);
    private static final WorldGenBlob CORAL_BLOCK_GENERATOR = new WorldGenCoralBlock();
    private static final WorldGenCoralPlant CORAL_PLANT_GENERATOR = new WorldGenCoralPlant();
    private static final WorldGenJellyfishTrees JELLYFISH_TREE_GENERATOR = new WorldGenJellyfishTrees(false);
    private static final WorldGenPalmTrees PALM_TREE_GENERATOR = new WorldGenPalmTrees(false);
    private static final WorldGenerator[] CORALS = {CORAL_BLOCK_GENERATOR, CORAL_PLANT_GENERATOR};
    private static final WorldGenBlob[] ROCKS = {ROCK_GENERATOR, ANDESITE_GENERATOR};

    public BiomeDriedReef() {
        super("dried_reef", new BiomeProperties("Dried Reef").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(2.0F).setRainfall(0.0F).setTemperature(2).setWaterColor(0x00FFFF));
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
    public boolean canRain() {
        return false;
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return rand.nextInt(10) == 0 ? JELLYFISH_TREE_GENERATOR : PALM_TREE_GENERATOR;
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        super.decorate(worldIn, rand, pos);
        for (int i = 0; i < rand.nextInt(4); ++i) CORALS[rand.nextInt(CORALS.length)].generate(worldIn, rand, worldIn.getHeight(pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8)));
        for (int i = 0; i < rand.nextInt(2); ++i) ROCKS[rand.nextInt(ROCKS.length)].generate(worldIn, rand, worldIn.getHeight(pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8)));
        if(rand.nextInt(15) == 0 && TerrainGen.decorate(worldIn, rand, pos, DecorateBiomeEvent.Decorate.EventType.TREE)) getRandomTreeFeature(rand).generate(worldIn, rand,  worldIn.getHeight(pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8)));
    }

    private static class WorldGenBlob extends WorldGenerator {
        private IBlockState block;
        private int size;

        WorldGenBlob(IBlockState blockIn, int size) {
            super(false);
            this.block = blockIn;
            this.size = size;
        }

        WorldGenBlob(IBlockState blockIn) {
            this(blockIn, 0);
        }

        public boolean generate(World worldIn, Random rand, BlockPos position) {
            IBlockState state = getBlock(rand);
            position = worldIn.getTopSolidOrLiquidBlock(position);
            for (int i = 0; i < 6; ++i) {
                int j = rand.nextInt(2 + size);
                int k = rand.nextInt(2 + size);
                int l = rand.nextInt(2 + size);
                float f = (float) (j + k + l) / 3 + 0.5F;
                for (BlockPos blockpos : BlockPos.getAllInBox(position.add(-j, -k, -l), position.add(j, k, l))) if (blockpos.distanceSq(position) <= (double) (f * f) && (worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.SAND || worldIn.getBlockState(blockpos.down()).getBlock() == state.getBlock())) worldIn.setBlockState(blockpos, state, 4);
                position = position.add(rand.nextInt(4) - 1, -rand.nextInt(2), rand.nextInt(4) - 1);
            }

            return true;
        }

        protected IBlockState getBlock(Random rand) {
            return block;
        }
    }

    private static class WorldGenCoralBlock extends WorldGenBlob {

        private IBlockState[] states = new IBlockState[CoralColor.values().length];

        WorldGenCoralBlock() {
            super(null);
        }

        @Override
        protected IBlockState getBlock(Random rand) {
            int i = rand.nextInt(states.length);
            if(states[i] == null) states[i] = BeastsBlocks.CORAL_BLOCK.getDefaultState().withProperty(BlockCoral.COLOR, CoralColor.values()[i]);
            return states[i];
        }
    }

    private static class WorldGenCoralPlant extends WorldGenerator {
        WorldGenCoralPlant() {
            super(false);
        }

        public boolean generate(World worldIn, Random rand, BlockPos position) {
            ChunkPos chunkPos = worldIn.getChunkFromBlockCoords(position).getPos();
            for(BlockPos pos : BlockPos.getAllInBox(new BlockPos(chunkPos.getXStart(), position.getY() - 16, chunkPos.getZStart()), new BlockPos(chunkPos.getXEnd(), position.getY() + 16, chunkPos.getZEnd()))) if(worldIn.getBlockState(pos).getBlock() instanceof BlockLeaves) return false;
            return BeastsBlocks.CORAL_PLANTS.get(CoralColor.getRandom(rand)).generatePlant(worldIn, position, rand);
        }
    }
}
