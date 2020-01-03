package random.beasts.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import random.beasts.main.BeastsUtils;

public class BeastsStairs extends BlockStairs {
    public BeastsStairs(Block planks, String name) {
        super(planks.getDefaultState());
        BeastsUtils.addToRegistry(this, name + "_stairs");
    }
}
