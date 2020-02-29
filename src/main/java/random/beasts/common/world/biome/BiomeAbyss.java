package random.beasts.common.world.biome;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.storage.loot.RandomValueRange;
import random.beasts.api.configuration.BeastsConfig;
import random.beasts.api.world.biome.underground.UndergroundBiome;
import random.beasts.api.world.biome.underground.UndergroundBiomeBounds;
import random.beasts.common.block.BlockAbyssalOre;
import random.beasts.common.block.OreType;
import random.beasts.common.init.BeastsBlocks;

import java.util.Random;

public class BiomeAbyss extends UndergroundBiome {
	protected static final PerlinNoiseGenerator CAVE_NOISE_LAYER_1 = new PerlinNoiseGenerator(new Random(2345L), 8);
	protected static final PerlinNoiseGenerator CAVE_NOISE_LAYER_2 = new PerlinNoiseGenerator(new Random(-123589L), 8);
	//private static final WorldGenerator VENT_CLUSTER_GENERATOR = new WorldGenAbyssalVentCluster(6, 1, 6, 2, 4, 2);
	//private static final WorldGenerator CORAL_CLUSTER_GENERATOR = new WorldGenAbyssalCoralCluster(3, 1, 3);
	private static final int VENT_CLUSTER_CHANCE = 50;
	private static final int CORAL_CLUSTER_CHANCE = 50;
	private static final int ABYSSAL_GRASS_CHANCE = 8;
	private static final int TENTACLE_GRASS_CHANCE = 40;
	private static final int ABYSSAL_TENDRILS_CHANCE = 15;
	private static final int GLOW_CORAL_CHANCE = 15;
	private final int layerHeight1;
	private final int layerHeight2;

	public BiomeAbyss(Biome base) {
		super("The Abyss", BeastsConfig.abyssWeight, base, new RandomValueRange(80, 144), null);
		this.layerHeight1 = 8;
		this.layerHeight2 = 22;
	}

	@Override
	public RandomValueRange getSize() {
		return new RandomValueRange(80, 144);
	}

	@Override
	public void populate(World world, Random rand, BlockPos pos, UndergroundBiomeBounds bounds) {
		super.populate(world, rand, pos, bounds);
		ChunkPos chunk = new ChunkPos(pos);
		int minY = (pos.getY() >> 5) * 32;
		BlockPos chunkStart = new BlockPos(chunk.getXStart(), minY, chunk.getZStart());
		BlockPos chunkEnd = new BlockPos(chunk.getXEnd(), minY + 32, chunk.getZEnd());

		int radiusX = (bounds.maxX * 16 - bounds.minX * 16) / 2;
		int radiusY = 16;
		int radiusZ = (bounds.maxZ * 16 - bounds.minZ * 16) / 2;
		BlockPos center = new BlockPos(bounds.minX * 16, minY, bounds.minZ * 16).add(radiusX, radiusY, radiusZ);
		BlockPos.getAllInBox(chunkStart, chunkEnd).forEach(posit -> {

			double a = Math.pow(posit.getX() - center.getX(), 2) / Math.pow(radiusX, 2) +
					Math.pow(posit.getY() - center.getY(), 2) / Math.pow(radiusY, 2) +
					Math.pow(posit.getZ() - center.getZ(), 2) / Math.pow(radiusZ, 2);

			if (a < 1) {
				if (world.getBlockState(posit) == Blocks.GRANITE.getDefaultState()) {
					world.setBlockState(posit, Blocks.DARK_PRISMARINE.getDefaultState(), 16);
				}
				if (world.getBlockState(posit) == Blocks.ANDESITE.getDefaultState()) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_STONE.getDefaultState(), 16);
				}
				if (world.getBlockState(posit) == Blocks.DIORITE.getDefaultState()) {
					world.setBlockState(posit, Blocks.PRISMARINE.getDefaultState(), 16);
				}
				if (world.getBlockState(posit) == Blocks.STONE.getDefaultState() ||
						world.getBlockState(posit) == Blocks.GRAVEL.getDefaultState() || world.getBlockState(posit) == Blocks.DIRT.getDefaultState()) {
					if (rand.nextInt(200) == 0)
						world.setBlockState(posit, Blocks.SEA_LANTERN.getDefaultState(), 16);
					else
						world.setBlockState(posit, BeastsBlocks.ABYSSAL_STONE.getDefaultState(), 16);
				}

				ItemStack stack = new ItemStack(world.getBlockState(posit).getBlock());
				if (!stack.isEmpty())
					for (int i : OreDictionary.getOreIDs(stack)) {
						if (OreDictionary.getOreName(i).contains("ore")) {
							world.setBlockState(posit, BeastsBlocks.ABYSSAL_ORE.getDefaultState().with(BlockAbyssalOre.ORE, OreType.getByOreDictionary(OreDictionary.getOreName(i))), 16);
							break;
						}
					}

				if (a < 0.92d) {
					int layer1_height = layerHeight1;
					int layer2_height = layerHeight2;

					double d0 = CAVE_NOISE_LAYER_1.getValue((double) posit.getX() * 1.6667f, (double) posit.getZ() * 1.6667f) * 0.09d;
					if (posit.getY() < Math.round((float) -d0) + minY + layer1_height && posit.getY() > Math.round((float) d0 * 0.333f) + minY + layer1_height) {
						world.setBlockState(posit, Blocks.AIR.getDefaultState(), 16);
					} else if (posit.getY() <= Math.round((float) d0 * 0.333f) + minY + layer1_height && posit.getY() > Math.round((float) d0 * 0.5f) + minY + layer1_height) {
						//world.setBlockState(posit, BeastsBlocks.ABYSSAL_SAND.getDefaultState(), 16);
					}

					double d1 = CAVE_NOISE_LAYER_2.getValue((double) posit.getX() * 1.6667f, (double) posit.getZ() * 1.6667f) * 0.09d;
					if (posit.getY() < Math.round((float) -d1) + minY + layer2_height && posit.getY() > Math.round((float) d1 * 0.333f) + minY + layer2_height) {
						world.setBlockState(posit, Blocks.AIR.getDefaultState(), 16);
					} else if (posit.getY() <= Math.round((float) d1 * 0.333f) + minY + layer2_height && posit.getY() > Math.round((float) d1 * 0.5f) + minY + layer2_height) {
						//world.setBlockState(posit, BeastsBlocks.ABYSSAL_SAND.getDefaultState(), 16);
					}
				}
			}
		});
		//Random random = new Random(center.toLong());
		//this.generateCave(world, pos, rand, bounds);
	}

	@Override
	public void decorate(World world, Random rand, BlockPos pos, UndergroundBiomeBounds bounds) {
		super.decorate(world, rand, pos, bounds);
		ChunkPos chunk = new ChunkPos(pos);
		int minY = (pos.getY() >> 5) * 32;
		BlockPos chunkStart = new BlockPos(chunk.getXStart(), minY, chunk.getZStart());
		BlockPos chunkEnd = new BlockPos(chunk.getXEnd(), minY + 32, chunk.getZEnd());

		int radiusX = (bounds.maxX * 16 - bounds.minX * 16) / 2;
		int radiusY = 16;
		int radiusZ = (bounds.maxZ * 16 - bounds.minZ * 16) / 2;
		BlockPos center = new BlockPos(bounds.minX * 16, minY, bounds.minZ * 16).add(radiusX, radiusY, radiusZ);
		BlockPos.getAllInBox(chunkStart, chunkEnd).forEach(posit -> {

			double a = Math.pow(posit.getX() - center.getX(), 2) / Math.pow(radiusX, 2) +
					Math.pow(posit.getY() - center.getY(), 2) / Math.pow(radiusY, 2) +
					Math.pow(posit.getZ() - center.getZ(), 2) / Math.pow(radiusZ, 2);

			if (a < 0.92d) {
				int layer1_height = layerHeight1;
				int layer2_height = layerHeight2;
				double d0 = CAVE_NOISE_LAYER_1.getValue((double) posit.getX() * 1.6667f, (double) posit.getZ() * 1.6667f) * 0.09d;
				double d1 = CAVE_NOISE_LAYER_2.getValue((double) posit.getX() * 1.6667f, (double) posit.getZ() * 1.6667f) * 0.09d;
				if ((posit.getY() < Math.round((float) -d0) + minY + layer1_height && posit.getY() > Math.round((float) d0 * 0.333f) + minY + layer1_height) ||
						(posit.getY() < Math.round((float) -d1) + minY + layer2_height && posit.getY() > Math.round((float) d1 * 0.333f) + minY + layer2_height)) {

					PerlinNoiseGenerator plantNoise = new PerlinNoiseGenerator(new Random(-12L), 3);
					double noise = plantNoise.getValue(posit.getX() * 1.5f, posit.getZ() * 1.5f);
					if (noise > 1.5d) {
						/*
						if (rand.nextInt(VENT_CLUSTER_CHANCE) == 0 && world.getBlockState(posit.down()).getBlock() == BeastsBlocks.ABYSSAL_SAND)
							VENT_CLUSTER_GENERATOR.generate(world, rand, posit);
					*/
					} /*else if (noise < 1.25d) {
						if (rand.nextInt(CORAL_CLUSTER_CHANCE) == 0 && !world.isAirBlock(posit.down()))
							CORAL_CLUSTER_GENERATOR.generate(world, rand, posit.down());
						if (rand.nextInt(ABYSSAL_GRASS_CHANCE) == 0 && world.getBlockState(posit.down()).getBlock() == BeastsBlocks.ABYSSAL_SAND)
							world.setBlockState(posit, BeastsBlocks.ABYSSAL_GRASS.getDefaultState(), 16);
						if (rand.nextInt(TENTACLE_GRASS_CHANCE) == 0 && world.getBlockState(posit.down()).getBlock() == BeastsBlocks.ABYSSAL_SAND)
							world.setBlockState(posit, BeastsBlocks.TENTACLE_GRASS.getDefaultState(), 16);
						if (rand.nextInt(ABYSSAL_TENDRILS_CHANCE) == 0 && world.getBlockState(posit.down()).getBlock() == BeastsBlocks.ABYSSAL_SAND && world.isAirBlock(posit.up())) {
							world.setBlockState(posit, BeastsBlocks.ABYSSAL_TENDRILS.getDefaultState().with(BlockAbyssalTendrils.HALF, BlockAbyssalTendrils.EnumBlockHalf.LOWER), 16);
							world.setBlockState(posit.up(), BeastsBlocks.ABYSSAL_TENDRILS.getDefaultState().with(BlockAbyssalTendrils.HALF, BlockAbyssalTendrils.EnumBlockHalf.UPPER), 16);
						}
						EnumFacing facing = EnumFacing.getFront(rand.nextInt(6));
						if (rand.nextInt(GLOW_CORAL_CHANCE) == 0 && world.getBlockState(posit.offset(facing)).getBlock() == BeastsBlocks.ABYSSAL_STONE) {
							if (rand.nextBoolean())
								world.setBlockState(posit, BeastsBlocks.GLOW_CORAL_BLUE.getDefaultState().with(BlockGlowCoral.FACING, facing.getOpposite()), 16);
							else
								world.setBlockState(posit, BeastsBlocks.GLOW_CORAL_PINK.getDefaultState().with(BlockGlowCoral.FACING, facing.getOpposite()), 16);
						}
					}*/
				}
			}
		});
	}

	/*public void generateCave(World world, BlockPos pos, Random rand, UndergroundBiomeBounds bounds) {
		ChunkPos chunk = new ChunkPos(pos);
		int minY = (pos.getY() >> 5) * 32;
		BlockPos chunkStart = new BlockPos(chunk.getXStart(), minY, chunk.getZStart());
		BlockPos chunkEnd = new BlockPos(chunk.getXEnd(), minY + 32, chunk.getZEnd());
		NoiseGeneratorPerlin cave_noise = new NoiseGeneratorPerlin(rand, 1);

		int radiusX = (bounds.maxX * 16 - bounds.minX * 16) / 2;
		int radiusY = 16;
		int radiusZ = (bounds.maxZ * 16 - bounds.minZ * 16) / 2;
		BlockPos center = new BlockPos(bounds.minX * 16, minY, bounds.minZ * 16).add(radiusX, radiusY, radiusZ);

		for (int y = -16; y <= 16; ++y) {
			int x = Math.round((float) cave_noise.getValue(chunk.getXStart() * 0.5d, (double) y * 0.5d) * radiusX * 0.5f);
			int z = Math.round((float) cave_noise.getValue(chunk.getZStart() * 0.5d, (double) y * 0.5d) * radiusZ * 0.5f);
			BlockPos position = center.add(x, y, z);
			double a = Math.pow(position.getX() - center.getX(), 2) / Math.pow(radiusX, 2) +
					Math.pow(position.getY() - center.getY(), 2) / Math.pow(radiusY, 2) +
					Math.pow(position.getZ() - center.getZ(), 2) / Math.pow(radiusZ, 2);
			if (a < 1)
				this.clearSphere(world, (1d - a) * 5, position, chunk);
		}
	}

	public void clearSphere(World world, double radius, BlockPos pos, ChunkPos chunkPos) {
		int radInt = Math.round((float) radius);
		for (BlockPos posit : BlockPos.getAllInBox(pos.add(radInt, radInt, radInt), pos.add(-radInt, -radInt, -radInt))) {
			double a = Math.pow(posit.getX() - pos.getX(), 2) / Math.pow(radius, 2) +
					Math.pow(posit.getY() - pos.getY(), 2) / Math.pow(radius, 2) +
					Math.pow(posit.getZ() - pos.getZ(), 2) / Math.pow(radius, 2);
			if (chunkPos.equals(new ChunkPos(posit)) && a < 1)
				world.setBlockState(posit, Blocks.AIR.getDefaultState(), 16);
		}
	}*/
}
