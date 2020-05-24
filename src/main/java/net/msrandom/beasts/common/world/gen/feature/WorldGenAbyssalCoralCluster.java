package net.msrandom.beasts.common.world.gen.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.msrandom.beasts.common.block.BlockCoral;
import net.msrandom.beasts.common.block.CoralColor;
import net.msrandom.beasts.common.init.BeastsBlocks;

import java.util.Random;

public class WorldGenAbyssalCoralCluster extends WorldGenerator {
    private final int minSize;
    private final int maxSize;
    private final int coralChance;

    public WorldGenAbyssalCoralCluster(int maxSize, int minSize, int coralChance) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.coralChance = coralChance;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        int size = rand.nextInt(maxSize - minSize) + minSize;
        CoralColor color = CoralColor.BLUE;
        if (rand.nextBoolean())
            color = CoralColor.PURPLE;
        for (BlockPos pos : BlockPos.getAllInBox(position.add(size, size, size), position.add(-size, -size, -size))) {
            double a = Math.pow(position.getX() - pos.getX(), 2) / Math.pow(size, 2) +
                    Math.pow(position.getY() - pos.getY(), 2) / Math.pow(size, 2) +
                    Math.pow(position.getZ() - pos.getZ(), 2) / Math.pow(size, 2);
            if ((pos.getX() >> 4 == position.getX() >> 4 && pos.getZ() >> 4 == position.getZ() >> 4) && a <= 1d && rand.nextBoolean()) {
                if (rand.nextInt(coralChance) == 0)
                    worldIn.setBlockState(pos, BeastsBlocks.CORAL_BLOCK.getDefaultState().withProperty(BlockCoral.COLOR, color), 16);
                else
                    worldIn.setBlockState(pos, BeastsBlocks.ABYSSAL_STONE.getDefaultState(), 16);
            }
        }
        return true;
    }

}
