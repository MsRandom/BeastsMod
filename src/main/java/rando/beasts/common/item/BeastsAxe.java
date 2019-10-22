package rando.beasts.common.item;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import rando.beasts.common.main.BeastsUtils;

public class BeastsAxe extends ItemAxe {

    private BeastsToolSet kit;

    public BeastsAxe(ToolMaterial material, String name, @Nullable BeastsToolSet kit) {
        super(material);
        this.kit = kit;
        BeastsUtils.addToRegistry(this, name, true);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return (kit != null && kit.damageEntity(stack, target, attacker)) || super.hitEntity(stack, target, attacker);
    }
}
