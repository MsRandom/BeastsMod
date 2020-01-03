package random.beasts.block;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import random.beasts.main.BeastsUtils;

public class BeastsTrapdoor extends BlockTrapDoor {
    public BeastsTrapdoor(String name) {
        super(Material.WOOD);
        setHardness(3.0F);
        setSoundType(SoundType.WOOD);
        disableStats();
        BeastsUtils.addToRegistry(this, name + "_trapdoor");
    }
}
