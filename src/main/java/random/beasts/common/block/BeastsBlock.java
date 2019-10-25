package random.beasts.common.block;

import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import random.beasts.common.main.BeastsUtils;

public class BeastsBlock extends Block {

    //aaaaa too many constructors

    public BeastsBlock(Material materialIn, String name) {
        this(materialIn, name, true);
    }

    public BeastsBlock(Material materialIn, MapColor color, String name, boolean tab) {
        this(materialIn, color, name, tab, ItemBlock::new);
    }

    public BeastsBlock(Material materialIn, String name, boolean tab) {
        this(materialIn, name, tab, ItemBlock::new);
    }

    public BeastsBlock(Material materialIn, String name, Function<Block, Item> item) {
        this(materialIn, name, true, item);
    }

    public BeastsBlock(Material materialIn, String name, boolean tab, Function<Block, Item> item) {
        this(materialIn, materialIn.getMaterialMapColor(), name, tab, item);
    }

    public BeastsBlock(Material materialIn, MapColor color, String name, boolean tab, Function<Block, Item> item) {
        super(materialIn, color);
        BeastsUtils.addToRegistry(this, name, tab, item);
    }
}
