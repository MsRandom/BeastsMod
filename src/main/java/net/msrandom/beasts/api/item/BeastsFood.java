package net.msrandom.beasts.api.item;

import net.minecraft.item.ItemFood;
import net.msrandom.beasts.api.main.BeastsUtils;

public class BeastsFood extends ItemFood {
    public BeastsFood(String name, int amount, float saturation) {
        super(amount, saturation, false);
        BeastsUtils.addToRegistry(this, name);
    }
}
