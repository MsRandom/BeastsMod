package rando.beasts.common.item;

import net.minecraft.item.ItemSword;
import rando.beasts.common.utils.BeastsUtil;

public class BeastsSword extends ItemSword {

    public BeastsSword(ToolMaterial material, String name) {
        super(material);
        BeastsUtil.addToRegistry(this, name, true);
    }
}
