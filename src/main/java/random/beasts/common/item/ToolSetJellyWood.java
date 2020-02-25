package random.beasts.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import random.beasts.api.item.BeastsToolSet;

public class ToolSetJellyWood extends BeastsToolSet {
    public ToolSetJellyWood() {
        super(ItemTier.WOOD, "jelly");
    }

    @Override
    protected boolean damageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.isPotionActive(Effects.POISON)) {
            EffectInstance effect = new EffectInstance(Effects.POISON, 100);
            if (target.isPotionApplicable(effect)) target.addPotionEffect(effect);
            return true;
        }
        return super.damageEntity(stack, target, attacker);
    }
}
