package random.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraftforge.common.ToolType;
import random.beasts.api.block.BeastsBlock;

public class BlockShell extends BeastsBlock {
    public BlockShell(String name) {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 10).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(1), name, BlockItem::new);
    }
}
