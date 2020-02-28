package random.beasts.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import random.beasts.api.main.BeastsUtils;

public class BeastsStairs extends StairsBlock {
    public BeastsStairs(Block planks, String name) {
        super(planks.getDefaultState(), Block.Properties.create(Material.WOOD));
        BeastsUtils.addToRegistry(this, name + "_stairs");
    }
}
