package random.beasts.common.world.gen.feature;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import random.beasts.common.entity.monster.EntityCoconutCrab;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsEntities;

import java.util.Random;

public class WorldGenPalmTrees extends WorldGenAbstractTree {
    public WorldGenPalmTrees(boolean notify) {
        super(notify);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos position) {
        if (world.getBlockState(position.down()).getBlock() == Blocks.SAND) {
            BlockState log = BeastsBlocks.PALM_LOG.getDefaultState();
            BlockState leaves = BeastsBlocks.PALM_LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, true).withProperty(BlockLeaves.DECAYABLE, true);
            int height = rand.nextInt(4) + 7;
            int radius = rand.nextInt(2) + 2;
            if (radius % 2 == 0) radius += 1;
            for (int i = -1; i < 2; i++)
                for (int j = -1; j < 2; j++)
                    for (int k = -1; k < 2; k++)
                        setBlockAndNotifyAdequately(world, position.add(i, height + k, j), leaves);
            for (int i = 1; i <= radius; i++) {
                int h = height - i + 1;
                setBlockAndNotifyAdequately(world, position.add(0, h, i + 1), leaves);
                setBlockAndNotifyAdequately(world, position.add(0, h, -i - 1), leaves);
                setBlockAndNotifyAdequately(world, position.add(i + 1, h, 0), leaves);
                setBlockAndNotifyAdequately(world, position.add(-i - 1, h, 0), leaves);

                setBlockAndNotifyAdequately(world, position.add(0, h, i + 2), leaves);
                setBlockAndNotifyAdequately(world, position.add(0, h, -i - 2), leaves);
                setBlockAndNotifyAdequately(world, position.add(i + 2, h, 0), leaves);
                setBlockAndNotifyAdequately(world, position.add(-i - 2, h, 0), leaves);

                h = height + i + 1;
                setBlockAndNotifyAdequately(world, position.add(0, h, i + 1), leaves);
                setBlockAndNotifyAdequately(world, position.add(0, h, -i - 1), leaves);
                setBlockAndNotifyAdequately(world, position.add(i + 1, h, 0), leaves);
                setBlockAndNotifyAdequately(world, position.add(-i - 1, h, 0), leaves);

                setBlockAndNotifyAdequately(world, position.add(0, h, i + 2), leaves);
                setBlockAndNotifyAdequately(world, position.add(0, h, -i - 2), leaves);
                setBlockAndNotifyAdequately(world, position.add(i + 2, h, 0), leaves);
                setBlockAndNotifyAdequately(world, position.add(-i - 2, h, 0), leaves);
            }

            BlockPos pos = position;
            setBlockAndNotifyAdequately(world, pos, log);
            for (; world.getBlockState(pos.up()).getBlock() == Blocks.AIR; ) {
                BlockPos higher = pos.up();
                setBlockAndNotifyAdequately(world, higher, log);
                pos = higher;
            }

            for (int i = -5; i < 5; i++) {
                if (i != 0) {
                    pos = world.getHeight(position.add(i, 0, i));
                    if (world.isAirBlock(pos) && world.getBlockState(pos.down()).getBlock() == Blocks.SAND) {
                        if (rand.nextInt(3) == 0) {
                            if (rand.nextInt(10) < 2) {
                                EntityCoconutCrab crab = BeastsEntities.COCONUT_CRAB.create(world);
                                crab.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
                                crab.onInitialSpawn(world.getDifficultyForLocation(pos), null);
                                world.addEntity(crab);
                            } else world.setBlockState(pos, BeastsBlocks.COCONUT.getDefaultState());
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
