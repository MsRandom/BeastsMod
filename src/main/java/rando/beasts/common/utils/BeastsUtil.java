package rando.beasts.common.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import rando.beasts.client.init.BeastsCreativeTabs;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.init.BeastsItems;

public class BeastsUtil {

    public static void addToRegistry(Item item, String name) {
        item.setUnlocalizedName(name);
        item.setRegistryName(name);
        item.setCreativeTab(BeastsCreativeTabs.MAIN);
        BeastsItems.LIST.add(item);
    }

    public static void addToRegistry(Block block, String name) {
        block.setUnlocalizedName(name);
        block.setRegistryName(name);
        block.setCreativeTab(BeastsCreativeTabs.MAIN);
        BeastsBlocks.LIST.add(block);
        BeastsItems.LIST.add(new ItemBlock(block).setRegistryName(name));
    }
}
