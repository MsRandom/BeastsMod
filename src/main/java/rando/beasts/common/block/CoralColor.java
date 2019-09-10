package rando.beasts.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.util.IStringSerializable;
import rando.beasts.common.entity.monster.EntityBranchieBase;

import javax.annotation.Nonnull;
import java.util.Random;

public enum CoralColor implements IStringSerializable {
    BLUE(MapColor.BLUE),
    PINK(MapColor.PINK),
    PURPLE(MapColor.PURPLE),
    RED(MapColor.RED),
    YELLOW(MapColor.YELLOW);

    public MapColor mapColor;

    CoralColor(MapColor color) {
        this.mapColor = color;
    }

    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase();
    }

    public static CoralColor getRandom(Random rand) {
        return values()[rand.nextInt(values().length)];
    }
}
