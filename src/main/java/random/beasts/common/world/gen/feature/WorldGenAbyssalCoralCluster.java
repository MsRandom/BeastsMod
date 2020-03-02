package random.beasts.common.world.gen.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import random.beasts.common.block.BlockCoral;
import random.beasts.common.block.CoralColor;
import random.beasts.common.init.BeastsBlocks;

import java.util.Random;

public class WorldGenAbyssalCoralCluster extends WorldGenerator {
    private int minSize;
    private int maxSize;
    private int coralChance;

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
            if (new ChunkPos(position).equals(new ChunkPos(pos)) && a <= 1d && rand.nextBoolean()) {
                if (rand.nextInt(coralChance) == 0)
                    worldIn.setBlockState(pos, BeastsBlocks.CORAL_BLOCK.getDefaultState().withProperty(BlockCoral.COLOR, color), 16);
                else
                    worldIn.setBlockState(pos, BeastsBlocks.ABYSSAL_STONE.getDefaultState(), 16);
            }
        }
        return true;
    }

}
