package random.beasts.main;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import random.beasts.init.BeastsRegistries;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Function;

public class BeastsUtils {

    private static CreativeTabs defaultTab;

    public static CreativeTabs getRegistryTab() {
        return defaultTab;
    }

    public static void setRegistryTab(CreativeTabs tab) {
        defaultTab = tab;
    }

    public static void addToRegistry(Item item, String name) {
        addToRegistry(item, name, true);
    }

    public static void addToRegistry(Item item, String name, boolean tab) {
        item.setUnlocalizedName(name);
        item.setRegistryName(name);
        if (tab && defaultTab != null) item.setCreativeTab(defaultTab);
        BeastsRegistries.ITEMS.add(item);
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
        if (tab && defaultTab != null) block.setCreativeTab(defaultTab);
        BeastsRegistries.BLOCKS.add(block);
        if (tab) {
            Item i = item.apply(block).setRegistryName(name);
            if (itemCallback != null) itemCallback.accept(i);
            BeastsRegistries.ITEMS.add(i);
        }
    }
}
