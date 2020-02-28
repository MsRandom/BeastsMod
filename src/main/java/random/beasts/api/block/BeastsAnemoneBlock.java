package random.beasts.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;

public class BeastsAnemoneBlock extends BeastsBlock {
    public BeastsAnemoneBlock(String name) {
        super(Block.Properties.create(Material.CLAY).sound(SoundType.SLIME).hardnessAndResistance(0.5f, 0), "anemone_" + name, BlockItem::new);
    }
}
