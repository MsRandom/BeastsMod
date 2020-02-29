package random.beasts.api.world.biome;

import net.minecraft.world.biome.Biome;
import random.beasts.api.main.BeastsRegistries;

public class BeastsBiome extends Biome {
    public BeastsBiome(String name, Biome.Builder properties) {
        super(properties);
        setRegistryName(name);
        BeastsRegistries.BIOMES.add(this);
    }
}
