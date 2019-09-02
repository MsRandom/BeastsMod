package rando.beasts.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import rando.beasts.common.utils.BeastsUtil;

import javax.annotation.Nullable;

public class BeastsSword extends ItemSword {

    private BeastsToolSet kit;

    public BeastsSword(ToolMaterial material, String name, @Nullable BeastsToolSet kit) {
        super(material);
        this.kit = kit;
        BeastsUtil.addToRegistry(this, name, true);
    }

    public BeastsSword(ToolMaterial material, String name) {
        this(material, name, null);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return (kit != null && kit.damageEntity(stack, target, attacker)) || super.hitEntity(stack, target, attacker);
    }
}
