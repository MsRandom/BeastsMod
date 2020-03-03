package random.beasts.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.block.BlockAbyssalOre;
import random.beasts.common.block.OreType;
import random.beasts.common.init.BeastsBlocks;

import javax.annotation.Nullable;

public class ItemOreBlock extends BlockItem {
    private final OreType ore;

    public ItemOreBlock(OreType ore) {
        super(BeastsBlocks.ABYSSAL_ORE, new Item.Properties().group(BeastsUtils.getRegistryTab()));
        BeastsUtils.addToRegistry(this, "abyssal_" + ore.getName() + "_ore");
        this.ore = ore;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return BeastsBlocks.ABYSSAL_ORE.getDefaultState().with(BlockAbyssalOre.ORE, ore);
    }
}
