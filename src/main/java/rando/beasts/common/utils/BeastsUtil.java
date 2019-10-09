package rando.beasts.common.utils;

import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import rando.beasts.client.init.BeastsCreativeTabs;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.init.BeastsItems;

public class BeastsUtil {

    public static void addToRegistry(Item item, String name, boolean tab) {
        item.setUnlocalizedName(name);
        item.setRegistryName(name);
        if(tab) item.setCreativeTab(BeastsCreativeTabs.MAIN);
        BeastsItems.LIST.add(item);
    }

    public static void addToRegistry(Block block, String name, boolean tab, Function<Block, Item> item) {
        block.setUnlocalizedName(name);
        block.setRegistryName(name);
        if(tab) block.setCreativeTab(BeastsCreativeTabs.MAIN);
        BeastsBlocks.LIST.add(block);
        if(item != null && tab) BeastsItems.LIST.add(item.apply(block).setRegistryName(name));
    }
}
