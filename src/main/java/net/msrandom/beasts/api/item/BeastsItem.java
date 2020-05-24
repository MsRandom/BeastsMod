package net.msrandom.beasts.api.item;

import net.minecraft.item.Item;
import net.msrandom.beasts.api.main.BeastsUtils;

public class BeastsItem extends Item {

    public BeastsItem(String name, boolean tab) {
        BeastsUtils.addToRegistry(this, name, tab);
    }

    public BeastsItem(String name) {
        this(name, true);
    }
}
