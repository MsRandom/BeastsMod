package random.beasts.api.main;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import javax.annotation.Nullable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class BeastsUtils {

    private static ItemGroup defaultTab;

    public static ItemGroup getRegistryTab() {
        return defaultTab;
    }

    public static void setRegistryTab(ItemGroup tab) {
        defaultTab = tab;
    }

    public static void addToRegistry(Item item, String name) {
        item.setRegistryName(name);
        BeastsRegistries.ITEMS.add(item);
    }

    public static void addToRegistry(Block block, String name) {
        addToRegistry(block, name, BlockItem::new);
    }

    public static void addToRegistry(Block block, String name, @Nullable BiFunction<Block, Item.Properties, Item> item) {
        addToRegistry(block, name, item, null);
    }

    public static void addToRegistry(Block block, String name, @Nullable BiFunction<Block, Item.Properties, Item> item, @Nullable Consumer<Item> itemCallback) {
        boolean tab = item != null;
        block.setRegistryName(name);
        BeastsRegistries.BLOCKS.add(block);
        if (tab) {
            Item i = item.apply(block, new Item.Properties().group(defaultTab)).setRegistryName(name);
            if (itemCallback != null) itemCallback.accept(i);
            BeastsRegistries.ITEMS.add(i);
        }
    }
}
