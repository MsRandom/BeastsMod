package random.beasts.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.material.Material;
import random.beasts.api.main.BeastsUtils;

public class BeastsFence extends FenceBlock {
    public BeastsFence(String name) {
        super(Block.Properties.create(Material.WOOD).hardnessAndResistance(2, 5));
        BeastsUtils.addToRegistry(this, name + "_fence");
    }
}
