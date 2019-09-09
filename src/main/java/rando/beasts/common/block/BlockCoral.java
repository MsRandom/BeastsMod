package rando.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import rando.beasts.common.item.ItemCoralBlock;
import rando.beasts.common.utils.BeastsUtil;

@SuppressWarnings("deprecation")
public class BlockCoral extends BeastsBlock {

    public static final PropertyEnum<CoralColor> COLOR = PropertyEnum.create("color", CoralColor.class);

    public BlockCoral() {
        super(Material.PLANTS, "coral_block", ItemCoralBlock::new);
        setDefaultState(getDefaultState().withProperty(COLOR, CoralColor.BLUE));
        setHardness(0.6F);
        setSoundType(SoundType.PLANT);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int i = 0; i < CoralColor.values().length; i++) items.add(i, new ItemStack(this, 1, i));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COLOR).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(COLOR, CoralColor.values()[meta]);
    }
    
    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(COLOR).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COLOR);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.getValue(COLOR).mapColor;
    }
}
