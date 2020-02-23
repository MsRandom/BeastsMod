package random.beasts.common.world.biome;

import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.storage.loot.RandomValueRange;
import random.beasts.api.configuration.BeastsConfig;
import random.beasts.api.world.biome.underground.UndergroundBiome;
import random.beasts.api.world.biome.underground.UndergroundBiomeBounds;
import random.beasts.common.init.BeastsBlocks;

import java.util.Random;

public class BiomeAbyss extends UndergroundBiome {

	protected static final NoiseGeneratorPerlin CAVE_NOISE_LAYER_1 = new NoiseGeneratorPerlin(new Random(2345L), 8);
	protected static final NoiseGeneratorPerlin CAVE_NOISE_LAYER_2 = new NoiseGeneratorPerlin(new Random(-123589L), 8);

	public BiomeAbyss(Biome base) {
		super("The Abyss", BeastsConfig.abyssWeight, base);
	}

	public int getRarity() {
		return 4;
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
		for (BlockPos posit : BlockPos.getAllInBox(chunkStart, chunkEnd)) {

			double a = Math.pow(posit.getX() - center.getX(), 2) / Math.pow(radiusX, 2) +
					Math.pow(posit.getY() - center.getY(), 2) / Math.pow(radiusY, 2) +
					Math.pow(posit.getZ() - center.getZ(), 2) / Math.pow(radiusZ, 2);

			if (a < 1) {
				if (world.getBlockState(posit) == Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE)) {
					world.setBlockState(posit, Blocks.PRISMARINE.getDefaultState().withProperty(BlockPrismarine.VARIANT, BlockPrismarine.EnumType.DARK), 16);
				}
				if (world.getBlockState(posit) == Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE)) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_STONE.getDefaultState(), 16);
				}
				if (world.getBlockState(posit) == Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE)) {
					world.setBlockState(posit, Blocks.PRISMARINE.getDefaultState().withProperty(BlockPrismarine.VARIANT, BlockPrismarine.EnumType.ROUGH), 16);
				}
				if (world.getBlockState(posit) == Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE) ||
						world.getBlockState(posit) == Blocks.GRAVEL.getDefaultState() || world.getBlockState(posit) == Blocks.DIRT.getDefaultState()) {
					if (rand.nextInt(200) == 0)
						world.setBlockState(posit, Blocks.SEA_LANTERN.getDefaultState(), 16);
					else
						world.setBlockState(posit, BeastsBlocks.ABYSSAL_STONE.getDefaultState(), 16);
				}
				if (world.getBlockState(posit).getBlock() == Blocks.IRON_ORE) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_IRON_ORE.getDefaultState(), 16);
				}
				if (world.getBlockState(posit).getBlock() == Blocks.GOLD_ORE) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_GOLD_ORE.getDefaultState(), 16);
				}
				if (world.getBlockState(posit).getBlock() == Blocks.LAPIS_ORE) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_LAPIS_ORE.getDefaultState(), 16);
				}
				if (world.getBlockState(posit).getBlock() == Blocks.REDSTONE_ORE) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_REDSTONE_ORE.getDefaultState(), 16);
				}
				if (world.getBlockState(posit).getBlock() == Blocks.COAL_ORE) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_COAL_ORE.getDefaultState(), 16);
				}
				if (world.getBlockState(posit).getBlock() == Blocks.DIAMOND_ORE) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_DIAMOND_ORE.getDefaultState(), 16);
				}

				int layer1_height = 8;
				int layer2_height = 22;

				double d0 = CAVE_NOISE_LAYER_1.getValue((double) posit.getX() * 1.6667f, (double) posit.getZ() * 1.6667f) * 0.09d;
				if (posit.getY() < Math.round((float) -d0) + minY + layer1_height && posit.getY() > Math.round((float) d0 * 0.333f) + minY + layer1_height) {
					world.setBlockState(posit, Blocks.AIR.getDefaultState(), 16);
				} else if (posit.getY() <= Math.round((float) d0 * 0.333f) + minY + layer1_height && posit.getY() > Math.round((float) d0 * 0.5f) + minY + layer1_height) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_SAND.getDefaultState(), 16);
				}

				double d1 = CAVE_NOISE_LAYER_2.getValue((double) posit.getX() * 1.6667f, (double) posit.getZ() * 1.6667f) * 0.09d;
				if (posit.getY() < Math.round((float) -d1) + minY + layer2_height && posit.getY() > Math.round((float) d1 * 0.333f) + minY + layer2_height) {
					world.setBlockState(posit, Blocks.AIR.getDefaultState(), 16);
				} else if (posit.getY() <= Math.round((float) d1 * 0.333f) + minY + layer2_height && posit.getY() > Math.round((float) d1 * 0.5f) + minY + layer2_height) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_SAND.getDefaultState(), 16);
				}
			}
		}
		Random random = new Random(center.toLong());
		this.generateCave(world, pos, rand, bounds);
	}

	public void generateCave(World world, BlockPos pos, Random rand, UndergroundBiomeBounds bounds) {
		ChunkPos chunk = new ChunkPos(pos);
		int minY = (pos.getY() >> 5) * 32;
		BlockPos chunkStart = new BlockPos(chunk.getXStart(), minY, chunk.getZStart());
		BlockPos chunkEnd = new BlockPos(chunk.getXEnd(), minY + 32, chunk.getZEnd());
		NoiseGeneratorPerlin cave_noise = new NoiseGeneratorPerlin(rand, 1);

		int radiusX = (bounds.maxX * 16 - bounds.minX * 16) / 2;
		int radiusY = 16;
		int radiusZ = (bounds.maxZ * 16 - bounds.minZ * 16) / 2;
		BlockPos center = new BlockPos(bounds.minX * 16, minY, bounds.minZ * 16).add(radiusX, radiusY, radiusZ);

		for (int y = -15; y <= 15; ++y) {
			int x = Math.round((float) cave_noise.getValue(chunk.getXStart(), y));
			int z = Math.round((float) cave_noise.getValue(chunk.getZStart(), y));
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
	}
}
