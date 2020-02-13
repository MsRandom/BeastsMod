package random.beasts.api.configuration;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Configuration;
import random.beasts.common.entity.monster.*;
import random.beasts.common.entity.passive.*;
import random.beasts.common.init.BeastsBiomes;
import random.beasts.common.init.BeastsEntities;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeastsSpawnConfig {

	public static Configuration config = new Configuration(new File("config/beasts.cfg"));
	public static final List<BeastsEntities.SpawnEntry> SPAWNS = new ArrayList<>();

	public static void setup(){
		config.load();

		add(EntityPufferfishDog.class, 30, 1, 1, EnumCreatureType.CREATURE, BeastsBiomes.DRIED_REEF);
		add(EntityVileEel.class, 40, 1, 1, EnumCreatureType.CREATURE, BeastsBiomes.DRIED_REEF);
		add(EntityLandwhale.class, 30, 1, 1, EnumCreatureType.CREATURE, BeastsBiomes.DRIED_REEF);
		add(EntityGiantGardenEel.class, 30, 4, 8, EnumCreatureType.CREATURE, BeastsBiomes.DRIED_REEF);
		add(EntitySkewerShrimp.class, 30, 4, 8, EnumCreatureType.CREATURE, BeastsBiomes.DRIED_REEF);
		add(EntityHermitTurtle.class, 30, 1, 3, EnumCreatureType.CREATURE, BeastsBiomes.DRIED_REEF);
		add(EntityLegfish.class, 30, 2, 4, EnumCreatureType.CREATURE, BeastsBiomes.DRIED_REEF);
		add(EntityScallop.class, 30, 2, 4, EnumCreatureType.MONSTER, BeastsBiomes.DRIED_REEF);
		add(EntityTrimola.class, 20, 2, 4, EnumCreatureType.CREATURE, BeastsBiomes.DRIED_REEF);
		add(EntitySlimeSlug.class, 20, 2, 4, EnumCreatureType.CREATURE, BeastsBiomes.DRIED_REEF);
		add(EntityButterflyFish.class, 20, 2, 4, EnumCreatureType.CREATURE, BeastsBiomes.DRIED_REEF);

		config.save();
	}

	public static void add(Class<? extends EntityLiving> entity, int chance, int min, int max, EnumCreatureType type, Biome... biomes){
		SPAWNS.add(new BeastsEntities.SpawnEntry(
				entity,
				type,
				config.get(entity.getSimpleName(), "Chance", chance).getInt(),
				config.get(entity.getSimpleName(), "Min", min).getInt(),
				config.get(entity.getSimpleName(), "Max", max).getInt(),
				config.get(entity.getSimpleName(), "Biomes", getBiomeNames(biomes)).getStringList()));
	}

	public static String[] getBiomeNames(Biome... biomes){
		List<String> a = new ArrayList<>();
		Arrays.asList(biomes).forEach(b -> a.add(b.getRegistryName().toString()));
		return a.toArray(new String[0]);
	}
}
