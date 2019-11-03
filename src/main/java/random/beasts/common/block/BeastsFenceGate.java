package random.beasts.common.block;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.item.ItemBlock;
import random.beasts.common.main.BeastsUtils;

public class BeastsFenceGate extends BlockFenceGate {
    public BeastsFenceGate(String name) {
        super(BlockPlanks.EnumType.ACACIA);
        BeastsUtils.addToRegistry(this, name + "_gate");
    }
}
