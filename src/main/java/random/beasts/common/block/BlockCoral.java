package random.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import random.beasts.api.block.BeastsBlock;
import random.beasts.api.block.IColoredCoral;

public class BlockCoral extends BeastsBlock implements IColoredCoral {
    public BlockCoral() {
        super(Block.Properties.create(Material.PLANTS).hardnessAndResistance(0.6f, 0).sound(SoundType.PLANT), "coral_block", null);
        setDefaultState(getDefaultState().with(COLOR, CoralColor.BLUE));
    }

    /*@Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int i = 0; i < CoralColor.values().length; i++) items.add(i, new ItemStack(this, 1, i));
    }

    @Override
    public int damageDropped(BlockState state) {
        return state.get(COLOR).ordinal();
    }

    @Override
    public MaterialColor getMaterialColor(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return state.get(COLOR).mapColor;
    }*/
}
