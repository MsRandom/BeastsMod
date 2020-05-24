package net.msrandom.beasts.api.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BeastsAnemoneBlock extends BeastsBlock {
    public BeastsAnemoneBlock(String name) {
        super(Material.CLAY, "anemone_" + name);
        setSoundType(SoundType.SLIME);
        setHardness(0.5f);
    }
}
