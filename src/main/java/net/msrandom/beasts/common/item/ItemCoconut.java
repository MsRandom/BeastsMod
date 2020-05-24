package net.msrandom.beasts.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.msrandom.beasts.api.item.BeastsFood;
import net.msrandom.beasts.common.entity.item.EntityThrownCoconut;

import java.util.Objects;

public class ItemCoconut extends BeastsFood {
    public ItemCoconut() {
        super("coconut", 2, 0.4F);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (playerIn.isSneaking()) {
            ItemStack itemstack = playerIn.getHeldItem(handIn);
            if (!playerIn.capabilities.isCreativeMode) itemstack.shrink(1);
            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!worldIn.isRemote) {
                EntityThrownCoconut entity = new EntityThrownCoconut(worldIn, playerIn);
                entity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
                worldIn.spawnEntity(entity);
            }

            playerIn.addStat(Objects.requireNonNull(StatList.getObjectUseStats(this)));
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        } else return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
