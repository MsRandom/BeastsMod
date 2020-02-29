package random.beasts.common.item;

import random.beasts.api.item.BeastsItem;
import random.beasts.common.block.CoralColor;

public class ItemCoralEssence extends BeastsItem {
    public ItemCoralEssence(CoralColor color) {
        super("coral_essence_" + color.getName());
    }
}
