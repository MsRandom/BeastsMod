package random.beasts.api.configuration;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import random.beasts.common.init.BeastsEntities;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BeastsConfig {
    private static final String BIOMES = "biomes";
    public static Configuration config = new Configuration(new File("config/beasts.cfg"));
    public static int reefWeight = 10;
    //this is atmoic so the abyss can still be final and not have to be moved to init
    public static AtomicInteger abyssWeight = new AtomicInteger(20);

    public static void init() {
        config.load();
        defineCategory(BIOMES);

        BeastsEntities.SPAWNS.forEach((type, spawns) -> {
            for (BeastsEntities.SpawnEntry spawn : spawns) {
                String category = type.getRegistryName();
                String unlocalized = "entity." + category + ".name";
                config.setCategoryLanguageKey(category, unlocalized);
                spawn.weight = config.getInt("Chance", category, spawn.weight, 1, 128, "Spawn chance");
                spawn.min = config.getInt("Min", category, spawn.min, 1, 32, "Minimum group spawns");
                spawn.max = config.getInt("Max", category, spawn.max, 1, 32, "Maximum group spawns");
                if (spawn.min > spawn.max)
                    throw new IllegalArgumentException("Entity " + I18n.format(unlocalized) + " has invalid group spawns, as the minimum is higher than the maximum");
                spawn.biomes = Stream.of(config.getStringList("Biomes", category, StreamSupport.stream(spawn.biomes.spliterator(), false).map(biome -> Objects.requireNonNull(biome.getRegistryName()).toString()).toArray(String[]::new), "Biomes to spawn in")).map(name -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(name))).collect(Collectors.toList());
            }
        });

        reefWeight = config.getInt("Reefweight", BIOMES, 10, 0, 128, "The spawn chance of the Dried Reef biome");
        abyssWeight.set(config.getInt("AbyssWeight", BIOMES, 4, 0, 128, "The spawn chance of The Abyss biome"));

        config.save();
    }

    private static void defineCategory(String name) {
        config.setCategoryLanguageKey(name, "config." + name + ".category");
    }
}
