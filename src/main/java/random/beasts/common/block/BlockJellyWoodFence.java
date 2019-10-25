package random.beasts.common.block;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import random.beasts.common.main.BeastsUtils;

public class BlockJellyWoodFence extends BlockFence {
    public BlockJellyWoodFence() {
        super(Material.WOOD, Material.WOOD.getMaterialMapColor());
        BeastsUtils.addToRegistry(this, "jellywood_fence", true, ItemBlock::new);
    }
}
