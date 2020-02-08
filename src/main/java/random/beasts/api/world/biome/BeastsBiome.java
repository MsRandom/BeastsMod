package random.beasts.api.world.biome;

import net.minecraft.world.biome.Biome;
import random.beasts.api.main.BeastsRegistries;
import random.beasts.api.world.biome.underground.UndergroundBiome;

public class BeastsBiome extends Biome {
    public BeastsBiome(String name, BiomeProperties properties) {
        super(properties);
        setRegistryName(name);
        if(!(this instanceof UndergroundBiome))
            BeastsRegistries.BIOMES.add(this);
    }
}
