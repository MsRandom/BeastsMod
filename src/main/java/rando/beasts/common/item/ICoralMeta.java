package rando.beasts.common.item;

import rando.beasts.common.block.CoralColor;

public interface ICoralMeta extends IHandleMeta {

    default int getDamage() {
        return CoralColor.values().length;
    }

    default String handleMeta(int meta) {
        return CoralColor.values()[meta].getName();
    }
}
