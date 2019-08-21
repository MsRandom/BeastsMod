package rando.beasts.common.item;

import net.minecraft.item.ItemSword;
import rando.beasts.common.utils.BeastsUtil;

public class ItemCookedDaggerFish extends ItemSword {

    public ItemCookedDaggerFish() {
        super(ToolMaterial.DIAMOND);
        BeastsUtil.addToRegistry(this, "cooked_daggerfish");
    }
}
