package rando.beasts.common.item;

import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;
import rando.beasts.common.init.BeastsItems;
import rando.beasts.common.utils.BeastsUtil;

public class ItemCookedDaggerFish extends ItemSword {


    public ItemCookedDaggerFish() {
        super(BeastsItems.SWORDFISH_MAT);
        BeastsUtil.addToRegistry(this, "cooked_daggerfish", true);
    }
}
