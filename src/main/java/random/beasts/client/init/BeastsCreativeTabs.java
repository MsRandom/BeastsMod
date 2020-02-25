package random.beasts.client.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import random.beasts.common.init.BeastsItems;

public class BeastsCreativeTabs {
    public static final CreativeTabs MAIN = new CreativeTabs(BeastsMod.MOD_ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(BeastsItems.ICON);
        }
    };
}
