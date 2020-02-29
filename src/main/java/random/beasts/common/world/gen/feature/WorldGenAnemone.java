package random.beasts.common.world.gen.feature;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import random.beasts.api.world.IBeastsGenerator;
import random.beasts.common.entity.passive.EntityAnemoneCrawler;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsEntities;

import java.util.Random;
import java.util.function.Function;

public class WorldGenAnemone implements IBeastsGenerator {
    @Override
    public boolean generate(IWorld worldIn, Random rand, BlockPos position) {
        if (worldIn.getBlockState(position.down()).getBlock() == Blocks.SAND) {
            BlockPos[] vertical = new BlockPos[9];
            for (int i = 0; i < vertical.length; i++) vertical[i] = position.up(i + 1);
            Function<Integer, BlockPos> getUp = i -> i == 0 ? position : vertical[i - 1];
            BlockState stalk = BeastsBlocks.ANEMONE_STALK.getDefaultState();
            BlockState tentacle = BeastsBlocks.ANEMONE_TENTACLE.getDefaultState();
            BlockPos.getAllInBox(position.add(-2, 0, -2), position.add(2, 5, 2)).forEach(pos -> setBlockAndNotifyAdequately(worldIn, pos, stalk));
            BlockPos.getAllInBox(position.add(0, 0, 0), position.add(0, 3, 0)).forEach(pos -> {
                for (int i = -1; i <= 1; i++) setInAllDirections(worldIn, i, 1, pos, stalk);
            });
            for (int i = -1; i <= 1; i++) setInAllDirections(worldIn, i, vertical[5], stalk);
            for (int i = -2; i <= 2; i++)
                for (int j = 0; j < 6; j++) setInAllDirections(worldIn, i, getUp.apply(j), stalk);
            for (int i = 0; i < 2; i++)
                for (int j = -1; j <= 1; j++) setInAllDirections(worldIn, j, 1, getUp.apply(i), stalk);
            for (int i = 0; i < 4; i++) setInAllDirections(worldIn, 3, i == 0 ? position : vertical[i - 1], stalk);
            setInAllDirections(worldIn, 0, 1, vertical[3], stalk);
            for (int i = 0; i < 2; i++) {
                BlockPos pos = getUp.apply(i);
                setInAllDirections(worldIn, -2, 1, pos, stalk);
                setInAllDirections(worldIn, 2, 1, pos, stalk);
            }
            for (int i = -2; i <= 2; i++)
                for (int j = -2; j <= 2; j++) setBlockAndNotifyAdequately(worldIn, position.add(i, 6, j), tentacle);
            setInAllDirections(worldIn, 2, -1, vertical[5], stalk);
            for (int i = -2; i <= 2; i++)
                for (int j = 0; j < 2; j++) setInAllDirections(worldIn, i, j, vertical[6], tentacle);
            for (int i = 0; i < rand.nextInt(2) + 3; i++)
                setInAllDirections(worldIn, 0, i, vertical[8 + Math.min(i, 1) - 1], tentacle);
            setInAllDirections(worldIn, 3, vertical[6], tentacle);
            setInAllDirections(worldIn, 2, -1, vertical[6], tentacle);
            for (int i = 0; i < rand.nextInt(3) + 2; i++)
                setInAllDirections(worldIn, 2 + i, i - 1, vertical[8 + Math.min(i, 1) - 1], tentacle);
            for (int i = 0; i < 2; i++) setInAllDirections(worldIn, 0, 2 + i, vertical[6 - i], tentacle);
            setInAllDirections(worldIn, -2, 2, vertical[5], tentacle);
            setInAllDirections(worldIn, 2, 2, vertical[5], tentacle);
            setInAllDirections(worldIn, 4, 1, vertical[5], tentacle);
            setBlockAndNotifyAdequately(worldIn, vertical[5], BeastsBlocks.ANEMONE_MOUTH.getDefaultState());
            if (worldIn instanceof World) {
                for (int i = 0; i < rand.nextInt(2) + 1; i++) {
                    EntityAnemoneCrawler crawler = BeastsEntities.ANEMONE_CRAWLER.create((World) worldIn);
                    crawler.setLocationAndAngles(position.getX() + i, vertical[6].getY(), position.getZ() + i, 0, 0);
                    crawler.onInitialSpawn(worldIn.getDifficultyForLocation(crawler.getPosition()), null);
                    worldIn.addEntity(crawler);
                }
            }
            return true;
        }
        return false;
    }

    //set block using a blockpos on center x and z to all directions
    private void setInAllDirections(IWorld world, int x, int z, BlockPos pos, BlockState state) {
        z--;
        setBlockAndNotifyAdequately(world, pos.add(-4 - z, 0, x), state);
        setBlockAndNotifyAdequately(world, pos.add(4 + z, 0, -x), state);
        setBlockAndNotifyAdequately(world, pos.add(-x, 0, -4 - z), state);
        setBlockAndNotifyAdequately(world, pos.add(x, 0, 4 + z), state);
    }

    private void setInAllDirections(IWorld world, int x, BlockPos pos, BlockState state) {
        setInAllDirections(world, x, 0, pos, state);
    }

    protected void setBlockAndNotifyAdequately(IWorld worldIn, BlockPos pos, BlockState state) {
        worldIn.setBlockState(pos, state, 3);
    }
}
