package net.msrandom.beasts.common.block;

import net.minecraft.util.IStringSerializable;

import java.util.HashMap;
import java.util.Map;

public enum OreType implements IStringSerializable {
    COAL,
    DIAMOND,
    EMERALD,
    GOLD,
    IRON,
    LAPIS,
    REDSTONE;

    public static final Map<String, OreType> VALUES = new HashMap<>();

    public String getName() {
        return name().toLowerCase();
    }

    static {
        for (OreType value : values()) {
            VALUES.put("ore" + value.name().substring(0, 1) + value.name().substring(1).toLowerCase(), value);
        }
    }
}
