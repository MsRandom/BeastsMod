package random.beasts.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import random.beasts.api.item.BeastsFood;
import random.beasts.common.init.BeastsItems;

public class BeastsCoconutBowl extends BeastsFood {

    private final EffectInstance[] effects;

    public BeastsCoconutBowl(String name, int amount, float saturation, EffectInstance... effects) {
        super(name, amount, saturation);
        this.effects = effects;
        setMaxStackSize(2);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, PlayerEntity player) {
        for (EffectInstance effect : effects) player.addPotionEffect(effect);
        player.inventory.add(1, new ItemStack(BeastsItems.COCONUT_BOWL));
        super.onFoodEaten(stack, worldIn, player);
    }
}
