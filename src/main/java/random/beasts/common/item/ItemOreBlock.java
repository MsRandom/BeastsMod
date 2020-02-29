package random.beasts.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.block.OreType;
import random.beasts.common.init.BeastsBlocks;

import javax.annotation.Nullable;

public class ItemOreBlock extends BlockItem {
    public ItemOreBlock(OreType ore) {
        super(BeastsBlocks.ABYSSAL_ORE, new Item.Properties().group(BeastsUtils.getRegistryTab()));
        BeastsUtils.addToRegistry(this, "abyssal_ore_" + ore.getName());
    }

    @Nullable
    @Override
    protected BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context);
    }
}
