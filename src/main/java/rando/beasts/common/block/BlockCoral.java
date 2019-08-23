package rando.beasts.common.block;

import static rando.beasts.common.init.BeastsItems.CORAL_ESSENCE_BLUE;
import static rando.beasts.common.init.BeastsItems.CORAL_ESSENCE_PINK;
import static rando.beasts.common.init.BeastsItems.CORAL_ESSENCE_PURPLE;
import static rando.beasts.common.init.BeastsItems.CORAL_ESSENCE_RED;
import static rando.beasts.common.init.BeastsItems.CORAL_ESSENCE_YELLOW;

import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;

public class BlockCoral extends BeastsBlock {
	private Item itemToDrop;
	
    public BlockCoral(Color color) {
        super(Material.PLANTS, color.mapColor, "coral_block_" + color.getName());
        setHardness(0.6F);
        setSoundType(SoundType.PLANT);
        this.itemToDrop = getCoralEssence(color);
    }
    
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return MathHelper.clamp(this.quantityDropped(random) + random.nextInt(fortune + 1), 1, 4);
    }

    public int quantityDropped(Random random)
    {
        return 2 + random.nextInt(3);
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.itemToDrop;
    }
    
    private Item getCoralEssence(Color color)
    {
    	switch(color) {
    	case BLUE:
    		return CORAL_ESSENCE_BLUE;
    	case PINK:
    		return CORAL_ESSENCE_PINK;
    	case PURPLE:
    		return CORAL_ESSENCE_PURPLE;
    	case RED:
    		return CORAL_ESSENCE_RED;
    	case YELLOW:
    		return CORAL_ESSENCE_YELLOW;
    	}
        return Item.getItemFromBlock(this);
    }
    

    public enum Color implements IStringSerializable {
        BLUE("blue", MapColor.BLUE),
        PINK("pink", MapColor.PINK),
        PURPLE("purple", MapColor.PURPLE),
        RED("red", MapColor.RED),
        YELLOW("yellow", MapColor.YELLOW);

        public MapColor mapColor;
        private String name;

        Color(String name, MapColor color) {
            this.name = name;
            this.mapColor = color;
        }

        @Nonnull
        @Override
        public String getName() {
        	
            return name;
        }
    }
}
