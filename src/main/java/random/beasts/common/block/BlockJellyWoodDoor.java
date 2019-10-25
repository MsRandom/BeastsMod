package random.beasts.common.block;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import random.beasts.common.item.ItemJellyWoodDoor;
import random.beasts.common.main.BeastsUtils;

public class BlockJellyWoodDoor extends BlockDoor {
    public BlockJellyWoodDoor() {
        super(Material.WOOD);
        setHardness(3.0F);
        setSoundType(SoundType.WOOD);
        disableStats();
        BeastsUtils.addToRegistry(this, "jellywood_door", true, ItemJellyWoodDoor::new);
    }
}
