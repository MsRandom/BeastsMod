package random.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.item.ItemBlock;
import random.beasts.common.main.BeastsUtils;

public class BeastsStairs extends BlockStairs {
    public BeastsStairs(Block planks, String name) {
        super(planks.getDefaultState());
        BeastsUtils.addToRegistry(this, name + "_stairs");
    }
}
