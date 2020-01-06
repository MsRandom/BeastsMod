package random.beasts.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import random.beasts.api.main.BeastsUtils;

public class BeastsStairs extends BlockStairs {
    public BeastsStairs(Block planks, String name) {
        super(planks.getDefaultState());
        BeastsUtils.addToRegistry(this, name + "_stairs");
    }
}
