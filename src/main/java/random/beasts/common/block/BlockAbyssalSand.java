package random.beasts.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.init.BeastsBlocks;

public class BlockAbyssalSand extends FallingBlock {

    public BlockAbyssalSand() {
        this.setHardness(0.5F);
        this.setSoundType(SoundType.SAND);
        BeastsUtils.addToRegistry(this, "abyssal_sand");
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction direction, IPlantable plantable) {
        BlockState plant = plantable.getPlant(world, pos.offset(direction));
        if (plant.getBlock() == BeastsBlocks.ABYSSAL_TENDRILS || plant.getBlock() == BeastsBlocks.CORAL_SAPLING || plant.getBlock() == BeastsBlocks.TENTACLE_GRASS ||
                plant.getBlock() == BeastsBlocks.ABYSSAL_GRASS) {
            return true;
        }
        return super.canSustainPlant(state, world, pos, direction, plantable);
    }

    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState state) {
        return 5665378;
    }
}
