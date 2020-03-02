package random.beasts.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import random.beasts.api.main.BeastsUtils;

import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockCoralPlant extends SixWayBlock {
    private static final BooleanProperty NORTH = BooleanProperty.create("north");
    private static final BooleanProperty EAST = BooleanProperty.create("east");
    private static final BooleanProperty SOUTH = BooleanProperty.create("south");
    private static final BooleanProperty WEST = BooleanProperty.create("west");
    private static final BooleanProperty UP = BooleanProperty.create("up");
    private static final BooleanProperty DOWN = BooleanProperty.create("down");
    public CoralColor color;

    public BlockCoralPlant(CoralColor color) {
        super(0.3125f, Block.Properties.create(Material.PLANTS, color.mapColor).hardnessAndResistance(2.0f, 0).sound(SoundType.PLANT));
        this.color = color;
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, false));
        BeastsUtils.addToRegistry(this, "coral_plant_" + color.getName(), null);
    }

    private static boolean areAllNeighborsEmpty(IWorld worldIn, BlockPos pos, @Nullable Direction excludingSide) {
        for (Direction enumfacing : Direction.Plane.HORIZONTAL)
            if (enumfacing != excludingSide && !worldIn.isAirBlock(pos.offset(enumfacing))) return false;
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.makeConnections(context.getWorld(), context.getPos());
    }

    public BlockState makeConnections(IBlockReader p_196497_1_, BlockPos p_196497_2_) {
        Block block = p_196497_1_.getBlockState(p_196497_2_.down()).getBlock();
        Block block1 = p_196497_1_.getBlockState(p_196497_2_.up()).getBlock();
        Block block2 = p_196497_1_.getBlockState(p_196497_2_.north()).getBlock();
        Block block3 = p_196497_1_.getBlockState(p_196497_2_.east()).getBlock();
        Block block4 = p_196497_1_.getBlockState(p_196497_2_.south()).getBlock();
        Block block5 = p_196497_1_.getBlockState(p_196497_2_.west()).getBlock();
        return this.getDefaultState().with(DOWN, block == this || block == Blocks.CHORUS_FLOWER || block == Blocks.END_STONE).with(UP, block1 == this || block1 == Blocks.CHORUS_FLOWER).with(NORTH, block2 == this || block2 == Blocks.CHORUS_FLOWER).with(EAST, block3 == this || block3 == Blocks.CHORUS_FLOWER).with(SOUTH, block4 == this || block4 == Blocks.CHORUS_FLOWER).with(WEST, block5 == this || block5 == Blocks.CHORUS_FLOWER);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.isValidPosition(worldIn, currentPos)) {
            worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
            return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        } else {
            Block block = facingState.getBlock();
            boolean flag = block == this || block == Blocks.CHORUS_FLOWER || facing == Direction.DOWN && block == Blocks.END_STONE;
            return stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), flag);
        }
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        if (!state.isValidPosition(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }

    /*@Override
    public void getDrops(NonNullList<ItemStack> drops, IWorldReader world, BlockPos pos, BlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        if (rand.nextFloat() <= 0.5f) drops.add(new ItemStack(BeastsBlocks.CORAL_BLOCK, 1, color.ordinal()));
        if (rand.nextFloat() <= 0.15f) drops.add(new ItemStack(BeastsItems.CORAL_ESSENCE, 1, color.ordinal()));
    }*/

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder.add(NORTH).add(EAST).add(SOUTH).add(WEST).add(UP).add(DOWN));
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos.down());
        boolean flag = !worldIn.getBlockState(pos.up()).isAir() && !blockstate.isAir();

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockpos = pos.offset(direction);
            Block block = worldIn.getBlockState(blockpos).getBlock();
            if (block == this) {
                if (flag) {
                    return false;
                }

                Block block1 = worldIn.getBlockState(blockpos.down()).getBlock();
                if (block1 == this || block1 == Blocks.END_STONE) {
                    return true;
                }
            }
        }

        Block block2 = blockstate.getBlock();
        return block2 == this || block2 == Blocks.END_STONE;
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader blockAccess, BlockPos pos, Direction side) {
        Block block = blockAccess.getBlockState(pos.offset(side)).getBlock();
        return block != this && !(block instanceof BlockCoral) && (side != Direction.DOWN || block != Blocks.SAND);
    }

    public boolean generatePlant(IWorld worldIn, BlockPos pos, Random rand) {
        if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.SAND) {
            worldIn.setBlockState(pos, getDefaultState(), 2);
            growTreeRecursive(worldIn, pos, rand, pos, 8, 0);
            return true;
        }
        return false;
    }

    private void growTreeRecursive(IWorld worldIn, BlockPos p_185601_1_, Random rand, BlockPos p_185601_3_, int p_185601_4_, int p_185601_5_) {
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
