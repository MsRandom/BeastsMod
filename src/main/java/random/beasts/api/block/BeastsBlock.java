package random.beasts.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import random.beasts.api.main.BeastsUtils;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class BeastsBlock extends Block {

    //aaaaa too many constructors

    public BeastsBlock(Material materialIn, String name) {
        this(materialIn, name, BlockItem::new);
    }

    public BeastsBlock(Material materialIn, MaterialColor color, String name) {
        this(materialIn, color, name, BlockItem::new);
    }

    public BeastsBlock(Material materialIn, String name, @Nullable BiFunction<Block, Item.Properties, Item> item) {
        this(materialIn, materialIn.getColor(), name, item);
    }

    public BeastsBlock(Material materialIn, MaterialColor color, String name, @Nullable BiFunction<Block, Item.Properties, Item> item) {
        this(Block.Properties.create(materialIn, color), name, item);
    }

    public BeastsBlock(Block.Properties properties, String name, @Nullable BiFunction<Block, Item.Properties, Item> item) {
        super(properties);
        register(name, item);
    }

    protected void register(String name, @Nullable BiFunction<Block, Item.Properties, Item> item) {
        BeastsUtils.addToRegistry(this, name, item);
    }
}
