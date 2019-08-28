package rando.beasts.common.block;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import rando.beasts.common.item.ItemCoralBlock;
import rando.beasts.common.utils.BeastsUtil;

@SuppressWarnings("deprecation")
public class BlockCoral extends Block {

    public static final PropertyEnum<Color> COLOR = PropertyEnum.create("color", Color.class);

    public BlockCoral() {
        super(Material.PLANTS);
        setDefaultState(getDefaultState().withProperty(COLOR, Color.BLUE));
        setHardness(0.6F);
        setSoundType(SoundType.PLANT);
        BeastsUtil.addToRegistry(this, "coral_block", true, ItemCoralBlock::new);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int i = 0; i < Color.values().length; i++) items.add(i, new ItemStack(this, 1, i));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COLOR).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(COLOR, Color.values()[meta]);
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

    @Override
    public String getUnlocalizedName() {
        return super.getUnlocalizedName();
    }

    public enum Color implements IStringSerializable {
        BLUE("blue", MapColor.BLUE),
        PINK("pink", MapColor.PINK),
        PURPLE("purple", MapColor.PURPLE),
        RED("red", MapColor.RED),
        YELLOW("yellow", MapColor.YELLOW);

        public MapColor mapColor;
        private String name;

        Color(String name, MapColor color) {
            this.name = name;
            this.mapColor = color;
        }

        @Nonnull
        @Override
        public String getName() {
            return name;
        }
    }
}
