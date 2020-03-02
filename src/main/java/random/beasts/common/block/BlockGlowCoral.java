package random.beasts.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import random.beasts.api.main.BeastsUtils;

import java.util.Random;

public class BlockGlowCoral extends BushBlock implements IGrowable {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BlockGlowCoral(String name) {
        super(Block.Properties.create(Material.PLANTS).lightValue(11).sound(SoundType.PLANT));
        BeastsUtils.addToRegistry(this, name);
    }

    protected static boolean canPlaceBlock(IWorldReader worldIn, BlockPos pos, Direction direction) {
        BlockPos blockpos = pos.offset(direction);
        return worldIn.getBlockState(blockpos).func_224755_d(worldIn, blockpos, direction.getOpposite());
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return canPlaceBlock(worldIn, pos, state.get(FACING).getOpposite());
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        spawnAsEntity(worldIn, pos, new ItemStack(this));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder.add(FACING));
    }
}
