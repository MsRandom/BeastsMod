package rando.beasts.common.item;

import net.minecraft.item.Item;
import rando.beasts.common.utils.BeastsUtil;

public class BeastsIconItem extends Item {
    public BeastsIconItem() {
        BeastsUtil.addToRegistry(this, "icon", false);
    }
}
