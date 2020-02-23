package random.beasts.api.world.biome;

import net.minecraft.world.biome.Biome;
import random.beasts.api.main.BeastsRegistries;

public class BeastsBiome extends Biome {
    public BeastsBiome(String name, BiomeProperties properties) {
        super(properties);
        setRegistryName(name);
        register();
    }

    protected void register() {
        BeastsRegistries.BIOMES.add(this);
    }
}
