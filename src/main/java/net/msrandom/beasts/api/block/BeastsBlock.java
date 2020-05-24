package net.msrandom.beasts.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.msrandom.beasts.api.main.BeastsUtils;

import javax.annotation.Nullable;
import java.util.function.Function;

public class BeastsBlock extends Block {

    //aaaaa too many constructors

    public BeastsBlock(Material materialIn, String name) {
        this(materialIn, name, ItemBlock::new);
    }

    public BeastsBlock(Material materialIn, MapColor color, String name) {
        this(materialIn, color, name, ItemBlock::new);
    }

    public BeastsBlock(Material materialIn, String name, @Nullable Function<Block, Item> item) {
        this(materialIn, materialIn.getMaterialMapColor(), name, item);
    }

    public BeastsBlock(Material materialIn, MapColor color, String name, @Nullable Function<Block, Item> item) {
        super(materialIn, color);
        register(name, item);
    }

    protected void register(String name, @Nullable Function<Block, Item> item) {
        BeastsUtils.addToRegistry(this, name, item);
    }
}
