package random.beasts.common.item;

import net.minecraft.item.ItemShield;
import random.beasts.common.main.BeastsUtils;

public class ItemShellShield extends ItemShield
{
    public ItemShellShield()
    {
        this.setMaxDamage(168);
        BeastsUtils.addToRegistry(this, "shell_shield");
    }
}
