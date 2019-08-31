package rando.beasts.common.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rando.beasts.common.entity.monster.EntityBranchie;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.init.BeastsItems;

@SuppressWarnings("deprecation")
public class BlockCoralPlant extends Block {
    private static final PropertyBool NORTH = PropertyBool.create("north");
    private static final PropertyBool EAST = PropertyBool.create("east");
    private static final PropertyBool SOUTH = PropertyBool.create("south");
    private static final PropertyBool WEST = PropertyBool.create("west");
    private static final PropertyBool UP = PropertyBool.create("up");
    private static final PropertyBool DOWN = PropertyBool.create("down");
    private CoralColor color;

    public BlockCoralPlant(CoralColor color) {
        super(Material.PLANTS, color.mapColor);
        this.color = color;
        String name = "coral_plant_" + color.getName();
        setUnlocalizedName(name);
        setRegistryName(name);
        setHardness(2.0F);
        setSoundType(SoundType.PLANT);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(UP, false).withProperty(DOWN, false));
        BeastsBlocks.LIST.add(this);
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        Block block1 = worldIn.getBlockState(pos.up()).getBlock();
        Block block2 = worldIn.getBlockState(pos.north()).getBlock();
        Block block3 = worldIn.getBlockState(pos.east()).getBlock();
        Block block4 = worldIn.getBlockState(pos.south()).getBlock();
        Block block5 = worldIn.getBlockState(pos.west()).getBlock();
        return state.withProperty(DOWN, block == this || block instanceof BlockCoral || block == Blocks.SAND).withProperty(UP, block1 == this || block1 instanceof BlockCoral).withProperty(NORTH, block2 == this || block2 instanceof BlockCoral).withProperty(EAST, block3 == this || block3 instanceof BlockCoral).withProperty(SOUTH, block4 == this || block4 instanceof BlockCoral).withProperty(WEST, block5 == this || block5 instanceof BlockCoral);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
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

    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (!isActualState) state = state.getActualState(worldIn, pos);
        float f = 0.1875F;
        float f1 = 0.8125F;
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f, f, f, f1, f1, f1));
        if (state.getValue(WEST)) addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.0D, f, f, f, f1, f1));
        if (state.getValue(EAST)) addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f1, f, f, 1.0D, f1, f1));
        if (state.getValue(UP)) addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f, f1, f, f1, 1.0D, f1));
        if (state.getValue(DOWN)) addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f, 0.0D, f, f1, f, f1));
        if (state.getValue(NORTH)) addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f, f, 0.0D, f1, f1, f));
        if (state.getValue(SOUTH)) addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f, f, f1, f1, f1, 1.0D));
    }

    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!this.canSurviveAt(worldIn, pos)) worldIn.destroyBlock(pos, true);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        if(!worldIn.isRemote && player.getRNG().nextFloat() <= 0.01f && worldIn.getBlockState(pos.down()).getBlock() == Blocks.SAND) {
            EntityBranchie entity = new EntityBranchie(worldIn);
            entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
            entity.onInitialSpawn(worldIn.getDifficultyForLocation(pos), null);
            entity.setVariant(color);
            entity.scream();
            entity.setRevengeTarget(player);
            worldIn.spawnEntity(entity);
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        if (rand.nextFloat() <= 0.5f) drops.add(new ItemStack(BeastsBlocks.CORAL_BLOCK, 1, BeastsBlocks.CORAL_PLANTS.indexOf(this)));
        if (rand.nextFloat() <= 0.15f) drops.add(new ItemStack(BeastsItems.CORAL_ESSENCE, 1, BeastsBlocks.CORAL_PLANTS.indexOf(this)));
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && this.canSurviveAt(worldIn, pos);
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!this.canSurviveAt(worldIn, pos)) worldIn.scheduleUpdate(pos, this, 1);
    }

    private boolean canSurviveAt(World wordIn, BlockPos pos) {
        boolean flag = wordIn.isAirBlock(pos.up());
        boolean flag1 = wordIn.isAirBlock(pos.down());
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
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

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        Block block = blockAccess.getBlockState(pos.offset(side)).getBlock();
        return block != this && !(block instanceof BlockCoral) && (side != EnumFacing.DOWN || block != Blocks.SAND);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    public boolean generatePlant(World worldIn, BlockPos pos, Random rand) {
        if(worldIn.getBlockState(pos.down()).getBlock() == Blocks.SAND) {
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
                EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
                BlockPos blockpos1 = p_185601_1_.up(i).offset(enumfacing);
                if (Math.abs(blockpos1.getX() - p_185601_3_.getX()) < p_185601_4_ && Math.abs(blockpos1.getZ() - p_185601_3_.getZ()) < p_185601_4_ && worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.down()) && areAllNeighborsEmpty(worldIn, blockpos1, enumfacing.getOpposite())) {
                    worldIn.setBlockState(blockpos1, getDefaultState(), 2);
                    growTreeRecursive(worldIn, blockpos1, rand, p_185601_3_, p_185601_4_, p_185601_5_ + 1);
                }
            }
        }
    }

    private static boolean areAllNeighborsEmpty(World worldIn, BlockPos pos, @Nullable EnumFacing excludingSide) {
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) if (enumfacing != excludingSide && !worldIn.isAirBlock(pos.offset(enumfacing))) return false;
        return true;
    }
}
