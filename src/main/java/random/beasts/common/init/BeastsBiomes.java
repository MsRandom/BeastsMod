package random.beasts.common.init;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import random.beasts.api.world.biome.underground.UndergroundBiome;
import random.beasts.common.world.biome.BiomeDriedReef;
import random.beasts.common.world.types.WorldTypeBeasts;

public class BeastsBiomes {

    public static final WorldType BEASTS_WORLD_TYPE = new WorldTypeBeasts();
    public static final Biome DRIED_REEF = new BiomeDriedReef();
    public static final UndergroundBiome THE_ABYSS = new UndergroundBiome("The Abyss", 20, DRIED_REEF);

    public static void addTypes(Biome biome, BiomeManager.BiomeType mainType, int weight, boolean stronghold, boolean village, BiomeDictionary.Type... types) {
        BiomeManager.addBiome(mainType, new BiomeManager.BiomeEntry(biome, weight));
        BiomeManager.addSpawnBiome(biome);
        if (stronghold) BiomeManager.addStrongholdBiome(biome);
        if (village) BiomeManager.addVillageBiome(biome, false);
        BiomeDictionary.addTypes(biome, types);
    }

    public static void addTypes(Biome biome, BiomeManager.BiomeType mainType, int weight, BiomeDictionary.Type... types) {
        addTypes(biome, mainType, weight, true, false, types);
    }
}
