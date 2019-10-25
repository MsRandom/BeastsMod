package random.beasts.common.block;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.item.ItemBlock;
import random.beasts.common.main.BeastsUtils;

public class BlockJellyWoodFenceGate extends BlockFenceGate {
    public BlockJellyWoodFenceGate() {
        super(BlockPlanks.EnumType.ACACIA);
        BeastsUtils.addToRegistry(this, "jellywood_gate", true, ItemBlock::new);
    }
}
