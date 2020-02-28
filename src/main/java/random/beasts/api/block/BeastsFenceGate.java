package random.beasts.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.material.Material;
import random.beasts.api.main.BeastsUtils;

public class BeastsFenceGate extends FenceGateBlock {
    public BeastsFenceGate(String name) {
        super(Block.Properties.create(Material.WOOD).hardnessAndResistance(2, 5));
        BeastsUtils.addToRegistry(this, name + "_gate");
    }
}
