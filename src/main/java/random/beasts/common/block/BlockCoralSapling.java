package random.beasts.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import random.beasts.api.block.BeastsSapling;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.item.ItemCoralBlock;

import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockCoralSapling extends BeastsSapling {
    private static final EnumProperty<CoralColor> TYPE = EnumProperty.create("type", CoralColor.class);

    public BlockCoralSapling() {
        super("coral_sapling", null, ItemCoralBlock::new);
        this.setDefaultState(getDefaultState().with(TYPE, CoralColor.BLUE));
    }

    @Override
    public int damageDropped(BlockState state) {
        return state.get(TYPE).ordinal();
    }

    @Override
    protected void generateTree(World worldIn, BlockPos pos, BlockState state, Random rand) {
        BeastsBlocks.CORAL_PLANTS.get(state.get(TYPE)).generatePlant(worldIn, pos, rand);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int i = 0; i < CoralColor.values().length; i++) items.add(i, new ItemStack(this, 1, i));
    }

    @Override
    public MaterialColor getMaterialColor(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return state.get(TYPE).mapColor;
    }
}
