package random.beasts.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;
import random.beasts.api.main.BeastsUtils;

public class ItemShellShield extends ShieldItem {
    public ItemShellShield() {
        super(new Item.Properties().group(BeastsUtils.getRegistryTab()).maxDamage(168));
        BeastsUtils.addToRegistry(this, "shell_shield");
    }
}
