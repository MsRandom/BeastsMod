package random.beasts.common.block;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

public enum OreType implements IStringSerializable {

    COAL("ores/coal"),
    DIAMOND("ores/diamond"),
    EMERALD("ores/emerald"),
    GOLD("ores/gold"),
    IRON("ores/iron"),
    LAPIS("ores/lapis"),
    REDSTONE("ores/redstone");

    private final ResourceLocation oreTag;

    OreType(String tag) {
        this.oreTag = new ResourceLocation(tag);
    }

    public static OreType getByTag(ResourceLocation tag) {
        for (OreType value : values()) {
            if (value.getOreDictionary().equals(tag)) {
                return value;
            }
        }
        return COAL;
    }

    public ResourceLocation getOreDictionary() {
        return oreTag;
    }

    public String getName() {
        return name().toLowerCase();
    }
}
