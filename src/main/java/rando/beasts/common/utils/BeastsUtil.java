package rando.beasts.common.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import rando.beasts.client.init.BeastsCreativeTabs;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.init.BeastsItems;

import java.util.function.Function;

public class BeastsUtil {

    public static void addToRegistry(Item item, String name, boolean tab) {
        item.setUnlocalizedName(name);
        item.setRegistryName(name);
        if(tab) item.setCreativeTab(BeastsCreativeTabs.MAIN);
        BeastsItems.LIST.add(item);
    }

    public static void addToRegistry(Block block, String name, boolean tab, Function<Block, ItemBlock> item) {
        block.setUnlocalizedName(name);
        block.setRegistryName(name);
        if(tab) block.setCreativeTab(BeastsCreativeTabs.MAIN);
        BeastsBlocks.LIST.add(block);
        BeastsItems.LIST.add(item.apply(block).setRegistryName(name));
    }
}
