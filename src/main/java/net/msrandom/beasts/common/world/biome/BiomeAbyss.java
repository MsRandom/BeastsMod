package net.msrandom.beasts.common.world.biome;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraftforge.oredict.OreDictionary;
import net.msrandom.beasts.api.configuration.BeastsConfig;
import net.msrandom.beasts.api.world.biome.underground.UndergroundBiome;
import net.msrandom.beasts.api.world.biome.underground.UndergroundBiomeBounds;
import net.msrandom.beasts.common.block.BlockAbyssalOre;
import net.msrandom.beasts.common.block.BlockAbyssalTendrils;
import net.msrandom.beasts.common.block.BlockGlowCoral;
import net.msrandom.beasts.common.block.OreType;
import net.msrandom.beasts.common.init.BeastsBlocks;
import net.msrandom.beasts.common.world.gen.feature.WorldGenAbyssalCoralCluster;
import net.msrandom.beasts.common.world.gen.feature.WorldGenAbyssalVentCluster;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BiomeAbyss extends UndergroundBiome {
	protected static final NoiseGeneratorPerlin CAVE_NOISE_LAYER_1 = new NoiseGeneratorPerlin(new Random(2345L), 8);
	protected static final NoiseGeneratorPerlin CAVE_NOISE_LAYER_2 = new NoiseGeneratorPerlin(new Random(-123589L), 8);
	private static final WorldGenerator VENT_CLUSTER_GENERATOR = new WorldGenAbyssalVentCluster(6, 1, 6, 2, 4, 2);
	private static final WorldGenerator CORAL_CLUSTER_GENERATOR = new WorldGenAbyssalCoralCluster(3, 1, 3);
	private static final Set<UndergroundBiomeBounds> DENS = new HashSet<>();
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
	public void populate(World world, Random rand, BlockPos pos, UndergroundBiomeBounds bounds) {
		super.populate(world, rand, pos, bounds);
		ChunkPos chunk = new ChunkPos(pos);
		int minY = (pos.getY() >> 5) * 32;
		BlockPos chunkStart = new BlockPos(chunk.getXStart(), minY - 32, chunk.getZStart());
		BlockPos chunkEnd = new BlockPos(chunk.getXEnd(), minY + 32, chunk.getZEnd());
		int radiusX = (int) (((bounds.maxX * 16 - bounds.minX * 16) / 2) * 1.5);
		int radiusY = 16;
		int radiusZ = (int) (((bounds.maxZ * 16 - bounds.minZ * 16) / 2) * 1.5);
		BlockPos center = new BlockPos(bounds.minX * 16 + radiusX, minY + radiusY, bounds.minZ * 16 + radiusZ);
		if (DENS.contains(bounds) || rand.nextInt(50) == 0) {
			DENS.add(bounds);
			BlockPos denStart = center.add(-24, -16, -24);
			BlockPos denEnd = center.add(24, 16, 24);
			for (BlockPos posit : BlockPos.getAllInBox(denStart, denEnd)) {
				if (posit.getX() > chunkStart.getX() && posit.getX() < chunkEnd.getX() && posit.getZ() > chunkStart.getX() && posit.getZ() < chunkEnd.getZ()) {
					double a = Math.pow(posit.getX() - center.getX(), 2) / Math.pow(radiusX, 2) +
							Math.pow(posit.getY() - center.getY(), 2) / Math.pow(radiusY, 2) +
							Math.pow(posit.getZ() - center.getZ(), 2) / Math.pow(radiusZ, 2);

					BlockPos p = posit.down(12);

					boolean air = a < 1;
					boolean low = posit.getY() > center.getY() - 4;

					if ((a < 1.2 && a > 1) || (air && !low)) replaceStone(world, p, rand);
					else if (air) world.setBlockToAir(p);
				}
			}
		} else {
			for (BlockPos p : BlockPos.getAllInBox(chunkStart, chunkEnd))
				generateCaveNoise(rand, world, p, center, radiusX, radiusY, radiusZ, minY);
		}
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
		for (BlockPos posit : BlockPos.getAllInBox(chunkStart, chunkEnd)) {
			double a = Math.pow(posit.getX() - center.getX(), 2) / Math.pow(radiusX, 2) +
					Math.pow(posit.getY() - center.getY(), 2) / Math.pow(radiusY, 2) +
					Math.pow(posit.getZ() - center.getZ(), 2) / Math.pow(radiusZ, 2);

			if (a < 0.92d) {
				int layer1_height = layerHeight1;
				int layer2_height = layerHeight2;
				double d0 = CAVE_NOISE_LAYER_1.getValue((double) posit.getX() * 1.6667f, (double) posit.getZ() * 1.6667f) * 0.09d;
				double d1 = CAVE_NOISE_LAYER_2.getValue((double) posit.getX() * 1.6667f, (double) posit.getZ() * 1.6667f) * 0.09d;
				if ((posit.getY() < Math.round((float) -d0) + minY + layer1_height && posit.getY() > Math.round((float) d0 * 0.25f) + minY + layer1_height) ||
						(posit.getY() < Math.round((float) -d1) + minY + layer2_height && posit.getY() > Math.round((float) d1 * 0.25f) + minY + layer2_height)) {

					NoiseGeneratorPerlin plantNoise = new NoiseGeneratorPerlin(new Random(-12L), 3);
					double noise = plantNoise.getValue(posit.getX() * 1.5f, posit.getZ() * 1.5f);
					if (noise > 1.5d) {
						if (rand.nextInt(VENT_CLUSTER_CHANCE) == 0 && world.getBlockState(posit.down()).getBlock() == BeastsBlocks.ABYSSAL_SAND)
							VENT_CLUSTER_GENERATOR.generate(world, rand, posit);
					} else if (noise < 1.25d) {
						if (rand.nextInt(CORAL_CLUSTER_CHANCE) == 0 && world.getBlockState(posit.down()).getBlock() == BeastsBlocks.ABYSSAL_SAND)
							CORAL_CLUSTER_GENERATOR.generate(world, rand, posit.down());
						if (rand.nextInt(ABYSSAL_GRASS_CHANCE) == 0 && world.getBlockState(posit.down()).getBlock() == BeastsBlocks.ABYSSAL_SAND && world.isAirBlock(posit))
							world.setBlockState(posit, BeastsBlocks.ABYSSAL_GRASS.getDefaultState(), 16);
						if (rand.nextInt(TENTACLE_GRASS_CHANCE) == 0 && world.getBlockState(posit.down()).getBlock() == BeastsBlocks.ABYSSAL_SAND && world.isAirBlock(posit))
							world.setBlockState(posit, BeastsBlocks.TENTACLE_GRASS.getDefaultState(), 16);
						if (rand.nextInt(ABYSSAL_TENDRILS_CHANCE) == 0 && world.getBlockState(posit.down()).getBlock() == BeastsBlocks.ABYSSAL_SAND && world.isAirBlock(posit.up()) && world.isAirBlock(posit)) {
							world.setBlockState(posit, BeastsBlocks.ABYSSAL_TENDRILS.getDefaultState().withProperty(BlockAbyssalTendrils.HALF, BlockAbyssalTendrils.EnumBlockHalf.LOWER), 16);
							world.setBlockState(posit.up(), BeastsBlocks.ABYSSAL_TENDRILS.getDefaultState().withProperty(BlockAbyssalTendrils.HALF, BlockAbyssalTendrils.EnumBlockHalf.UPPER), 16);
						}
						EnumFacing facing = EnumFacing.getFront(rand.nextInt(6));
						if (rand.nextInt(GLOW_CORAL_CHANCE) == 0 && world.getBlockState(posit.offset(facing)).getBlock() == BeastsBlocks.ABYSSAL_STONE && world.isAirBlock(posit)) {
							if (rand.nextBoolean())
								world.setBlockState(posit, BeastsBlocks.GLOW_CORAL_BLUE.getDefaultState().withProperty(BlockGlowCoral.FACING, facing.getOpposite()), 16);
							else
								world.setBlockState(posit, BeastsBlocks.GLOW_CORAL_PINK.getDefaultState().withProperty(BlockGlowCoral.FACING, facing.getOpposite()), 16);
						}
					}
				}
			}
		}
	}

	private void generateCaveNoise(Random rand, World world, BlockPos pos, BlockPos center, int radiusX, int radiusY, int radiusZ, int minY) {
		double a = Math.pow(pos.getX() - center.getX(), 2) / Math.pow(radiusX, 2) +
				Math.pow(pos.getY() - center.getY(), 2) / Math.pow(radiusY, 2) +
				Math.pow(pos.getZ() - center.getZ(), 2) / Math.pow(radiusZ, 2);

		if (a < 1) {
			replaceStone(world, pos, rand);

			if (a < 0.92d) {
				int layer1Height = layerHeight1;
				int layer2Height = layerHeight2;

				double d0 = CAVE_NOISE_LAYER_1.getValue((double) pos.getX() * 1.6667f, (double) pos.getZ() * 1.6667f) * 0.09d;
				if (pos.getY() < Math.round((float) -d0) + minY + layer1Height && pos.getY() > Math.round((float) d0 * 0.25f) + minY + layer1Height)
					world.setBlockState(pos, Blocks.AIR.getDefaultState(), 16);
				else if (pos.getY() <= Math.round((float) d0 * 0.25f) + minY + layer1Height && pos.getY() > Math.round((float) d0 * 0.5f) + minY + layer1Height)
					world.setBlockState(pos, BeastsBlocks.ABYSSAL_SAND.getDefaultState(), 16);
				else if (pos.getY() <= Math.round((float) d0 * 0.5f) + minY + layer1Height && pos.getY() > Math.round((float) d0 * 0.667f) + minY + layer1Height)
					world.setBlockState(pos, BeastsBlocks.ABYSSAL_SANDSTONE.getDefaultState(), 16);

				double d1 = CAVE_NOISE_LAYER_2.getValue((double) pos.getX() * 1.6667f, (double) pos.getZ() * 1.6667f) * 0.09d;
				if (pos.getY() < Math.round((float) -d1) + minY + layer2Height && pos.getY() > Math.round((float) d1 * 0.25f) + minY + layer2Height)
					world.setBlockState(pos, Blocks.AIR.getDefaultState(), 16);
				else if (pos.getY() <= Math.round((float) d1 * 0.25f) + minY + layer2Height && pos.getY() > Math.round((float) d1 * 0.5f) + minY + layer2Height)
					world.setBlockState(pos, BeastsBlocks.ABYSSAL_SAND.getDefaultState(), 16);
				else if (pos.getY() <= Math.round((float) d1 * 0.5f) + minY + layer2Height && pos.getY() > Math.round((float) d1 * 0.667f) + minY + layer2Height)
					world.setBlockState(pos, BeastsBlocks.ABYSSAL_SANDSTONE.getDefaultState(), 16);
			}
		}
	}

	private void replaceStone(World world, BlockPos p, Random rand) {
		IBlockState state = world.getBlockState(p);
		Block block = state.getBlock();
		if (block == Blocks.STONE) {
			switch (state.getValue(BlockStone.VARIANT)) {
				case GRANITE:
					world.setBlockState(p, Blocks.PRISMARINE.getDefaultState().withProperty(BlockPrismarine.VARIANT, BlockPrismarine.EnumType.DARK), 16);
					break;
				case ANDESITE:
					world.setBlockState(p, BeastsBlocks.ABYSSAL_STONE.getDefaultState(), 16);
					break;
				case DIORITE:
					world.setBlockState(p, Blocks.PRISMARINE.getDefaultState().withProperty(BlockPrismarine.VARIANT, BlockPrismarine.EnumType.ROUGH), 16);
					break;
			}
		} else if ((block == STONE && state.getValue(BlockStone.VARIANT) == BlockStone.EnumType.STONE) || block == Blocks.GRAVEL || block == Blocks.DIRT) {
			if (rand.nextInt(200) == 0) world.setBlockState(p, Blocks.SEA_LANTERN.getDefaultState(), 16);
			else world.setBlockState(p, BeastsBlocks.ABYSSAL_STONE.getDefaultState(), 16);
		} else if (block == Blocks.SANDSTONE && state.getValue(BlockSandStone.TYPE) == BlockSandStone.EnumType.DEFAULT) {
			world.setBlockState(p, BeastsBlocks.ABYSSAL_SANDSTONE.getDefaultState(), 16);
		} else {
			ItemStack stack = new ItemStack(block);
			if (!stack.isEmpty()) {
				for (int i : OreDictionary.getOreIDs(stack)) {
					String val = OreDictionary.getOreName(i);
					if (OreType.VALUES.containsKey(val)) {
						world.setBlockState(p, BeastsBlocks.ABYSSAL_ORE.getDefaultState().withProperty(BlockAbyssalOre.ORE, OreType.VALUES.get(val)), 16);
						break;
					}
				}
			}
		}
	}
}
