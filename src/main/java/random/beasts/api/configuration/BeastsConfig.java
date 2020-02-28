package random.beasts.api.configuration;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import random.beasts.common.init.BeastsEntities;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BeastsConfig {
    private static final String BIOMES = "biomes";
    //public static Configuration config = new Configuration(new File("config/beasts.cfg"));
    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final BeastsConfig CONFIG;
    public static int reefWeight = 10;
    //this is atmoic so the abyss can still be final and not have to be moved to init
    public static AtomicInteger abyssWeight = new AtomicInteger(20);

    static {
        final Pair<BeastsConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(BeastsConfig::new);
        CONFIG_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    public BeastsConfig(ForgeConfigSpec.Builder builder) {
        BeastsEntities.SPAWNS.forEach((type, spawns) -> {
            String category = type.getRegistryName().getPath();
            builder.push(category);
            for (int i = 0; i < spawns.length; i++) {
                BeastsEntities.SpawnEntry spawn = spawns[i];
                String s = i == 0 ? "" : String.valueOf(i + 1);
                spawn.weight = builder.comment("Spawn chance").translation("config." + category + ".chance" + s).defineInRange(category + "Chance" + s, spawn.weight, 1, 128).get();
                spawn.min = builder.comment("Minimum group spawns").translation("config." + category + ".min" + s).defineInRange(category + "Min" + s, spawn.min, 1, 32).get();
                spawn.max = builder.comment("Maximum group spawns").translation("config." + category + ".max" + s).defineInRange(category + "Max" + s, spawn.max, 1, 32).get();
                if (spawn.min > spawn.max)
                    throw new IllegalArgumentException("Entity " + I18n.format("entity." + category + ".name") + " has invalid group spawns, as the minimum is higher than the maximum");
                spawn.biomes = Stream.of(builder.comment("Biomes to spawn in").translation("config." + category + ".biomes" + s).define(category + "Biomes" + s, StreamSupport.stream(spawn.biomes.spliterator(), false).map(biome -> Objects.requireNonNull(biome.getRegistryName()).toString()).toArray(String[]::new)).get()).map(name -> ForgeRegistries.BIOMES.get(new ResourceLocation(name))).collect(Collectors.toList());
            }
            builder.pop();
        });

        builder.push(BIOMES);
        reefWeight = builder.comment("The spawn chance of the Dried Reef biome").translation("config." + BIOMES + ".chance").defineInRange("Reefweight", 10, 0, 128).get();
        abyssWeight.set(builder.comment("The spawn chance of The Abyss biome").translation("config." + BIOMES + ".chance").defineInRange("AbyssWeight", 10, 2, 128).get());
        builder.pop();
    }
}
