package rando.beasts.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;
import rando.beasts.client.init.BeastsCreativeTabs;

import java.util.Objects;

public class ItemJellyWoodDoor extends ItemDoor {
    public ItemJellyWoodDoor(Block block) {
        super(block);
        String name = Objects.requireNonNull(block.getRegistryName()).getResourcePath();
        setUnlocalizedName(name);
        setCreativeTab(BeastsCreativeTabs.MAIN);
    }
}
