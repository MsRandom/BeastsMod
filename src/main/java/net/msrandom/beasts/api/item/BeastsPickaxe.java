package net.msrandom.beasts.api.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.msrandom.beasts.api.main.BeastsUtils;

import javax.annotation.Nullable;

public class BeastsPickaxe extends ItemPickaxe {

    private final BeastsToolSet kit;

    public BeastsPickaxe(ToolMaterial material, String name, @Nullable BeastsToolSet kit) {
        super(material);
        this.kit = kit;
        BeastsUtils.addToRegistry(this, name);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return (kit != null && kit.damageEntity(stack, target, attacker)) || super.hitEntity(stack, target, attacker);
    }
}
