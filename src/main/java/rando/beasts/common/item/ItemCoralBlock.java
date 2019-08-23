package rando.beasts.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import rando.beasts.common.block.BlockCoral;

public class ItemCoralBlock extends ItemMultiTexture implements IHandleMeta {
    public ItemCoralBlock(Block block) {
        super(block, block, stack -> BlockCoral.Color.values()[stack.getItemDamage()].getName());
    }

    @Override
    public int getDamage() {
        return BlockCoral.Color.values().length;
    }

    @Override
    public String handleMeta(int meta) {
        return BlockCoral.Color.values()[meta].getName();
    }
}
