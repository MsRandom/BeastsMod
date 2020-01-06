package random.beasts.api.item;

import net.minecraft.item.Item;
import random.beasts.api.main.BeastsUtils;

public class BeastsItem extends Item {

    public BeastsItem(String name, boolean tab) {
        BeastsUtils.addToRegistry(this, name, tab);
    }

    public BeastsItem(String name) {
        this(name, true);
    }
}
