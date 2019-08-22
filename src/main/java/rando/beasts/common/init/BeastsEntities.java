package rando.beasts.common.init;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;
import rando.beasts.common.entity.*;
import rando.beasts.common.entity.projectile.EntityCoconutBomb;
import rando.beasts.common.main.BeastsMod;
import rando.beasts.common.utils.BeastsReference;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unused")
public class BeastsEntities {

	private static int entityId = 0;
	public static final List<EntityEntry> LIST = new ArrayList<>();

	private static final EntityType PUFFERFISH_DOG = new EntityType(EntityPufferfishDog.class, 0xFBA70C, 0x429BBA, new EntityType.SpawnEntry(EnumCreatureType.CREATURE, 12, 1, 1, BiomeDictionary.getBiomes(BiomeDictionary.Type.BEACH)));
	private static final EntityType RABBITMAN = new EntityType(EntityRabbitman.class, 0x4E362D, 0xE5E5E5, null);
	private static final EntityType COCONUT_CRAB = new EntityType(EntityCoconutCrab.class, 0x3C1C11, 0xA16745, new EntityType.SpawnEntry(EnumCreatureType.CREATURE, 12, 1, 1, BiomeDictionary.getBiomes(BiomeDictionary.Type.BEACH)));
	private static final EntityType VILE_EEL = new EntityType(EntityVileEel.class, 0x313337, 0x987CAF, new EntityType.SpawnEntry(EnumCreatureType.MONSTER, 12, 1, 1, BiomeDictionary.getBiomes(BiomeDictionary.Type.BEACH)));
	private static final EntityType LANDWHALE = new EntityType(EntityLandwhale.class, 0x587377, 0xE25AA5, new EntityType.SpawnEntry(EnumCreatureType.CREATURE, 12, 1, 1, BiomeDictionary.getBiomes(BiomeDictionary.Type.BEACH)));
	private static final EntityType COCONADE = new EntityType(EntityCoconutBomb.class);
	private static final EntityType GIANT_GARDEN_EEL = new EntityType(EntityGiantGardenEel.class, 0x605D4E, 0xFFFE69, new EntityType.SpawnEntry(EnumCreatureType.CREATURE, 12, 1, 1, BiomeDictionary.getBiomes(BiomeDictionary.Type.BEACH)));
	private static final EntityType SKEWER_SHRIMP = new EntityType(EntitySkewerShrimp.class, 0xEA4E3C, 0xFFACA3, new EntityType.SpawnEntry(EnumCreatureType.CREATURE, 12, 4, 8, BiomeDictionary.getBiomes(BiomeDictionary.Type.BEACH)));

	public static class EntityType {
		EntityType(Class<? extends Entity> cls, int prim, int sec, SpawnEntry spawn) {
			String entityName = StringUtils.join(cls.getSimpleName().replace("Entity", "").split("(?=[A-Z])"), "_").toLowerCase();
			EntityEntryBuilder builder = EntityEntryBuilder.create().entity(cls).id(new ResourceLocation(BeastsReference.ID, entityName), BeastsEntities.entityId++).name(entityName).tracker(90, 1, true);
			if(spawn != null) builder.spawn(spawn.type, spawn.weight, spawn.min, spawn.max, spawn.biomes);
			EntityEntry entry = builder.build();
			entry.setEgg(new EntityList.EntityEggInfo(new ResourceLocation(BeastsReference.ID, entityName), prim, sec));
			LIST.add(entry);
		
		}

		EntityType(Class<? extends Entity> cls) {
			String entityName = StringUtils.join(cls.getSimpleName().replace("Entity", "").split("(?=[A-Z])"), "_").toLowerCase();
			EntityEntryBuilder builder = EntityEntryBuilder.create().entity(cls).id(new ResourceLocation(BeastsReference.ID, entityName), BeastsEntities.entityId++).name(entityName).tracker(90, 1, true);
			EntityEntry entry = builder.build();
			LIST.add(entry);
		}

		static class SpawnEntry {
			EnumCreatureType type;
			int weight;
			int min;
			int max;
			Iterable<Biome> biomes;

			SpawnEntry(EnumCreatureType type, int weight, int min, int max, Iterable<Biome> biomes) {
				this.type = type;
				this.weight = weight;
				this.min = min;
				this.max = max;
				this.biomes = biomes;
			}
		}
	}
}
