package random.beasts.api.item;

import net.minecraft.item.Item;
import random.beasts.api.main.BeastsUtils;

public class BeastsItem extends Item {
    public BeastsItem(Item.Properties properties, String name) {
        super(properties);
        BeastsUtils.addToRegistry(this, name);
    }

    public BeastsItem(String name, boolean tab) {
        this(tab ? new Item.Properties().group(BeastsUtils.getRegistryTab()) : new Item.Properties(), name);
    }

    public BeastsItem(String name) {
        this(name, true);
    }
}
