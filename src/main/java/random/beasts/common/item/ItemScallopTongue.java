package random.beasts.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import random.beasts.api.item.BeastsFood;

public class ItemScallopTongue extends BeastsFood {

    private final int potionDuration;

    public ItemScallopTongue(boolean cooked, int amount, float saturation, int potionDuration) {
        super((cooked ? "cooked_" : "") + "scallop_tongue", amount, saturation);
        this.potionDuration = potionDuration;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, PlayerEntity player) {
        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 0));
        player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, potionDuration, 0));
        super.onFoodEaten(stack, worldIn, player);
    }
}
