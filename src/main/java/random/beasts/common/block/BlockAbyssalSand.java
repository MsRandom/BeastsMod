package random.beasts.common.block;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.init.BeastsBlocks;

public class BlockAbyssalSand extends BlockFalling {

    public BlockAbyssalSand() {
        this.setHardness(0.5F);
        this.setSoundType(SoundType.SAND);
        BeastsUtils.addToRegistry(this, "abyssal_sand", ItemBlock::new);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        IBlockState plant = plantable.getPlant(world, pos.offset(direction));
        if (plant.getBlock() == BeastsBlocks.ABYSSAL_TENDRILS || plant.getBlock() == BeastsBlocks.CORAL_SAPLING || plant.getBlock() == BeastsBlocks.TENTACLE_GRASS ||
                plant.getBlock() == BeastsBlocks.ABYSSAL_GRASS) {
            return true;
        }
        return super.canSustainPlant(state, world, pos, direction, plantable);
    }

    @SideOnly(Side.CLIENT)
    public int getDustColor(IBlockState state) {
        return 5665378;
    }
}
