package random.beasts.common.block;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import random.beasts.common.main.BeastsUtils;

public class BeastsTrapdoor extends BlockTrapDoor {
    public BeastsTrapdoor(String name) {
        super(Material.WOOD);
        setHardness(3.0F);
        setSoundType(SoundType.WOOD);
        disableStats();
        BeastsUtils.addToRegistry(this, name + "_trapdoor");
    }
}
