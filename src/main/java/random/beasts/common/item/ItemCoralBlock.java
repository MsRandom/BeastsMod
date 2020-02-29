package random.beasts.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.block.CoralColor;

import java.util.Objects;

public class ItemCoralBlock extends BlockItem {
    public ItemCoralBlock(Block block, CoralColor color) {
        super(block, new Item.Properties().group(BeastsUtils.getRegistryTab()));
        BeastsUtils.addToRegistry(this, Objects.requireNonNull(block.getRegistryName()).getPath() + "_" + color.getName());
    }
}
