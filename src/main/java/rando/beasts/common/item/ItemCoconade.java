package rando.beasts.common.item;

import java.util.Objects;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import rando.beasts.common.entity.projectile.EntityCoconutBomb;

public class ItemCoconade extends BeastsItem {

    public ItemCoconade(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!playerIn.capabilities.isCreativeMode) stack.shrink(1);
        worldIn.playSound(null, playerIn.getPosition(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
            EntityCoconutBomb egg = new EntityCoconutBomb(worldIn, playerIn);
            egg.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.spawnEntity(egg);
        }
        playerIn.addStat(Objects.requireNonNull(StatList.getObjectUseStats(this)));
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
