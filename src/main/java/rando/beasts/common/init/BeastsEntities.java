package rando.beasts.common.init;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import org.apache.commons.lang3.StringUtils;
import rando.beasts.common.entity.item.EntityBeastsPainting;
import rando.beasts.common.entity.monster.*;
import rando.beasts.common.entity.passive.EntityLandwhale;
import rando.beasts.common.entity.passive.EntityPufferfishDog;
import rando.beasts.common.entity.passive.EntityRabbitman;
import rando.beasts.common.entity.projectile.EntityCoconutBomb;
import rando.beasts.common.utils.BeastsReference;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class BeastsEntities {

	public static final List<EntityEntry> LIST = new ArrayList<>();

	private static int entityId = 0;
	private static final EntityEntry PUFFERFISH_DOG = createEntry(EntityPufferfishDog.class, 0xFBA70C, 0x429BBA, new SpawnEntry(EnumCreatureType.CREATURE, 12, 1, 1, BeastsBiomes.DRIED_REEF));
	private static final EntityEntry RABBITMAN = createEntry(EntityRabbitman.class, 0x4E362D, 0xE5E5E5, null);
	private static final EntityEntry COCONUT_CRAB = createEntry(EntityCoconutCrab.class, 0x3C1C11, 0xA16745, new SpawnEntry(EnumCreatureType.CREATURE, 12, 1, 1, BeastsBiomes.DRIED_REEF));
	private static final EntityEntry BRANCHIE = createEntry(EntityBranchie.class, 0xEDEC4C, 0xD6549B, new SpawnEntry(EnumCreatureType.CREATURE, 15, 1, 1, BeastsBiomes.DRIED_REEF));
	private static final EntityEntry VILE_EEL = createEntry(EntityVileEel.class, 0x313337, 0x987CAF, new SpawnEntry(EnumCreatureType.MONSTER, 12, 1, 1, BeastsBiomes.DRIED_REEF));
	private static final EntityEntry LANDWHALE = createEntry(EntityLandwhale.class, 0x587377, 0xE25AA5, new SpawnEntry(EnumCreatureType.CREATURE, 12, 1, 1, BeastsBiomes.DRIED_REEF));
	private static final EntityEntry COCONADE = createEntry(EntityCoconutBomb.class);
	private static final EntityEntry GIANT_GARDEN_EEL = createEntry(EntityGiantGardenEel.class, 0xCECEAF, 0x7A745E, new SpawnEntry(EnumCreatureType.CREATURE, 12, 1, 1, BeastsBiomes.DRIED_REEF));
	private static final EntityEntry SKEWER_SHRIMP = createEntry(EntitySkewerShrimp.class, 0xEA4E3C, 0xFFACA3, new SpawnEntry(EnumCreatureType.CREATURE, 12, 4, 8, BeastsBiomes.DRIED_REEF));
	private static final EntityEntry BEASTS_PAINTING = createEntry(EntityBeastsPainting.class);

	private static EntityEntry createEntry(Class<? extends Entity> cls, int prim, int sec, SpawnEntry spawn) {
		String entityName = StringUtils.join(cls.getSimpleName().replace("Entity", "").split("(?=[A-Z])"), "_").toLowerCase();
		ResourceLocation name = new ResourceLocation(BeastsReference.ID, entityName);
		EntityEntryBuilder builder = EntityEntryBuilder.create().entity(cls).id(name, BeastsEntities.entityId++).name(entityName).tracker(90, 1, true);
		if (spawn != null) builder.spawn(spawn.type, spawn.weight, spawn.min, spawn.max, spawn.biomes);
		EntityEntry entry = builder.build();
		entry.setEgg(new EntityList.EntityEggInfo(name, prim, sec));
		LIST.add(entry);
		return entry;
	}

	private static EntityEntry createEntry(Class<? extends Entity> cls) {
		String entityName = StringUtils.join(cls.getSimpleName().replace("Entity", "").split("(?=[A-Z])"), "_").toLowerCase();
		EntityEntry entry = EntityEntryBuilder.create().entity(cls).id(new ResourceLocation(BeastsReference.ID, entityName), BeastsEntities.entityId++).name(entityName).tracker(90, 1, true).build();
		LIST.add(entry);
		return entry;
	}

	private static class SpawnEntry {
		EnumCreatureType type;
		int weight;
		int min;
		int max;
		Iterable<Biome> biomes;

		private SpawnEntry(EnumCreatureType type, int weight, int min, int max, Iterable<Biome> biomes) {
			this.type = type;
			this.weight = weight;
			this.min = min;
			this.max = max;
			this.biomes = biomes;
		}

		private SpawnEntry(EnumCreatureType type, int weight, int min, int max, Biome... biomes) {
			this.type = type;
			this.weight = weight;
			this.min = min;
			this.max = max;
			this.biomes = Lists.newArrayList(biomes);
		}
	}
}
