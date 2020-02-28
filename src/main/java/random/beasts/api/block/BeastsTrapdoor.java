package random.beasts.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import random.beasts.api.main.BeastsUtils;

public class BeastsTrapdoor extends TrapDoorBlock {
    public BeastsTrapdoor(String name) {
        super(Block.Properties.create(Material.WOOD).hardnessAndResistance(3, 0).sound(SoundType.WOOD));
        BeastsUtils.addToRegistry(this, name + "_trapdoor");
    }
}
