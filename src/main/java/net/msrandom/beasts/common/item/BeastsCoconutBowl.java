package net.msrandom.beasts.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.msrandom.beasts.api.item.BeastsFood;
import net.msrandom.beasts.common.init.BeastsItems;

public class BeastsCoconutBowl extends BeastsFood {

    private final PotionEffect[] effects;

    public BeastsCoconutBowl(String name, int amount, float saturation, PotionEffect... effects) {
        super(name, amount, saturation);
        this.effects = effects;
        setMaxStackSize(2);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        for (PotionEffect effect : effects) player.addPotionEffect(effect);
        player.inventory.add(1, new ItemStack(BeastsItems.COCONUT_BOWL));
        super.onFoodEaten(stack, worldIn, player);
    }
}
