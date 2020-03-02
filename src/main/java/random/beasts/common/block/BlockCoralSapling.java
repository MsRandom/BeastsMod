package random.beasts.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.beasts.api.block.BeastsSapling;
import random.beasts.api.block.IColoredCoral;
import random.beasts.common.init.BeastsBlocks;

import java.util.Random;

public class BlockCoralSapling extends BeastsSapling implements IColoredCoral {
    public BlockCoralSapling() {
        super("coral_sapling", null, null);
        this.setDefaultState(getDefaultState().with(COLOR, CoralColor.BLUE));
    }

    @Override
    protected void generateTree(World worldIn, BlockPos pos, BlockState state, Random rand) {
        BeastsBlocks.CORAL_PLANTS.get(state.get(COLOR)).generatePlant(worldIn, pos, rand);
    }

    /*@Override
    public int damageDropped(BlockState state) {
        return state.get(TYPE).ordinal();
    }

    @Override
    public MaterialColor getMaterialColor(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return state.get(COLOR).mapColor;
    }*/
}
