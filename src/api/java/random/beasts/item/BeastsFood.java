package random.beasts.item;

import net.minecraft.item.ItemFood;
import random.beasts.main.BeastsUtils;

public class BeastsFood extends ItemFood {
    public BeastsFood(String name, int amount, float saturation) {
        super(amount, saturation, false);
        BeastsUtils.addToRegistry(this, name);
    }
}
