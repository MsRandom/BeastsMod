package random.beasts.api.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;
import random.beasts.api.main.BeastsUtils;

public class BeastsDoorItem extends TallBlockItem {
    public BeastsDoorItem(Block block) {
        super(block, new Item.Properties().group(BeastsUtils.getRegistryTab()));
    }
}
