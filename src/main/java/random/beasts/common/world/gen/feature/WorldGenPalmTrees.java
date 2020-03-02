package random.beasts.common.world.gen.feature;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import random.beasts.common.entity.monster.EntityCoconutCrab;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsEntities;

import javax.annotation.Nullable;
import java.util.Random;

public class WorldGenPalmTrees extends Tree {
    @Nullable
    @Override
    protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
        return null;
    }

    @Override
    public boolean spawn(IWorld world, BlockPos position, BlockState under, Random rand) {
        if (under.getBlock() == Blocks.SAND) {
            BlockState log = BeastsBlocks.PALM_LOG.getDefaultState();
            BlockState leaves = BeastsBlocks.PALM_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, false);
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
                    pos = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, position.add(i, 0, i));
                    if (world.isAirBlock(pos) && world.getBlockState(pos.down()).getBlock() == Blocks.SAND) {
                        if (rand.nextInt(3) == 0) {
                            if (world instanceof World && rand.nextInt(10) < 2) {
                                EntityCoconutCrab crab = BeastsEntities.COCONUT_CRAB.create((World) world);
                                crab.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
                                crab.onInitialSpawn(world, world.getDifficultyForLocation(pos), SpawnReason.NATURAL, null, null);
                                world.addEntity(crab);
                            } else world.setBlockState(pos, BeastsBlocks.COCONUT.getDefaultState(), 3);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    protected void setBlockAndNotifyAdequately(IWorld worldIn, BlockPos pos, BlockState state) {
        worldIn.setBlockState(pos, state, 3);
    }
}
