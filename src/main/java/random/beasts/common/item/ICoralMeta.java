package random.beasts.common.item;

import random.beasts.api.item.IHandleMeta;
import random.beasts.common.block.CoralColor;

public interface ICoralMeta extends IHandleMeta {

    default int getDamage() {
        return CoralColor.values().length;
    }

    default String handleMeta(int meta) {
        return CoralColor.values()[meta].getName();
    }
}
