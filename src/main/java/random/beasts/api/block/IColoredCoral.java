package random.beasts.api.block;

import net.minecraft.state.EnumProperty;
import random.beasts.common.block.CoralColor;

public interface IColoredCoral {
    EnumProperty<CoralColor> COLOR = EnumProperty.create("color", CoralColor.class);
}
