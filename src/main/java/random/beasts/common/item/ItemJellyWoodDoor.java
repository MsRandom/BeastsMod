package random.beasts.common.item;

import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;
import random.beasts.client.init.BeastsCreativeTabs;

public class ItemJellyWoodDoor extends ItemDoor {
    public ItemJellyWoodDoor(Block block) {
        super(block);
        String name = Objects.requireNonNull(block.getRegistryName()).getResourcePath();
        setUnlocalizedName(name);
        setCreativeTab(BeastsCreativeTabs.MAIN);
    }
}
