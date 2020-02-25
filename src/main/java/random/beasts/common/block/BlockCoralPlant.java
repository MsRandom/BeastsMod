package random.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.api.block.BeastsBlock;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

@SuppressWarnings("deprecation")
public class BlockCoralPlant extends BeastsBlock {
    private static final PropertyBool NORTH = PropertyBool.create("north");
    private static final PropertyBool EAST = PropertyBool.create("east");
    private static final PropertyBool SOUTH = PropertyBool.create("south");
    private static final PropertyBool WEST = PropertyBool.create("west");
    private static final PropertyBool UP = PropertyBool.create("up");
    private static final PropertyBool DOWN = PropertyBool.create("down");
    public CoralColor color;

    public BlockCoralPlant(CoralColor color) {
        super(Material.PLANTS, color.mapColor, "coral_plant_" + color.getName());
        this.color = color;
        setHardness(2.0F);
        setSoundType(SoundType.PLANT);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(UP, false).withProperty(DOWN, false));
    }

    @Override
    protected void register(String name, @Nullable Function<Block, Item> item) {
        BeastsUtils.addToRegistry(this, name, null);
    }

    private static boolean areAllNeighborsEmpty(World worldIn, BlockPos pos, @Nullable Direction excludingSide) {
        for (Direction enumfacing : Direction.Plane.HORIZONTAL)
            if (enumfacing != excludingSide && !worldIn.isAirBlock(pos.offset(enumfacing))) return false;
        return true;
    }

    public BlockState getActualState(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        Block block1 = worldIn.getBlockState(pos.up()).getBlock();
        Block block2 = worldIn.getBlockState(pos.north()).getBlock();
        Block block3 = worldIn.getBlockState(pos.east()).getBlock();
        Block block4 = worldIn.getBlockState(pos.south()).getBlock();
        Block block5 = worldIn.getBlockState(pos.west()).getBlock();
        return state.withProperty(DOWN, block == this || block instanceof BlockCoral || block == Blocks.SAND).withProperty(UP, block1 == this || block1 instanceof BlockCoral).withProperty(NORTH, block2 == this || block2 instanceof BlockCoral).withProperty(EAST, block3 == this || block3 instanceof BlockCoral).withProperty(SOUTH, block4 == this || block4 instanceof BlockCoral).withProperty(WEST, block5 == this || block5 instanceof BlockCoral);
    }

    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
        state = state.getActualState(source, pos);
        float f = 0.1875F;
        float f1 = state.getValue(WEST) ? 0.0F : f;
        float f2 = state.getValue(DOWN) ? 0.0F : f;
        float f3 = state.getValue(NORTH) ? 0.0F : f;
        float f4 = state.getValue(EAST) ? 1.0F : 0.8125F;
        float f5 = state.getValue(UP) ? 1.0F : 0.8125F;
        float f6 = state.getValue(SOUTH) ? 1.0F : 0.8125F;
        return new AxisAlignedBB(f1, f2, f3, f4, f5, f6);
    }

    public void addCollisionBoxToList(BlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (!isActualState) state = state.getActualState(worldIn, pos);
        float f = 0.1875F;
        float f1 = 0.8125F;
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f, f, f, f1, f1, f1));
        if (state.getValue(WEST))
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.0D, f, f, f, f1, f1));
        if (state.getValue(EAST))
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f1, f, f, 1.0D, f1, f1));
        if (state.getValue(UP))
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f, f1, f, f1, 1.0D, f1));
        if (state.getValue(DOWN))
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f, 0.0D, f, f1, f, f1));
        if (state.getValue(NORTH))
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f, f, 0.0D, f1, f1, f));
        if (state.getValue(SOUTH))
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f, f, f1, f1, f1, 1.0D));
    }

    public int getMetaFromState(BlockState state) {
        return 0;
    }

    public void updateTick(World worldIn, BlockPos pos, BlockState state, Random rand) {
        if (!this.canSurviveAt(worldIn, pos)) worldIn.destroyBlock(pos, true);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, BlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        if (rand.nextFloat() <= 0.5f) drops.add(new ItemStack(BeastsBlocks.CORAL_BLOCK, 1, color.ordinal()));
        if (rand.nextFloat() <= 0.15f) drops.add(new ItemStack(BeastsItems.CORAL_ESSENCE, 1, color.ordinal()));
    }

    public boolean isFullCube(BlockState state) {
        return false;
    }

    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && this.canSurviveAt(worldIn, pos);
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!this.canSurviveAt(worldIn, pos)) worldIn.scheduleUpdate(pos, this, 1);
    }

    private boolean canSurviveAt(World wordIn, BlockPos pos) {
        boolean flag = wordIn.isAirBlock(pos.up());
        boolean flag1 = wordIn.isAirBlock(pos.down());
        for (Direction enumfacing : Direction.Plane.HORIZONTAL) {
            BlockPos blockpos = pos.offset(enumfacing);
            Block block = wordIn.getBlockState(blockpos).getBlock();
            if (block == this) {
                if (!flag && !flag1) return false;
                Block block1 = wordIn.getBlockState(blockpos.down()).getBlock();
                if (block1 == this || block1 == Blocks.SAND) return true;
            }
        }
        Block block2 = wordIn.getBlockState(pos.down()).getBlock();
        return block2 == this || block2 == Blocks.SAND;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldSideBeRendered(BlockState blockState, IBlockAccess blockAccess, BlockPos pos, Direction side) {
        Block block = blockAccess.getBlockState(pos.offset(side)).getBlock();
        return block != this && !(block instanceof BlockCoral) && (side != Direction.DOWN || block != Blocks.SAND);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
        return BlockFaceShape.UNDEFINED;
    }

    public boolean generatePlant(World worldIn, BlockPos pos, Random rand) {
        if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.SAND) {
            worldIn.setBlockState(pos, getDefaultState(), 2);
            growTreeRecursive(worldIn, pos, rand, pos, 8, 0);
            return true;
        }
        return false;
    }

    private void growTreeRecursive(World worldIn, BlockPos p_185601_1_, Random rand, BlockPos p_185601_3_, int p_185601_4_, int p_185601_5_) {
        int i = rand.nextInt(4) + 1;
        if (p_185601_5_ == 0) ++i;
        for (int j = 0; j < i; ++j) {
            BlockPos blockpos = p_185601_1_.up(j + 1);
            if (!areAllNeighborsEmpty(worldIn, blockpos, null)) return;
            worldIn.setBlockState(blockpos, getDefaultState(), 2);
        }
        if (p_185601_5_ < 4) {
            int l = rand.nextInt(4);
            if (p_185601_5_ == 0) ++l;
            for (int k = 0; k < l; ++k) {
                Direction enumfacing = Direction.Plane.HORIZONTAL.random(rand);
                BlockPos blockpos1 = p_185601_1_.up(i).offset(enumfacing);
                if (Math.abs(blockpos1.getX() - p_185601_3_.getX()) < p_185601_4_ && Math.abs(blockpos1.getZ() - p_185601_3_.getZ()) < p_185601_4_ && worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.down()) && areAllNeighborsEmpty(worldIn, blockpos1, enumfacing.getOpposite())) {
                    worldIn.setBlockState(blockpos1, getDefaultState(), 2);
                    growTreeRecursive(worldIn, blockpos1, rand, p_185601_3_, p_185601_4_, p_185601_5_ + 1);
                }
            }
        }
    }
}
