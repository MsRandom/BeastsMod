package rando.beasts.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import rando.beasts.common.utils.BeastsUtil;

import javax.annotation.Nullable;

public class BeastsPickaxe extends ItemPickaxe {

    private BeastsToolSet kit;

    public BeastsPickaxe(ToolMaterial material, String name, @Nullable BeastsToolSet kit) {
        super(material);
        this.kit = kit;
        BeastsUtil.addToRegistry(this, name, true);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return (kit != null && kit.damageEntity(stack, target, attacker)) || super.hitEntity(stack, target, attacker);
    }
}
