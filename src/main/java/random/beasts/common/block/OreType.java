package random.beasts.common.block;

import net.minecraft.util.IStringSerializable;

public enum OreType implements IStringSerializable {

    COAL("oreCoal"),
    DIAMOND("oreDiamond"),
    EMERALD("oreEmerald"),
    GOLD("oreGold"),
    IRON("oreIron"),
    LAPIS("oreLapis"),
    REDSTONE("oreRedstone");

    private final String oreDictionary;

    OreType(String oreDictionary) {
        this.oreDictionary = oreDictionary;
    }

    public static OreType getByOreDictionary(String oreDictionaryIn) {
        for (OreType value : values()) {
            if (value.getOreDictionary().equals(oreDictionaryIn)) {
                return value;
            }
        }
        return COAL;
    }

    public String getOreDictionary() {
        return oreDictionary;
    }

    public String getName() {
        return name().toLowerCase();
    }
}
