package random.beasts.common.world.gen.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import random.beasts.api.world.IBeastsGenerator;
import random.beasts.common.block.BlockCoral;
import random.beasts.common.block.CoralColor;
import random.beasts.common.init.BeastsBlocks;

import java.util.Random;

public class WorldGenAbyssalCoralCluster implements IBeastsGenerator {
    private int minSize;
    private int maxSize;
    private int coralChance;

    public WorldGenAbyssalCoralCluster(int maxSize, int minSize, int coralChance) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.coralChance = coralChance;
    }

    public boolean generate(IWorld worldIn, Random rand, BlockPos position) {
        int size = rand.nextInt(maxSize - minSize) + minSize;
        CoralColor color = rand.nextBoolean() ? CoralColor.PURPLE : CoralColor.BLUE;
        BlockPos.getAllInBox(position.add(size, size, size), position.add(-size, -size, -size)).forEach(pos -> {
            double a = Math.pow(position.getX() - pos.getX(), 2) / Math.pow(size, 2) +
                    Math.pow(position.getY() - pos.getY(), 2) / Math.pow(size, 2) +
                    Math.pow(position.getZ() - pos.getZ(), 2) / Math.pow(size, 2);
            if ((pos.getX() >> 4 == position.getX() >> 4 && pos.getZ() >> 4 == position.getZ() >> 4) && a <= 1d && rand.nextBoolean()) {
                if (rand.nextInt(coralChance) == 0)
                    worldIn.setBlockState(pos, BeastsBlocks.CORAL_BLOCK.getDefaultState().with(BlockCoral.COLOR, color), 16);
                else
                    worldIn.setBlockState(pos, BeastsBlocks.ABYSSAL_STONE.getDefaultState(), 16);
            }
        });
        return true;
    }

}
