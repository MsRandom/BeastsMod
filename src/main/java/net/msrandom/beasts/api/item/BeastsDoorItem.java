package net.msrandom.beasts.api.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;
import net.msrandom.beasts.api.main.BeastsUtils;

import java.util.Objects;

public class BeastsDoorItem extends ItemDoor {
    public BeastsDoorItem(Block block) {
        super(block);
        String name = Objects.requireNonNull(block.getRegistryName()).getResourcePath();
        setUnlocalizedName(name);
        setCreativeTab(BeastsUtils.getRegistryTab());
    }
}
