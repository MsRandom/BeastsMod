package rando.beasts.common.world.biome;

import net.minecraft.world.biome.Biome;
import rando.beasts.common.init.BeastsBiomes;

class BeastsBiome extends Biome {
    BeastsBiome(String name, BiomeProperties properties) {
        super(properties);
        setRegistryName(name);
        BeastsBiomes.LIST.add(this);
    }
}
