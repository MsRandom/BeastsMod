package random.beasts.api.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import random.beasts.api.main.BeastsUtils;

import javax.annotation.Nullable;

public class BeastsSword extends SwordItem {
    private BeastsToolSet kit;

    public BeastsSword(IItemTier material, String name, @Nullable BeastsToolSet kit) {
        super(material, (int) material.getAttackDamage() + 3, -2, new Item.Properties().group(BeastsUtils.getRegistryTab()));
        this.kit = kit;
        BeastsUtils.addToRegistry(this, name);
    }

    public BeastsSword(IItemTier material, String name) {
        this(material, name, null);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return (kit != null && kit.damageEntity(stack, target, attacker)) || super.hitEntity(stack, target, attacker);
    }
}
