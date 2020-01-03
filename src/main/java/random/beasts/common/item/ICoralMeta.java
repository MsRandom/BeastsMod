package random.beasts.common.item;

import random.beasts.common.block.CoralColor;
import random.beasts.item.IHandleMeta;

public interface ICoralMeta extends IHandleMeta {

    default int getDamage() {
        return CoralColor.values().length;
    }

    default String handleMeta(int meta) {
        return CoralColor.values()[meta].getName();
    }
}
