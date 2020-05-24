package net.msrandom.beasts.api.world.biome;

import net.minecraft.world.biome.Biome;
import net.msrandom.beasts.api.main.BeastsRegistries;

public class BeastsBiome extends Biome {
    public BeastsBiome(String name, BiomeProperties properties) {
        super(properties);
        setRegistryName(name);
        BeastsRegistries.BIOMES.add(this);
    }
}
