package random.beasts.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;
import random.beasts.api.item.BeastsFood;
import random.beasts.common.entity.item.EntityThrownCoconut;

import java.util.Objects;

public class ItemCoconut extends BeastsFood {
    public ItemCoconut() {
        super("coconut", 2, 0.4F);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.isSneaking()) {
            ItemStack itemstack = playerIn.getHeldItem(handIn);
            if (!playerIn.abilities.isCreativeMode) itemstack.shrink(1);
            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

            if (!worldIn.isRemote) {
                EntityThrownCoconut entity = new EntityThrownCoconut(itemstack, worldIn, playerIn);
                entity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
                worldIn.addEntity(entity);
            }

            playerIn.addStat(Objects.requireNonNull(Stats.ITEM_USED.get(this)));
            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
        } else return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
