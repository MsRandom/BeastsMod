package net.msrandom.beasts.common.block;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.msrandom.beasts.api.block.BeastsLeaves;
import net.msrandom.beasts.api.main.BeastsUtils;
import net.msrandom.beasts.common.entity.item.EntityFallingCoconut;
import net.msrandom.beasts.common.init.BeastsBlocks;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockPalmTreeLeaves extends BeastsLeaves {
    public BlockPalmTreeLeaves() {
        BeastsUtils.addToRegistry(this, "palmleaves");
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (rand.nextInt(200) == 0) worldIn.spawnEntity(new EntityFallingCoconut(worldIn, pos));
    }

    protected int getSaplingDropChance(IBlockState state) {
        return 40;
    }

    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(this);
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (!state.getValue(DECAYABLE)) i |= 4;
        if (state.getValue(CHECK_DECAY)) i |= 8;
        return i;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BeastsBlocks.PALM_SAPLING);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
    }

    public int damageDropped(IBlockState state) {
        return 0;
    }

    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS) player.addStat(StatList.getBlockStats(this));
        else super.harvestBlock(worldIn, player, pos, state, te, stack);
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return BlockPlanks.EnumType.JUNGLE;
    }

    @Override
    public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this));
    }
}
