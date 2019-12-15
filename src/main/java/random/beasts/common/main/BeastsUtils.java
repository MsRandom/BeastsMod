package random.beasts.common.main;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import random.beasts.client.init.BeastsCreativeTabs;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.init.BeastsItems;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Function;

public class BeastsUtils {

    public static void addToRegistry(Item item, String name) {
        addToRegistry(item, name, true);
    }

    public static void addToRegistry(Item item, String name, boolean tab) {
        item.setUnlocalizedName(name);
        item.setRegistryName(name);
        if(tab) item.setCreativeTab(BeastsCreativeTabs.MAIN);
        BeastsItems.LIST.add(item);
    }

    public static void addToRegistry(Block block, String name) {
        addToRegistry(block, name, ItemBlock::new);
    }

    public static void addToRegistry(Block block, String name, @Nullable Function<Block, Item> item) {
        addToRegistry(block, name, item, null);
    }

    public static void addToRegistry(Block block, String name, @Nullable Function<Block, Item> item, @Nullable Consumer<Item> itemCallback) {
        boolean tab = item != null;
        block.setUnlocalizedName(name);
        block.setRegistryName(name);
        if(tab) block.setCreativeTab(BeastsCreativeTabs.MAIN);
        BeastsBlocks.LIST.add(block);
        if (tab) {
            Item i = item.apply(block).setRegistryName(name);
            if (itemCallback != null) itemCallback.accept(i);
            BeastsItems.LIST.add(i);
        }
    }
}
