package random.beasts.common.world.gen.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import random.beasts.common.init.BeastsBlocks;

import java.util.Random;

public class WorldGenAnemone extends WorldGenerator {
    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (true) {//worldIn.getBlockState(position.down()).getBlock() == Blocks.SAND) {
            IBlockState stalk = BeastsBlocks.ANEMONE_STALK.getDefaultState();
            IBlockState tentacle = BeastsBlocks.ANEMONE_TENTACLE.getDefaultState();
            for(BlockPos pos : BlockPos.getAllInBox(position.add(-2, 0, -2), position.add(2, 5, 2))) setBlockAndNotifyAdequately(worldIn, pos, stalk);
            for(BlockPos pos : BlockPos.getAllInBox(position.add(0, 0, 0), position.add(0, 3, 0))) for (int i = -1; i <= 1; i++) setInAllDirections(worldIn, i, 1, pos, stalk);
            for (int i = -1; i <= 1; i++) setInAllDirections(worldIn, i, position.up(6), stalk);
            for (int i = -2; i <= 2; i++) for(int j = 0; j < 6; j++) setInAllDirections(worldIn, i, position.up(j), stalk);
            for(int i = 0; i < 2; i++) for (int j = -1; j <= 1; j++) setInAllDirections(worldIn, j, 1, position.up(i), stalk);
            for (int i = 0; i < 4; i++) setInAllDirections(worldIn, 3, position.up(i), stalk);
            setInAllDirections(worldIn, 0, 1, position.up(4), stalk);
            for (int i = 0; i < 2; i++) {
                setInAllDirections(worldIn, -2, 1, position.up(i), stalk);
                setInAllDirections(worldIn, 2, 1, position.up(i), stalk);
            }
            for (int i = -2; i <= 2; i++) for (int j = -2; j <= 2; j++) setBlockAndNotifyAdequately(worldIn, position.add(i, 6, j), tentacle);
            setInAllDirections(worldIn, 2, -1, position.up(6), stalk);
            //make tentacles
            setBlockAndNotifyAdequately(worldIn, position.up(6), BeastsBlocks.ANEMONE_MOUTH.getDefaultState());
            return true;
        }
        return false;
    }

    //set block using a blockpos on center x and z to all directions
    private void setInAllDirections(World world, int x, int z, BlockPos pos, IBlockState state) {
        z--;
        setBlockAndNotifyAdequately(world, pos.add(-4 - z, 0, x), state);
        setBlockAndNotifyAdequately(world, pos.add(4 + z, 0, -x), state);
        setBlockAndNotifyAdequately(world, pos.add(-x, 0, -4 - z), state);
        setBlockAndNotifyAdequately(world, pos.add(x, 0, 4 + z), state);
    }

    private void setInAllDirections(World world, int x, BlockPos pos, IBlockState state) {
        setInAllDirections(world, x, 0, pos, state);
    }
}
