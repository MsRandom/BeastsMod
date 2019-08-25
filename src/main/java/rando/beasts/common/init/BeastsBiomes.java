package rando.beasts.common.init;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import rando.beasts.common.world.biome.BiomeDriedReef;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class BeastsBiomes {
    public static final List<Biome> LIST = new ArrayList<>();

    public static final Biome DRIED_REEF = new BiomeDriedReef();

    public static void addTypes(Biome biome, BiomeManager.BiomeType mainType, int weight, boolean stronghold, boolean village, BiomeDictionary.Type... types) {
        BiomeManager.addBiome(mainType, new BiomeManager.BiomeEntry(biome, weight));
        BiomeManager.addSpawnBiome(biome);
        if(stronghold) BiomeManager.addStrongholdBiome(biome);
        if(village) BiomeManager.addVillageBiome(biome, false);
        BiomeDictionary.addTypes(biome, types);
    }

    public static void addTypes(Biome biome, BiomeManager.BiomeType mainType, int weight, BiomeDictionary.Type... types) {
        addTypes(biome, mainType, weight, true, false, types);
    }
}
