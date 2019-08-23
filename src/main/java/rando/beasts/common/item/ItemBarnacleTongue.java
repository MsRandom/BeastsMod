package rando.beasts.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemBarnacleTongue extends BeastsFood {

    private final int potionDuration;

    public ItemBarnacleTongue(boolean cooked, int amount, float saturation, int potionDuration) {
        super((cooked?"cooked":"") + "barnacle_tongue", amount, saturation);
        this.potionDuration = potionDuration;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 0));
        player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, potionDuration, 0));
        super.onFoodEaten(stack, worldIn, player);
    }
}
