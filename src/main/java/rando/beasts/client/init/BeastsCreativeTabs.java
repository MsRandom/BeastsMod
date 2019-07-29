package rando.beasts.client.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.init.BeastsItems;
import rando.beasts.common.utils.BeastsReference;

public class BeastsCreativeTabs {
    public static final CreativeTabs MAIN = new CreativeTabs(BeastsReference.ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(BeastsItems.CARROT_COIN);
        }
    };
}
