package random.beasts.block;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import random.beasts.main.BeastsUtils;

public class BeastsFence extends BlockFence {
    public BeastsFence(String name) {
        super(Material.WOOD, Material.WOOD.getMaterialMapColor());
        setHardness(2.0F);
        setResistance(5.0F);
        BeastsUtils.addToRegistry(this, name + "_fence");
    }
}
