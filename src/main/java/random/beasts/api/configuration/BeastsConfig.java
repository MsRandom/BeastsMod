package random.beasts.api.configuration;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import random.beasts.common.init.BeastsEntities;

import java.io.File;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BeastsConfig {
    public static Configuration config = new Configuration(new File("config/beasts.cfg"));
    public static int reefWeight = 10;

    public static void init() {
        config.load();

        BeastsEntities.SPAWNS.forEach((type, spawns) -> {
            for (BeastsEntities.SpawnEntry spawn : spawns) {
                EntityEntry entry = type.getFinal();
                String category = entry.getName();
                spawn.weight = config.getInt("Chance", category, spawn.weight, 1, 128, "Spawn chance");
                spawn.min = config.getInt("Min", category, spawn.min, 1, 32, "Minimum group spawns");
                spawn.max = config.getInt("Max", category, spawn.max, 1, 32, "Maximum group spawns");
                if (spawn.min > spawn.max)
                    throw new IllegalArgumentException("Entity " + category + "has invalid group spawns, as the minimum is higher than the maximum");
                spawn.biomes = Stream.of(config.getStringList("Biomes", category, StreamSupport.stream(spawn.biomes.spliterator(), false).map(biome -> Objects.requireNonNull(biome.getRegistryName()).toString()).toArray(String[]::new), "Biomes to spawn in")).map(name -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(name))).collect(Collectors.toList());
            }
        });

        reefWeight = config.getInt("Reefweight", "Biomes", 10, 0, 128, "The spawn chance of the Dried Reef biome");

        config.save();
    }
}
