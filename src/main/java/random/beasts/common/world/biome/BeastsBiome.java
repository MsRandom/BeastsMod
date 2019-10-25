package random.beasts.common.world.biome;

import net.minecraft.world.biome.Biome;
import random.beasts.common.init.BeastsBiomes;

class BeastsBiome extends Biome {
    BeastsBiome(String name, BiomeProperties properties) {
        super(properties);
        setRegistryName(name);
        BeastsBiomes.LIST.add(this);
    }
}
