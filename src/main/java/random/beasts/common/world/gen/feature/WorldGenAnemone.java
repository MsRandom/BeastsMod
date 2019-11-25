package random.beasts.common.world.gen.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import random.beasts.common.entity.passive.EntityAnemoneCrawler;
import random.beasts.common.init.BeastsBlocks;

import java.util.Random;
import java.util.function.Function;

public class WorldGenAnemone extends WorldGenerator {
    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (worldIn.getBlockState(position.down()).getBlock() == Blocks.SAND) {
            BlockPos[] vertical = new BlockPos[9];
            for (int i = 0; i < vertical.length; i++) vertical[i] = position.up(i + 1);
            Function<Integer, BlockPos> getUp = i -> i == 0 ? position : vertical[i - 1];
            IBlockState stalk = BeastsBlocks.ANEMONE_STALK.getDefaultState();
            IBlockState tentacle = BeastsBlocks.ANEMONE_TENTACLE.getDefaultState();
            for(BlockPos pos : BlockPos.getAllInBox(position.add(-2, 0, -2), position.add(2, 5, 2))) setBlockAndNotifyAdequately(worldIn, pos, stalk);
            for(BlockPos pos : BlockPos.getAllInBox(position.add(0, 0, 0), position.add(0, 3, 0))) for (int i = -1; i <= 1; i++) setInAllDirections(worldIn, i, 1, pos, stalk);
            for (int i = -1; i <= 1; i++) setInAllDirections(worldIn, i, vertical[5], stalk);
            for (int i = -2; i <= 2; i++) for(int j = 0; j < 6; j++) setInAllDirections(worldIn, i, getUp.apply(j), stalk);
            for(int i = 0; i < 2; i++) for (int j = -1; j <= 1; j++) setInAllDirections(worldIn, j, 1, getUp.apply(i), stalk);
            for (int i = 0; i < 4; i++) setInAllDirections(worldIn, 3, i == 0 ? position : vertical[i - 1], stalk);
            setInAllDirections(worldIn, 0, 1, vertical[3], stalk);
            for (int i = 0; i < 2; i++) {
                BlockPos pos = getUp.apply(i);
                setInAllDirections(worldIn, -2, 1, pos, stalk);
                setInAllDirections(worldIn, 2, 1, pos, stalk);
            }
            for (int i = -2; i <= 2; i++) for (int j = -2; j <= 2; j++) setBlockAndNotifyAdequately(worldIn, position.add(i, 6, j), tentacle);
            setInAllDirections(worldIn, 2, -1, vertical[5], stalk);
            for (int i = -2; i <= 2; i++) for (int j = 0; j < 2; j++) setInAllDirections(worldIn, i, j, vertical[6], tentacle);
            for (int i = 0; i < rand.nextInt(2) + 3; i++) setInAllDirections(worldIn, 0, i, vertical[8 + Math.min(i, 1) - 1], tentacle);
            setInAllDirections(worldIn, 3, vertical[6], tentacle);
            setInAllDirections(worldIn, 2, -1, vertical[6], tentacle);
            for (int i = 0; i < rand.nextInt(3) + 2; i++) setInAllDirections(worldIn, 2 + i, i - 1, vertical[8 + Math.min(i, 1) - 1], tentacle);
            for (int i = 0; i < 2; i++) setInAllDirections(worldIn, 0, 2 + i, vertical[6 - i], tentacle);
            setInAllDirections(worldIn, -2, 2, vertical[5], tentacle);
            setInAllDirections(worldIn, 2, 2, vertical[5], tentacle);
            setInAllDirections(worldIn, 4, 1, vertical[5], tentacle);
            setBlockAndNotifyAdequately(worldIn, vertical[5], BeastsBlocks.ANEMONE_MOUTH.getDefaultState());
            for (int i = 0; i < rand.nextInt(2) + 1; i++) {
                EntityAnemoneCrawler crawler = new EntityAnemoneCrawler(worldIn);
                crawler.setLocationAndAngles(position.getX() + i, vertical[6].getY(), position.getZ() + i, 0, 0);
                crawler.onInitialSpawn(worldIn.getDifficultyForLocation(crawler.getPosition()), null);
                worldIn.spawnEntity(crawler);
            }
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
