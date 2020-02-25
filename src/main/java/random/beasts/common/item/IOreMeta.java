package random.beasts.common.item;

import random.beasts.api.item.IHandleMeta;
import random.beasts.common.block.OreType;

public interface IOreMeta extends IHandleMeta {

    default int getDamage() {
        return OreType.values().length;
    }

    default String handleMeta(int meta) {
        return OreType.values()[meta].getName();
    }
}
