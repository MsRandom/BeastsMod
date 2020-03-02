package random.beasts.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import random.beasts.api.block.IColoredCoral;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.block.CoralColor;

import javax.annotation.Nullable;
import java.util.Objects;

public class ItemCoralBlock extends BlockItem {
    public final CoralColor color;

    public ItemCoralBlock(Block block, CoralColor color) {
        super(block, new Item.Properties().group(BeastsUtils.getRegistryTab()));
        BeastsUtils.addToRegistry(this, Objects.requireNonNull(block.getRegistryName()).getPath() + "_" + color.getName());
        this.color = color;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getBlock().getDefaultState().with(IColoredCoral.COLOR, color);
    }
}
