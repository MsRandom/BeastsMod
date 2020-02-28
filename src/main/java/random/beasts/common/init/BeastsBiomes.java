package random.beasts.common.init;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import random.beasts.api.world.biome.underground.UndergroundBiome;
import random.beasts.common.world.biome.BiomeAbyss;
import random.beasts.common.world.biome.BiomeDriedReef;
import random.beasts.common.world.types.WorldTypeBeasts;

public class BeastsBiomes {

    public static final WorldType BEASTS_WORLD_TYPE = new WorldTypeBeasts();
    public static final Biome DRIED_REEF = new BiomeDriedReef();
    public static final UndergroundBiome THE_ABYSS = new BiomeAbyss(DRIED_REEF);

    public static void addTypes(Biome biome, BiomeManager.BiomeType mainType, int weight, boolean stronghold, VillageConfig village, BiomeDictionary.Type... types) {
        BiomeManager.addBiome(mainType, new BiomeManager.BiomeEntry(biome, weight));
        BiomeManager.addSpawnBiome(biome);
        if (stronghold) biome.addStructure(Feature.STRONGHOLD, IFeatureConfig.NO_FEATURE_CONFIG);
        if (village != null) biome.addStructure(Feature.VILLAGE, village);
        BiomeDictionary.addTypes(biome, types);
    }

    public static void addTypes(Biome biome, BiomeManager.BiomeType mainType, int weight, BiomeDictionary.Type... types) {
        addTypes(biome, mainType, weight, true, null, types);
    }
}
