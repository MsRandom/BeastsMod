package rando.beasts.common.block;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import rando.beasts.common.utils.BeastsUtil;

public class BlockJellyWoodFence extends BlockFence {
    public BlockJellyWoodFence() {
        super(Material.WOOD, Material.WOOD.getMaterialMapColor());
        BeastsUtil.addToRegistry(this, "jellywood_fence", true, ItemBlock::new);
    }
}
