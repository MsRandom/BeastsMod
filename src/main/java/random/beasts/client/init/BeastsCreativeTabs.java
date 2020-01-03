package random.beasts.client.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import random.beasts.common.init.BeastsItems;
import random.beasts.main.BeastsReference;

public class BeastsCreativeTabs {
    public static final CreativeTabs MAIN = new CreativeTabs(BeastsReference.ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(BeastsItems.ICON);
        }
    };
}
