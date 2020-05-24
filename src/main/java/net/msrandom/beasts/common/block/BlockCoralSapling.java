package net.msrandom.beasts.common.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.msrandom.beasts.api.block.BeastsSapling;
import net.msrandom.beasts.common.init.BeastsBlocks;
import net.msrandom.beasts.common.item.ItemCoralBlock;

import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockCoralSapling extends BeastsSapling {
    private static final PropertyEnum<CoralColor> TYPE = PropertyEnum.create("type", CoralColor.class);

    public BlockCoralSapling() {
        super("coral_sapling", null, ItemCoralBlock::new);
        this.setDefaultState(getDefaultState().withProperty(TYPE, CoralColor.BLUE));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    protected void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        BeastsBlocks.CORAL_PLANTS.get(state.getValue(TYPE)).generatePlant(worldIn, pos, rand);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int i = 0; i < CoralColor.values().length; i++) items.add(i, new ItemStack(this, 1, i));
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.getValue(TYPE).mapColor;
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, CoralColor.values()[meta & 7]).withProperty(STAGE, (meta & 8) >> 3);
    }

    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | state.getValue(TYPE).ordinal();
        i = i | state.getValue(STAGE) << 3;
        return i;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE, STAGE);
    }
}
