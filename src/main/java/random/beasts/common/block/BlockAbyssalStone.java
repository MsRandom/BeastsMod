package random.beasts.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import random.beasts.api.block.BeastsBlock;
import random.beasts.common.init.BeastsBlocks;

public class BlockAbyssalStone extends BeastsBlock {
    public BlockAbyssalStone() {
        super(Material.ROCK, "abyssal_stone");
        this.setHardness(2F);
        this.setResistance(5F);
        this.setSoundType(SoundType.STONE);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        IBlockState plant = plantable.getPlant(world, pos.offset(direction));
        if (plant.getBlock() == BeastsBlocks.CORAL_SAPLING) {
            return true;
        }
        return super.canSustainPlant(state, world, pos, direction, plantable);
    }
}
