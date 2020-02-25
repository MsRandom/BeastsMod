package random.beasts.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;
import random.beasts.api.item.BeastsItem;
import random.beasts.common.entity.projectile.EntityCoconutBomb;

import java.util.Objects;

public class ItemCoconade extends BeastsItem {

    public ItemCoconade(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!playerIn.abilities.isCreativeMode) stack.shrink(1);
        worldIn.playSound(null, playerIn.getPosition(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
            EntityCoconutBomb egg = new EntityCoconutBomb(stack, worldIn, playerIn);
            egg.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.addEntity(egg);
        }
        playerIn.addStat(Objects.requireNonNull(Stats.ITEM_USED.get(this)));
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}
