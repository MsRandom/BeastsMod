package net.msrandom.beasts.common.block;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockShellPiece extends BlockShell {
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.3, 0, 0.3, 0.7, 0.28, 0.7);
    private final AxisAlignedBB aabb;

    public BlockShellPiece(String name) {
        this(name, AABB);
    }

    public BlockShellPiece(String name, AxisAlignedBB aabb) {
        super(name);
        this.aabb = aabb;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return aabb;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.AIR;
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(this);
    }

    public static class Brown extends BlockShellPiece {
        private static final AxisAlignedBB SHELL_AABB = new AxisAlignedBB(0.3, 0, 0.3, 0.7, 0.1, 0.7);

        public Brown() {
            super("brown_shell_piece", SHELL_AABB);
        }
    }

    public static class LightBrown extends BlockShellPiece {
        private static final AxisAlignedBB SHELL_AABB = new AxisAlignedBB(0.4, 0, 0.25, 0.6, 0.5, 0.75);

        public LightBrown() {
            super("light_brown_shell_piece", SHELL_AABB);
        }
    }

    public static class Tan extends BlockShellPiece {
        private static final AxisAlignedBB SHELL_AABB = new AxisAlignedBB(0.38, 0, 0, 0.62, 0.3, 0.9375);

        public Tan() {
            super("tan_shell_piece", SHELL_AABB);
        }
    }
}
