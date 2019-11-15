package random.beasts.common.world.gen.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import random.beasts.common.world.biome.BiomeDriedReef;

public class ShellGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator generator, IChunkProvider provider)
	{
		switch(world.provider.getDimension())
		{
			case 1:
				break;
				
			case 0:
				generateStructure(new WorldGenStructure("shell1"), world, rand, chunkX, chunkZ, 20, Blocks.SAND, BiomeDriedReef.class);
				generateStructure(new WorldGenStructure("shell2"), world, rand, chunkX, chunkZ, 20, Blocks.SAND, BiomeDriedReef.class);
				generateStructure(new WorldGenStructure("shell3"), world, rand, chunkX, chunkZ, 20, Blocks.SAND, BiomeDriedReef.class);
				generateStructure(new WorldGenStructure("shell4"), world, rand, chunkX, chunkZ, 20, Blocks.SAND, BiomeDriedReef.class);
				break;
				
			case -1:
				break;
		}
	}
	
	private void generateStructure(WorldGenerator generator, World world, Random random, 
			int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));
		
		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = calculateGenerationHeight(world, x, z, topBlock);
		BlockPos pos = new BlockPos(x, y, z);
		
		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
		
		if(classesList.contains(biome))
		{
			if(random.nextInt(chance) == 0)
			{
				generator.generate(world, random, pos);
			}
		}
	}
	
	private static int calculateGenerationHeight(World world, int x, int z, Block topBlock)
	{
		int y = world.getHeight();
		boolean foundGround = false;
		
		while(!foundGround && y-- >= 0)
		{
			Block block = world.getBlockState(new BlockPos(x,y,z)).getBlock();
			foundGround = block == topBlock;
		}
		
		return y;
	}
}
