package random.beasts.common.block;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import random.beasts.common.main.BeastsUtils;

public class BeastsFenceGate extends BlockFenceGate {
    public BeastsFenceGate(String name) {
        super(BlockPlanks.EnumType.ACACIA);
        setHardness(2.0F);
        setResistance(5.0F);
        BeastsUtils.addToRegistry(this, name + "_gate");
    }
}
