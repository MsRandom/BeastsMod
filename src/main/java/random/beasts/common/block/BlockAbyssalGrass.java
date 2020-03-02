package random.beasts.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import random.beasts.api.main.BeastsUtils;

import java.util.Random;

public class BlockAbyssalGrass extends BushBlock implements IGrowable {

    public BlockAbyssalGrass() {
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        BeastsUtils.addToRegistry(this, "abyssal_grass", ItemBlock::new);
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
