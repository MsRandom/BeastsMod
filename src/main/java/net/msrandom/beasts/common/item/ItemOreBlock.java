package net.msrandom.beasts.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.msrandom.beasts.common.block.OreType;

public class ItemOreBlock extends ItemMultiTexture implements IOreMeta {
    public ItemOreBlock(Block block) {
        super(block, block, stack -> OreType.values()[stack.getItemDamage()].getName());
    }
}
