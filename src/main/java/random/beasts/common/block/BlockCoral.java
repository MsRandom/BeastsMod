package random.beasts.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import random.beasts.api.block.BeastsBlock;
import random.beasts.common.item.ItemCoralBlock;

@SuppressWarnings("deprecation")
public class BlockCoral extends BeastsBlock {

    public static final EnumProperty<CoralColor> COLOR = EnumProperty.create("color", CoralColor.class);

    public BlockCoral() {
        super(Material.PLANTS, "coral_block", ItemCoralBlock::new);
        setDefaultState(getDefaultState().with(COLOR, CoralColor.BLUE));
        setHardness(0.6F);
        setSoundType(SoundType.PLANT);
    }

    @Override
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
    }
}
