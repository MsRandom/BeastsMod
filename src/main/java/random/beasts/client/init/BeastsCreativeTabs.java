package random.beasts.client.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import random.beasts.common.BeastsMod;
import random.beasts.common.init.BeastsItems;

public class BeastsCreativeTabs {
    public static final ItemGroup MAIN = new ItemGroup(BeastsMod.MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BeastsItems.ICON);
        }
    };
}
