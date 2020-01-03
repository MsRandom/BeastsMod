package random.beasts.world.biome;

import net.minecraft.world.biome.Biome;
import random.beasts.init.BeastsRegistries;

public class BeastsBiome extends Biome {
    public BeastsBiome(String name, BiomeProperties properties) {
        super(properties);
        setRegistryName(name);
        BeastsRegistries.BIOMES.add(this);
    }
}
