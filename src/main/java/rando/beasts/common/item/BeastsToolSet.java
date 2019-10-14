package rando.beasts.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class BeastsToolSet {

    public final ItemAxe axe;
    public final ItemPickaxe pickaxe;
    public final ItemSpade shovel;
    public final ItemSword sword;
    public final ItemHoe hoe;

    public BeastsToolSet(Item.ToolMaterial material, String name) {
        this.axe = new BeastsAxe(material, name + "_axe", this);
        this.pickaxe = new BeastsPickaxe(material, name + "_pickaxe", this);
        this.shovel = new BeastsShovel(material, name + "_shovel", this);
        this.sword = new BeastsSword(material, name + "_sword", this);
        this.hoe = new BeastsHoe(material, name + "_hoe", this);
    }

    boolean damageEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return false;
    }
}
