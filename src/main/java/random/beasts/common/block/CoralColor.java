package random.beasts.common.block;

import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import java.util.Random;

public enum CoralColor implements IStringSerializable {
    BLUE(MaterialColor.BLUE),
    PINK(MaterialColor.PINK),
    PURPLE(MaterialColor.PURPLE),
    RED(MaterialColor.RED),
    YELLOW(MaterialColor.YELLOW);

    public MaterialColor mapColor;

    CoralColor(MaterialColor color) {
        this.mapColor = color;
    }

    public static CoralColor getRandom(Random rand) {
        return values()[rand.nextInt(values().length)];
    }

    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
