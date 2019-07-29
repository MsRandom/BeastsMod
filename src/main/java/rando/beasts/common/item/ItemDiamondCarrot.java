package rando.beasts.common.item;

import net.minecraft.item.ItemSword;
import rando.beasts.common.utils.BeastsUtil;

public class ItemDiamondCarrot extends ItemSword {

    public ItemDiamondCarrot() {
        super(ToolMaterial.DIAMOND);
        BeastsUtil.addToRegistry(this, "diamond_carrot");
    }
}
