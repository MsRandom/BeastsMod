package random.beasts.common.world.biome;

import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.Loader;
import random.beasts.common.block.BlockCoral;
import random.beasts.common.block.CoralColor;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.world.gen.feature.WorldGenAnemone;
import random.beasts.common.world.gen.feature.WorldGenJellyfishTrees;
import random.beasts.common.world.gen.feature.WorldGenPalmTrees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BiomeDriedReef extends BeastsBiome {

    private static final WorldGenBlob ROCK_GENERATOR = new WorldGenBlob(Blocks.STONE.getDefaultState(), 3);
    private static final WorldGenBlob ANDESITE_GENERATOR = new WorldGenBlob(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), 3);
    private static final WorldGenBlob CORAL_BLOCK_GENERATOR = new WorldGenCoralBlock();
    private static final WorldGenAnemone ANEMONE_GENERATOR = new WorldGenAnemone();
    private static final WorldGenCoralPlant CORAL_PLANT_GENERATOR = new WorldGenCoralPlant();
    private static final WorldGenJellyfishTrees JELLYFISH_TREE_GENERATOR = new WorldGenJellyfishTrees(false);
    private static final WorldGenPalmTrees PALM_TREE_GENERATOR = new WorldGenPalmTrees(false);
    private static final WorldGenRockCluster ROCK_CLUSTER = new WorldGenRockCluster();
    private static final WorldGenBlob[] ROCKS = {ROCK_GENERATOR, ANDESITE_GENERATOR};
    private static BlockPos[] coords = new BlockPos[Short.MAX_VALUE + 1];
    private static int generated = 0;

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
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return rand.nextInt(10) == 0 ? JELLYFISH_TREE_GENERATOR : PALM_TREE_GENERATOR;
    }

    private static BlockPos getNearestGeneratedCoords(BlockPos pos) {
        BlockPos blockpos = null;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(pos);
        double d0 = Double.MAX_VALUE;
        for (BlockPos pos1 : coords) {
            if(pos1 != null) {
                mutableBlockPos.setPos(pos1);
                double d1 = mutableBlockPos.distanceSq(pos);
                if (blockpos == null) {
                    blockpos = new BlockPos(mutableBlockPos);
                    d0 = d1;
                } else if (d1 < d0) {
                    blockpos = new BlockPos(mutableBlockPos);
                    d0 = d1;
                }
            }
        }
        return blockpos;
    }

    //this is probably really inefficient but i can't think of any other way of doing it
    private BlockPos getPos(World worldIn, Random rand, BlockPos pos) {
        BlockPos height = worldIn.getHeight(pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
        BlockPos nearest = getNearestGeneratedCoords(height);
        if(nearest != null && nearest.distanceSq(height) < 100) return getPos(worldIn, rand, height);
        if(coords.length - 1 < generated) coords = Arrays.copyOf(coords, coords.length + Short.MAX_VALUE + 1);
        coords[generated++] = height;
        return height;
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        super.decorate(worldIn, rand, pos);
        Supplier<BlockPos> position = () -> getPos(worldIn, rand, pos);
        for (int i = 0; i < rand.nextInt(4); ++i) CORAL_PLANT_GENERATOR.generate(worldIn, rand, position.get());
        if(rand.nextInt(30) == 0) ROCK_CLUSTER.generate(worldIn, rand, position.get());
        if(rand.nextInt(10) == 0 && TerrainGen.decorate(worldIn, rand, new ChunkPos(pos), DecorateBiomeEvent.Decorate.EventType.TREE)) for(int i = 0; i < rand.nextInt(4); i++) getRandomTreeFeature(rand).generate(worldIn, rand, position.get());
        if(rand.nextInt(45) == 0) ANEMONE_GENERATOR.generate(worldIn, rand, position.get());
    }
    
    private static class WorldGenRockCluster extends WorldGenerator{
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
			BlockPos[] positions = Iterables.toArray(BlockPos.getAllInBox(position.add(-clusterSize,0,-clusterSize), position.add(clusterSize,0,clusterSize)),BlockPos.class);
			for(int i = 0; i < 2 + rand.nextInt(5) + rand.nextInt(3);i++) CORAL_BLOCK_GENERATOR.generate(worldIn, rand, positions[rand.nextInt(positions.length)]);
		}

		private void generateTop(World worldIn, Random rand, BlockPos position) {
			BlockPos[] positions = Iterables.toArray(BlockPos.getAllInBox(position.add(-clusterSize,0,-clusterSize), position.add(clusterSize,0,clusterSize)),BlockPos.class);
			for(int i = 0; i < 4 + rand.nextInt(13);i++) {
				if(rand.nextInt(1) == 0) generateDisc(worldIn, rand, 1 + rand.nextInt(clusterSize / 2), 1, positions[rand.nextInt(positions.length)]);
				else ROCKS[rand.nextInt(ROCKS.length)].generate(worldIn, rand, positions[rand.nextInt(positions.length)]);
			}
			
		}

		private void generateScattered(World worldIn, Random rand, BlockPos position) {
			for(int i = 0; i < 3 + rand.nextInt(5) + rand.nextInt(5);i++) {
				int distance = clusterSize + rand.nextInt(6);
				float angle = (float) (rand.nextDouble()*2*Math.PI);
				int x = (int) (position.getX() + MathHelper.cos(angle)*distance);
				int z = (int) (position.getZ() + MathHelper.sin(angle)*distance);
				BlockPos pos = position.add(x, 0, z);
				if(rand.nextInt() == 0) generateDisc(worldIn, rand, 1 + rand.nextInt(3), 2, pos);
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
    
    private static class WorldGenDisc extends WorldGenerator{
    	public IBlockState block;
    	public int size;
    	private int height;
    	
    	WorldGenDisc(int height){
    		super(false);
    		this.height = height;
    	}

    	public boolean generate(World worldIn, Random rand, BlockPos position) {
    		position = worldIn.getTopSolidOrLiquidBlock(position).down(rand.nextInt(1 + height));
    		for(BlockPos pos : BlockPos.getAllInBox(position.add(-size - 3, -height - 3, -size - 3), position.add(size + 3, height + 3, size + 3))) {
    			int x = pos.getX() - position.getX();
    			int y = pos.getY() - position.getY();
    			int z = pos.getZ() - position.getZ();
    			int distance = (x*x + z*z)/(size*size) + (y*y)/(height*height);
    			if(distance <= 1) worldIn.setBlockState(pos, block, 4);
    		}
    		return true;
    	}
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
            position = worldIn.getTopSolidOrLiquidBlock(position).down(rand.nextInt(1 + size));
            for (int i = 0; i < 6; ++i) {
                int j = rand.nextInt(2 + size);
                int k = rand.nextInt(2 + size);
                int l = rand.nextInt(2 + size);
                float f = (float) (j + k + l) / 3 + 0.5F;
                for (BlockPos blockpos : BlockPos.getAllInBox(position.add(-j, -k, -l), position.add(j, k, l))) if (blockpos.distanceSq(position) <= (double) (f * f)) worldIn.setBlockState(blockpos, state, 4);
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
            //ChunkPos chunkPos = worldIn.getChunkFromBlockCoords(position).getPos();
            //for(BlockPos pos : BlockPos.getAllInBox(new BlockPos(chunkPos.getXStart(), position.getY() - 16, chunkPos.getZStart()), new BlockPos(chunkPos.getXEnd(), position.getY() + 16, chunkPos.getZEnd()))) if(worldIn.getBlockState(pos).getBlock() instanceof BlockLeaves) return false;
            return BeastsBlocks.CORAL_PLANTS.get(CoralColor.getRandom(rand)).generatePlant(worldIn, position, rand);
        }
    }
}
