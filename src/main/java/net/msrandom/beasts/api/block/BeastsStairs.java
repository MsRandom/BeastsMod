package net.msrandom.beasts.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.msrandom.beasts.api.main.BeastsUtils;

public class BeastsStairs extends BlockStairs {
    public BeastsStairs(Block planks, String name) {
        super(planks.getDefaultState());
        BeastsUtils.addToRegistry(this, name + "_stairs");
    }
}
