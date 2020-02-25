package random.beasts.common.world.gen.feature;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import random.beasts.common.block.BlockTentacle;
import random.beasts.common.init.BeastsBlocks;

import java.util.Random;

public class WorldGenJellyfishTrees extends WorldGenAbstractTree {
    public WorldGenJellyfishTrees(boolean notify) {
        super(notify);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (worldIn.getBlockState(position.down()).getBlock() == Blocks.SAND) {
            BlockState log = BeastsBlocks.JELLY_WOOD.getDefaultState();
            BlockState leaves = BeastsBlocks.JELLY_LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, true).withProperty(BlockLeaves.DECAYABLE, true);
            int height = rand.nextInt(3) + 4;
            int radius = rand.nextInt(5) + 8;
            if (radius % 2 == 0) radius += 1;
            int hs = height - Math.abs(((radius - height) / 2) - 2) - 1;

            BlockPos leavesPos = position.add(radius / -2, 0, radius / -2);
            int h;
            for (int i = 0; i < 2; i++) {
                h = hs + i;
                for (int j = 2; j < radius - 2; j++) {
                    setBlockWithTentacle(worldIn, leavesPos.add(j, h, 0), leaves);
                    setBlockWithTentacle(worldIn, leavesPos.add(j, h, radius - 1), leaves);
                    setBlockWithTentacle(worldIn, leavesPos.add(0, h, j), leaves);
                    setBlockWithTentacle(worldIn, leavesPos.add(radius - 1, h, j), leaves);
                }
                setBlockWithTentacle(worldIn, leavesPos.add(radius - 2, h, radius - 2), leaves);
                setBlockWithTentacle(worldIn, leavesPos.add(1, h, radius - 2), leaves);
                setBlockWithTentacle(worldIn, leavesPos.add(radius - 2, h, 1), leaves);
                setBlockWithTentacle(worldIn, leavesPos.add(1, h, 1), leaves);
            }

            h = hs + 2;
            for (int i = 2; i < radius - 2; i++) {
                setBlockAndNotifyAdequately(worldIn, leavesPos.add(i, h, 1), leaves);
                setBlockAndNotifyAdequately(worldIn, leavesPos.add(i, h, radius - 2), leaves);
                setBlockAndNotifyAdequately(worldIn, leavesPos.add(1, h, i), leaves);
                setBlockAndNotifyAdequately(worldIn, leavesPos.add(radius - 2, h, i), leaves);
            }
            setBlockAndNotifyAdequately(worldIn, leavesPos.add(radius - 3, h, radius - 3), leaves);
            setBlockAndNotifyAdequately(worldIn, leavesPos.add(2, h, radius - 3), leaves);
            setBlockAndNotifyAdequately(worldIn, leavesPos.add(radius - 3, h, 2), leaves);
            setBlockAndNotifyAdequately(worldIn, leavesPos.add(2, h, 2), leaves);
            h++;
            for (int i = 3; i < radius - 3; i++) {
                setBlockAndNotifyAdequately(worldIn, leavesPos.add(i, h, 2), leaves);
                setBlockAndNotifyAdequately(worldIn, leavesPos.add(i, h, radius - 3), leaves);
                setBlockAndNotifyAdequately(worldIn, leavesPos.add(2, h, i), leaves);
                setBlockAndNotifyAdequately(worldIn, leavesPos.add(radius - 3, h, i), leaves);
            }
            for (int i = 3; i < radius - 3; i++)
                for (int j = 3; j < radius - 3; j++) {
                    BlockPos p = leavesPos.add(i, h, j);
                    setBlockAndNotifyAdequately(worldIn, p, leaves);
                }

            BlockPos pos = position;
            setBlockAndNotifyAdequately(worldIn, pos, log);
            for (; worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR || worldIn.getBlockState(pos.up()).getBlock() == BeastsBlocks.TENTACLE; ) {
                BlockPos higher = pos.up();
                setBlockAndNotifyAdequately(worldIn, higher, log);
                pos = higher;
            }
            return true;
        }
        return false;
    }

    private void generateTentacles(BlockPos pos, World world, Random rand) {
        BlockPos lower = pos.down();
        if (world.getBlockState(lower).getBlock() == Blocks.AIR && rand.nextInt(3) == 0) {
            int size = rand.nextInt(16) + 1;
            boolean full = size > 8;
            BlockState state = BeastsBlocks.TENTACLE.getDefaultState();
            setBlockAndNotifyAdequately(world, lower, full ? state.withProperty(BlockTentacle.FULL, true) : state.withProperty(BlockTentacle.SIZE, 8));
            setBlockAndNotifyAdequately(world, lower.down(), full ? state.withProperty(BlockTentacle.FULL, true) : state.withProperty(BlockTentacle.SIZE, size));
            if (full)
                setBlockAndNotifyAdequately(world, lower.down().down(), state.withProperty(BlockTentacle.SIZE, size - 8));
        }
    }

    @Override
    protected void setBlockAndNotifyAdequately(World worldIn, BlockPos pos, BlockState state) {
        this.setBlockWithTentacle(worldIn, pos, state, state.getBlock() == BeastsBlocks.JELLY_LEAVES);
    }

    private void setBlockWithTentacle(World worldIn, BlockPos pos, BlockState state) {
        this.setBlockWithTentacle(worldIn, pos, state, false);
    }

    private void setBlockWithTentacle(World worldIn, BlockPos pos, BlockState state, boolean tentacle) {
        super.setBlockAndNotifyAdequately(worldIn, pos, state);
        if (tentacle) generateTentacles(pos, worldIn, worldIn.rand);
    }
}
