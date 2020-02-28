package random.beasts.common.item;

import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import random.beasts.api.item.BeastsItem;
import random.beasts.common.init.BeastsBlocks;

public class ItemGlowRoot extends BeastsItem {

    public ItemGlowRoot() {
        super("glow_root");
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        Direction facing = context.getFace();
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        PlayerEntity player = context.getPlayer();
        if (facing != Direction.DOWN || world.getBlockState(pos).getBlock() == BeastsBlocks.GLOW_ROOT_BOTTOM || world.getBlockState(pos).getBlock() == BeastsBlocks.GLOW_ROOT_TOP)
            return super.onItemUse(context);
        BlockPos down = pos.down();
        if (world.getBlockState(down) == Blocks.AIR.getDefaultState() && world.getBlockState(pos.down().down()) == Blocks.AIR.getDefaultState()) {
            world.setBlockState(down, BeastsBlocks.GLOW_ROOT_TOP.getDefaultState());
            world.setBlockState(down.down(), BeastsBlocks.GLOW_ROOT_BOTTOM.getDefaultState());
            SoundType soundtype = world.getBlockState(down).getBlock().getSoundType(world.getBlockState(pos.down()), world, pos, player);
            world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
            if (!player.abilities.isCreativeMode) player.getHeldItem(context.getHand()).shrink(1);
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }
}
