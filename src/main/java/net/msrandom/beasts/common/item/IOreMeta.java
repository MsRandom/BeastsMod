package net.msrandom.beasts.common.item;

import net.msrandom.beasts.api.item.IHandleMeta;
import net.msrandom.beasts.common.block.OreType;

public interface IOreMeta extends IHandleMeta {

    default int getDamage() {
        return OreType.values().length;
    }

    default String handleMeta(int meta) {
        return OreType.values()[meta].getName();
    }
}
