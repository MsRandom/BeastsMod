package random.beasts.common.block;

import net.minecraft.block.*;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import random.beasts.api.main.BeastsUtils;

import java.util.Random;

public class BlockGlowCoral extends BushBlock implements IGrowable {
    public static final DirectionProperty FACING = DirectionProperty.create("facing");

    public BlockGlowCoral(String name) {
        this.setHardness(0.0F);
        this.setLightLevel(0.75F);
        this.setSoundType(SoundType.PLANT);
        BeastsUtils.addToRegistry(this, name);
    }

    protected static boolean canPlaceBlock(World worldIn, BlockPos pos, Direction direction) {
        BlockPos blockpos = pos.offset(direction.getOpposite());
        BlockState iblockstate = worldIn.getBlockState(blockpos);
        boolean flag = iblockstate.getBlockFaceShape(worldIn, blockpos, direction) == BlockFaceShape.SOLID;
        Block block = iblockstate.getBlock();

        if (direction == Direction.UP) {
            return iblockstate.isTopSolid() || !isExceptionBlockForAttaching(block) && flag;
        } else {
            return !isExceptBlockForAttachWithPiston(block) && flag;
        }
    }

    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, Direction side) {
        return canPlaceBlock(worldIn, pos, side);
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        for (Direction enumfacing : Direction.values()) {
            if (canPlaceBlock(worldIn, pos, enumfacing)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, BlockState state) {
        return canPlaceBlock(worldIn, pos, worldIn.getBlockState(pos).get(FACING));
    }

    public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer) {
        return canPlaceBlock(worldIn, pos, facing) ? this.getDefaultState().with(FACING, facing) : this.getDefaultState().with(FACING, Direction.DOWN);
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (this.checkForDrop(worldIn, pos, state) && !canPlaceBlock(worldIn, pos, state.get(FACING))) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    private boolean checkForDrop(World worldIn, BlockPos pos, BlockState state) {
        if (this.canPlaceBlockAt(worldIn, pos)) {
            return true;
        } else {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        }
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
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }
}
