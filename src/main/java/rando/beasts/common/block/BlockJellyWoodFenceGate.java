package rando.beasts.common.block;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import rando.beasts.common.utils.BeastsUtil;

public class BlockJellyWoodFenceGate extends BlockFenceGate {
    public BlockJellyWoodFenceGate() {
        super(BlockPlanks.EnumType.ACACIA);
        BeastsUtil.addToRegistry(this, "jellywood_gate", true, ItemBlock::new);
    }
}
