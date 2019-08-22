package rando.beasts.common.item;

import net.minecraft.item.Item;
import rando.beasts.common.utils.BeastsUtil;

public class BeastsIconItem extends Item{

    public BeastsIconItem(String name) {
        BeastsUtil.addToRegistry(this, name, false);
    }
}
