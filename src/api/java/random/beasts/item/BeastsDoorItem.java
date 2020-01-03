package random.beasts.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;
import random.beasts.main.BeastsUtils;

import java.util.Objects;

public class BeastsDoorItem extends ItemDoor {
    public BeastsDoorItem(Block block) {
        super(block);
        String name = Objects.requireNonNull(block.getRegistryName()).getResourcePath();
        setUnlocalizedName(name);
        setCreativeTab(BeastsUtils.getRegistryTab());
    }
}
