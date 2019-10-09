package rando.beasts.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import rando.beasts.common.block.CoralColor;

public class ItemCoralBlock extends ItemMultiTexture implements ICoralMeta {
    public ItemCoralBlock(Block block) {
        super(block, block, stack -> CoralColor.values()[stack.getItemDamage()].getName());
    }
}
