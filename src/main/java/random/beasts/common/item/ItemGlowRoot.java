package random.beasts.common.item;

import net.minecraft.block.SoundType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.beasts.common.init.BeastsBlocks;

public class ItemGlowRoot extends BeastsItem {

    public ItemGlowRoot() {
        super("glow_root");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(facing != EnumFacing.DOWN || worldIn.getBlockState(pos).getBlock() == BeastsBlocks.GLOW_ROOT_BOTTOM || worldIn.getBlockState(pos).getBlock() == BeastsBlocks.GLOW_ROOT_TOP) return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        BlockPos down = pos.down();
        if(worldIn.getBlockState(down) == Blocks.AIR.getDefaultState() && worldIn.getBlockState(pos.down().down()) == Blocks.AIR.getDefaultState() ) {
            worldIn.setBlockState(down, BeastsBlocks.GLOW_ROOT_TOP.getDefaultState());
            worldIn.setBlockState(down.down(), BeastsBlocks.GLOW_ROOT_BOTTOM.getDefaultState());
            SoundType soundtype = worldIn.getBlockState(down).getBlock().getSoundType(worldIn.getBlockState(pos.down()), worldIn, pos, player);
            worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
            if (!player.capabilities.isCreativeMode) player.getHeldItem(hand).shrink(1);
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
