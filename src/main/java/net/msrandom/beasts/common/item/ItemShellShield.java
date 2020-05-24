package net.msrandom.beasts.common.item;

import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.msrandom.beasts.api.main.BeastsUtils;

public class ItemShellShield extends ItemShield {
    public ItemShellShield() {
        this.setMaxDamage(168);
        BeastsUtils.addToRegistry(this, "shell_shield");
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }
}
