package random.beasts.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import random.beasts.api.block.BeastsBlock;
import random.beasts.common.item.ItemOreBlock;

public class BlockAbyssalOre extends BeastsBlock {

    public static final PropertyEnum<OreType> ORE = PropertyEnum.create("ore", OreType.class);

    public BlockAbyssalOre(String name) {
        super(Material.ROCK, name, ItemOreBlock::new);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ORE, OreType.COAL));
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int i = 0; i < OreType.values().length; i++) items.add(i, new ItemStack(this, 1, i));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ORE).ordinal();
    }

    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(ORE, OreType.values()[meta]);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(ORE).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ORE);
    }

    /*@Override
    public int getHarvestLevel(IBlockState state) {
        return super.getHarvestLevel(state);
    }

    @Nullable
    @Override
    public String getHarvestTool(IBlockState state) {
        return "pickaxe";
    }

    public int damageDropped(IBlockState state) {
        return ((OreType)state.getValue(VARIANT)).getMetadata();
    }

    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (OreType ore : OreType.values())
        {
            items.add(new ItemStack(this, 1, ore.getMetadata()));
        }
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, OreType.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((OreType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }*/
}
