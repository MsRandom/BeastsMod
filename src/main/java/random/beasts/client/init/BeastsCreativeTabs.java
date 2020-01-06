package random.beasts.client.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import random.beasts.api.main.BeastsReference;
import random.beasts.common.init.BeastsItems;

public class BeastsCreativeTabs {
    public static final CreativeTabs MAIN = new CreativeTabs(BeastsReference.ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(BeastsItems.ICON);
        }
    };
}
