package random.beasts.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import random.beasts.api.main.BeastsUtils;

import java.util.Random;

public class BlockTentacleGrass extends BushBlock implements IGrowable {
    public BlockTentacleGrass() {
        this.setHardness(2.5F);
        this.setSoundType(SoundType.SLIME);
        BeastsUtils.addToRegistry(this, "tentacle_grass");
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        entityIn.motionX *= 0.2d;
        entityIn.motionY *= 0.2d;
        entityIn.motionZ *= 0.2d;
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.fullCube();
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos.up());
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        spawnAsEntity(worldIn, pos, new ItemStack(this));
    }
}
