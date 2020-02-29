package random.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraftforge.common.ToolType;
import random.beasts.api.block.BeastsBlock;

public class BlockAbyssalStone extends BeastsBlock {
    public BlockAbyssalStone() {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 10).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(1), "abyssal_stone", BlockItem::new);
    }
}
