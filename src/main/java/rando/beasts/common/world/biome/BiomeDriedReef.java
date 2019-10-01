package rando.beasts.common.world.biome;

import java.util.Random;

import com.google.common.collect.Iterables;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import rando.beasts.common.block.BlockCoral;
import rando.beasts.common.block.CoralColor;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.world.gen.feature.WorldGenJellyfishTrees;
import rando.beasts.common.world.gen.feature.WorldGenPalmTrees;

public class BiomeDriedReef extends BeastsBiome {

    private static final WorldGenBlob ROCK_GENERATOR = new WorldGenBlob(Blocks.STONE.getDefaultState(), 3);
    private static final WorldGenBlob ANDESITE_GENERATOR = new WorldGenBlob(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), 3);
    private static final WorldGenBlob CORAL_BLOCK_GENERATOR = new WorldGenCoralBlock();
    private static final WorldGenCoralPlant CORAL_PLANT_GENERATOR = new WorldGenCoralPlant();
    private static final WorldGenJellyfishTrees JELLYFISH_TREE_GENERATOR = new WorldGenJellyfishTrees(false);
    private static final WorldGenPalmTrees PALM_TREE_GENERATOR = new WorldGenPalmTrees(false);
    private static final WorldGenerator[] CORALS = {CORAL_BLOCK_GENERATOR, CORAL_PLANT_GENERATOR};
    private static final WorldGenBlob[] ROCKS = {ROCK_GENERATOR, ANDESITE_GENERATOR};

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
        for (int i = 0; i < rand.nextInt(4); ++i) CORALS[1].generate(worldIn, rand, worldIn.getHeight(pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8)));
        if(rand.nextInt(30) == 0) new WorldGenRockCluster(3 + rand.nextInt(9)).generate(worldIn, rand, worldIn.getHeight(pos.add(rand.nextInt(16) + 8,0,rand.nextInt(16))));
        if(rand.nextInt(10) == 0 && TerrainGen.decorate(worldIn, rand, pos, DecorateBiomeEvent.Decorate.EventType.TREE)) {
        	for(int i=0;i<rand.nextInt(4);i++)getRandomTreeFeature(rand).generate(worldIn, rand,  worldIn.getHeight(pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8)));
        }
    }
    
    private static class WorldGenRockCluster extends WorldGenerator{
    	private int clusterSize;
    	
    	public WorldGenRockCluster(int size) {
			super(false);
			this.clusterSize = size;
		} 	
    	public boolean generate(World worldIn, Random rand, BlockPos position) {
    		generateScattered(worldIn, rand, position);
    		generateTop(worldIn,rand,position);
    		generateCoral(worldIn,rand,position);
    		return true;
    	}
		private void generateCoral(World worldIn, Random rand, BlockPos position) {
			BlockPos[] positions = Iterables.toArray(BlockPos.getAllInBox(position.add(-clusterSize,0,-clusterSize), position.add(clusterSize,0,clusterSize)),BlockPos.class);
			for(int i = 0; i < 2 + rand.nextInt(5) + rand.nextInt(3);i++) {
				CORAL_BLOCK_GENERATOR.generate(worldIn, rand, positions[rand.nextInt(positions.length)]);
			}
		}
		private void generateTop(World worldIn, Random rand, BlockPos position) {
			BlockPos[] positions = Iterables.toArray(BlockPos.getAllInBox(position.add(-clusterSize,0,-clusterSize), position.add(clusterSize,0,clusterSize)),BlockPos.class);
			for(int i = 0; i < 4 + rand.nextInt(13);i++) {
				if(rand.nextInt(1) == 0) {
					int size = 1 + rand.nextInt(clusterSize/2);
					new WorldGenDisc(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, getRandomStone(rand)), size, 1).generate(worldIn, rand, positions[rand.nextInt(positions.length)]);
				}
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
				if(rand.nextInt() == 0) {
					int size = 1 + rand.nextInt(3);
					new WorldGenDisc(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, getRandomStone(rand)), size, 2).generate(worldIn, rand, pos);
				}
				else ROCKS[rand.nextInt(ROCKS.length)].generate(worldIn, rand, pos);
			}
			
		}
		private BlockStone.EnumType getRandomStone(Random rand) {
			switch(rand.nextInt(2)) {
			case 0:
				return BlockStone.EnumType.STONE;
			case 1:
				return BlockStone.EnumType.ANDESITE;
			}
			return BlockStone.EnumType.STONE;
		}
    	
    }
    
    private static class WorldGenDisc extends WorldGenerator{
    	private IBlockState block;
    	private int size;
    	private int height;
    	
    	WorldGenDisc(IBlockState block, int size, int height){
    		super(false);
    		this.block = block;
    		this.size = size;
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
            ChunkPos chunkPos = worldIn.getChunkFromBlockCoords(position).getPos();
            for(BlockPos pos : BlockPos.getAllInBox(new BlockPos(chunkPos.getXStart(), position.getY() - 16, chunkPos.getZStart()), new BlockPos(chunkPos.getXEnd(), position.getY() + 16, chunkPos.getZEnd()))) if(worldIn.getBlockState(pos).getBlock() instanceof BlockLeaves) return false;
            return BeastsBlocks.CORAL_PLANTS.get(CoralColor.getRandom(rand)).generatePlant(worldIn, position, rand);
        }
    }
}
