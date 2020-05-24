package net.msrandom.beasts.common.item;

import net.msrandom.beasts.api.item.IHandleMeta;
import net.msrandom.beasts.common.block.CoralColor;

public interface ICoralMeta extends IHandleMeta {

    default int getDamage() {
        return CoralColor.values().length;
    }

    default String handleMeta(int meta) {
        return CoralColor.values()[meta].getName();
    }
}
