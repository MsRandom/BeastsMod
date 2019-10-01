package rando.beasts.common.world.gen.feature;

import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import rando.beasts.common.block.BeastsBlock;
import rando.beasts.common.entity.monster.EntityCoconutCrab;
import rando.beasts.common.init.BeastsBlocks;

import java.util.Random;

public class WorldGenPalmTrees extends WorldGenAbstractTree {
    public WorldGenPalmTrees(boolean notify) {
        super(notify);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos position) {
        if (world.getBlockState(position.down()).getBlock() == Blocks.SAND) {
            IBlockState log = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
            IBlockState leaves = BeastsBlocks.PALM_LEAVES.getDefaultState();
            int height = rand.nextInt(4) + 7;
            int radius = rand.nextInt(4) + 2;
            if (radius % 2 == 0) radius += 1;
            for (int i = -1; i < 2; i++) for (int j = -1; j < 2; j++) setBlockAndNotifyAdequately(world, position.add(i, height, j), leaves);
            for (int i = 1; i < radius; i++) {
                int h = height - i + 1;
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
                if(i != 0) {
                    pos = position.add(i, 0, i);
                    if (rand.nextInt(3) == 0) {
                        if (rand.nextBoolean()) {
                            EntityCoconutCrab crab = new EntityCoconutCrab(world);
                            crab.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
                            crab.onInitialSpawn(world.getDifficultyForLocation(pos), null);
                            world.spawnEntity(crab);
                        } else world.setBlockState(pos, BeastsBlocks.COCONUT.getDefaultState());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
