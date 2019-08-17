package rando.beasts.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public class BlockCoral extends BeastsBlock {
    public BlockCoral(Color color) {
        super(Material.PLANTS, color.mapColor, "coral_block_" + color.getName());
        setHardness(0.6F);
        setSoundType(SoundType.PLANT);
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
