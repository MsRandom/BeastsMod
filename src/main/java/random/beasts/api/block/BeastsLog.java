package random.beasts.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import random.beasts.api.main.BeastsUtils;

public class BeastsLog extends LogBlock {

    public BeastsLog(String name) {
        super(MaterialColor.WOOD, Block.Properties.create(Material.WOOD));
        BeastsUtils.addToRegistry(this, name);
    }
}
