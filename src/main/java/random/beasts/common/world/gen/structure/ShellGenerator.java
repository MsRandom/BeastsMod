package random.beasts.common.world.gen.structure;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import random.beasts.api.world.gen.structure.BeastsWorldGenerator;
import random.beasts.common.init.BeastsBiomes;
import random.beasts.common.init.BeastsStructures;

import java.util.Random;

public class ShellGenerator extends BeastsWorldGenerator {
    private static int calculateGenerationHeight(World world, int x, int z) {
        int y = world.getHeight();
        boolean foundGround = false;

        while (!foundGround && y-- >= 0) {
            Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
            foundGround = block == Blocks.SAND;
        }

        return y;
    }

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator generator, IChunkProvider provider) {
        if (world.provider.getDimension() == 0) for (WorldGenStructure structure : BeastsStructures.SHELLS)
            generateStructure(structure, world, rand, chunkX, chunkZ);
    }

    private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ) {
        int x = (chunkX * 16) + random.nextInt(15) + 8;
        int z = (chunkZ * 16) + random.nextInt(15) + 8;
        int y = calculateGenerationHeight(world, x, z);
        BlockPos pos = new BlockPos(x, y, z);
        if (world.provider.getBiomeForCoords(pos) == BeastsBiomes.DRIED_REEF)
            if (random.nextInt(20) == 0) generator.generate(world, random, pos);
    }
}
