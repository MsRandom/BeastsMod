package net.msrandom.beasts.common.world.gen.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.msrandom.beasts.common.init.BeastsBlocks;

import java.util.Random;

public class WorldGenAbyssalVentCluster extends WorldGenerator {
    private final int maxNumber;
    private final int minNumber;
    private final int maxSpacing;
    private final int minSpacing;
    private final int maxHeight;
    private final int minHeight;

    public WorldGenAbyssalVentCluster(int maxHeight, int minHeight, int maxNumber, int minNumber, int maxSpacing, int minSpacing) {
        this.maxNumber = maxNumber;
        this.minNumber = minNumber;
        this.maxSpacing = maxSpacing;
        this.minSpacing = minSpacing;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
    }

    public static float getDistance(BlockPos pos1, BlockPos pos2) {
        return MathHelper.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getZ() - pos2.getZ(), 2));
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        int number = rand.nextInt(maxNumber - minNumber) + minNumber;
        int spacing = rand.nextInt(maxSpacing - minSpacing) + minSpacing;
        for (int i = 0; i < number; ++i) {
            BlockPos blockpos = position.add(rand.nextInt(spacing) - rand.nextInt(spacing), 0, rand.nextInt(spacing) - rand.nextInt(spacing));

            if ((!worldIn.provider.isNether() || blockpos.getY() < 255) && maxHeight > 1 && new ChunkPos(position).equals(new ChunkPos(blockpos))) {
                int j = Math.round(getDistance(position, blockpos) / spacing * rand.nextInt(maxHeight - minHeight)) + minHeight;

                for (int k = 0; k <= j; ++k) {
                    if (worldIn.isAirBlock(blockpos.up(k))) {
                        if (k < j)
                            worldIn.setBlockState(blockpos.up(k), BeastsBlocks.ABYSSAL_STONE.getDefaultState(), 16);
                        else
                            worldIn.setBlockState(blockpos.up(k), BeastsBlocks.ABYSSAL_VENT.getDefaultState(), 16);
                    }
                }

                if (worldIn.isAirBlock(blockpos.down())) {
                    int k = 0;
                    while (worldIn.isAirBlock(blockpos.down(k)) && blockpos.getY() - k > 0) {
                        worldIn.setBlockState(blockpos.down(k), BeastsBlocks.ABYSSAL_STONE.getDefaultState(), 16);
                        ++k;
                    }
                }

                for (BlockPos pos : BlockPos.getAllInBox(position.add(spacing, spacing, spacing), position.add(-spacing, -spacing, -spacing))) {
                    double a = Math.pow(position.getX() - pos.getX(), 2) / Math.pow(spacing, 2) +
                            Math.pow(position.getY() - pos.getY(), 2) / Math.pow(spacing, 2) +
                            Math.pow(position.getZ() - pos.getZ(), 2) / Math.pow(spacing, 2);
                    if (rand.nextDouble() > getDistance(position, blockpos) / spacing && a <= 1d) {
                        if (worldIn.getBlockState(pos).getBlock() == BeastsBlocks.ABYSSAL_SAND) {
                            worldIn.setBlockState(pos, BeastsBlocks.ABYSSAL_STONE.getDefaultState(), 16);
                        }
                    }
                }
            }
        }

        return true;
    }

}
