package rando.beasts.common.item;

import net.minecraft.item.ItemFood;
import rando.beasts.common.utils.BeastsUtil;

public class BeastsFood extends ItemFood {
    public BeastsFood(String name, int amount, float saturation) {
        super(amount, saturation, false);
        BeastsUtil.addToRegistry(this, name, true);
    }
}
