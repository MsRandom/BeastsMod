package net.msrandom.beasts.client.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.common.init.BeastsItems;

public class BeastsCreativeTabs {
    public static final CreativeTabs MAIN = new CreativeTabs(BeastsReference.ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(BeastsItems.ICON);
        }
    };
}
