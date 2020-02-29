package random.beasts.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import random.beasts.api.block.BeastsBlock;

public class BlockAbyssalStone extends BeastsBlock {
    public BlockAbyssalStone() {
        super(Material.ROCK, "abyssal_stone");
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setHarvestLevel("pickaxe", 1);
    }
}
