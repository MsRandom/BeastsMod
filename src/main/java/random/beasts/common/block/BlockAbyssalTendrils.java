package random.beasts.common.block;

import net.minecraft.block.*;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import random.beasts.api.main.BeastsUtils;

import java.util.Random;

public class BlockAbyssalTendrils extends BushBlock implements IGrowable {
    public static final EnumProperty<BlockAbyssalTendrils.EnumBlockHalf> HALF = EnumProperty.create("half", BlockAbyssalTendrils.EnumBlockHalf.class);

    public BlockAbyssalTendrils() {
        this.setDefaultState(this.stateContainer.getBaseState().with(HALF, BlockAbyssalTendrils.EnumBlockHalf.LOWER));
        this.setHardness(0.0F);
        this.setLightLevel(0.45F);
        this.setSoundType(SoundType.PLANT);
        BeastsUtils.addToRegistry(this, "abyssal_tendrils");
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.fullCube();
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos.up());
    }

    protected void checkAndDropBlock(World worldIn, BlockPos pos, BlockState state) {
        if (!this.canBlockStay(worldIn, pos, state)) {
            boolean flag = state.get(HALF) == BlockAbyssalTendrils.EnumBlockHalf.UPPER;
            BlockPos blockpos = flag ? pos : pos.up();
            BlockPos blockpos1 = flag ? pos.down() : pos;
            Block block = flag ? this : worldIn.getBlockState(blockpos).getBlock();
            Block block1 = flag ? worldIn.getBlockState(blockpos1).getBlock() : this;

            if (!flag) this.dropBlockAsItem(worldIn, pos, state, 0); //Forge move above the setting to air.

            if (block == this) {
                worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
            }

            if (block1 == this) {
                worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
            }
        }
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, BlockState state) {
        if (state.getBlock() != this) return super.canBlockStay(worldIn, pos, state);
        if (state.get(HALF) == BlockAbyssalTendrils.EnumBlockHalf.UPPER) {
            return worldIn.getBlockState(pos.down()).getBlock() == this;
        } else {
            BlockState iblockstate = worldIn.getBlockState(pos.up());
            return iblockstate.getBlock() == this && super.canBlockStay(worldIn, pos, iblockstate);
        }
    }

    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        if (state.get(HALF) == BlockAbyssalTendrils.EnumBlockHalf.UPPER) {
            return Items.AIR;
        } else {
            return super.getItemDropped(state, rand, fortune);
        }
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos.up(), this.getDefaultState().with(HALF, BlockAbyssalTendrils.EnumBlockHalf.UPPER), 2);
    }

    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.get(HALF) == BlockAbyssalTendrils.EnumBlockHalf.UPPER) {
            if (worldIn.getBlockState(pos.down()).getBlock() == this) {
                if (player.abilities.isCreativeMode) {
                    worldIn.setBlockToAir(pos.down());
                } else {
                    worldIn.destroyBlock(pos.down(), true);
                }
            }
        } else if (worldIn.getBlockState(pos.up()).getBlock() == this) {
            worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
        }

        super.onBlockHarvested(worldIn, pos, state, player);
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

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF);
    }

    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.XZ;
    }

    public enum EnumBlockHalf implements IStringSerializable {
        UPPER,
        LOWER;

        public String toString() {
            return this.getName();
        }

        public String getName() {
            return this == UPPER ? "upper" : "lower";
        }
    }
}
