package random.beasts.common.block;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import random.beasts.common.item.BeastsDoorItem;
import random.beasts.common.main.BeastsUtils;

public class BeastsDoor extends BlockDoor {
    public BeastsDoor(String name) {
        super(Material.WOOD);
        setHardness(3.0F);
        setSoundType(SoundType.WOOD);
        disableStats();
        BeastsUtils.addToRegistry(this, name + "_door", BeastsDoorItem::new);
    }
}
