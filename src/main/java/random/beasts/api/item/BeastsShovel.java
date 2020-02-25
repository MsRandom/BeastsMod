package random.beasts.api.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import random.beasts.api.main.BeastsUtils;

import javax.annotation.Nullable;

public class BeastsShovel extends ItemSpade {

    private BeastsToolSet kit;

    public BeastsShovel(ToolMaterial material, String name, @Nullable BeastsToolSet kit) {
        super(material);
        this.kit = kit;
        BeastsUtils.addToRegistry(this, name);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return (kit != null && kit.damageEntity(stack, target, attacker)) || super.hitEntity(stack, target, attacker);
    }
}
