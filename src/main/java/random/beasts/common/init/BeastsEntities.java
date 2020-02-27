package random.beasts.common.init;

import com.google.common.base.CaseFormat;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Tuple;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.BlockEvent;
import random.beasts.api.entity.BeastsBranchie;
import random.beasts.api.main.BeastsRegistries;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.entity.item.EntityBeastsPainting;
import random.beasts.common.entity.item.EntityFallingCoconut;
import random.beasts.common.entity.item.EntityThrownCoconut;
import random.beasts.common.entity.monster.*;
import random.beasts.common.entity.passive.*;
import random.beasts.common.entity.projectile.EntityCoconutBomb;

import java.util.*;
import java.util.function.Function;

@SuppressWarnings("unused")
public class BeastsEntities {
    public static final Map<EntityType<? extends MobEntity>, SpawnEntry[]> SPAWNS = new HashMap<>();
    private static final Map<EntityType<?>, Tuple<Integer, Integer>> EGGS = new HashMap<>();
    private static int entityId = 0;
    public static final EntityType<EntityPufferfishDog> PUFFERFISH_DOG = create(EntityPufferfishDog::new, EntityPufferfishDog.class, EntityClassification.CREATURE, 0.5f, 0.5f, 0xFBA70C, 0x429BBA, new SpawnEntry(30, 1, 1, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityCoconutBomb> COCONADE = create(EntityCoconutBomb::new, EntityCoconutBomb.class, 0.25F, 0.25F);
    public static final EntityType<EntityBeastsPainting> BEASTS_PAINTING = create(EntityBeastsPainting::new, EntityBeastsPainting.class, 0.5F, 0.5F);
    public static final EntityType<EntityFallingCoconut> FALLING_COCONUT = create(EntityFallingCoconut::new, EntityFallingCoconut.class, 0.98F, 0.98F);
    public static final EntityType<EntityThrownCoconut> THROWN_COCONUT = create(EntityThrownCoconut::new, EntityThrownCoconut.class, 0.25F, 0.25F);
    //private static final EntityEntry RABBITMAN = create(EntityRabbitman.class, 0x4E362D, 0xE5E5E5);
    public static final EntityType<EntityCoconutCrab> COCONUT_CRAB = create(EntityCoconutCrab::new, EntityCoconutCrab.class, EntityClassification.CREATURE, 0.5f, 0.4f, 0x3C1C11, 0xA16745, new SpawnEntry(30, 2, 4, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityCoralBranchie> CORAL_BRANCHIE = createBranchie(EntityCoralBranchie::new, EntityCoralBranchie.class, 0xEDEC4C, 0xD6549B, BeastsBlocks.CORAL_PLANTS.values(), new Tuple<>(2, EntityCoralBranchie::create));
    public static final EntityType<EntityChorusBranchie> CHORUS_BRANCHIE = createBranchie(EntityChorusBranchie::new, EntityChorusBranchie.class, 0x401A40, 0xEED6EE, Collections.singletonList(Blocks.CHORUS_PLANT), new Tuple<>(2, EntityChorusBranchie::create));
    public static final EntityType<EntityWoodBranchie> WOOD_BRANCHIE = createBranchie(EntityWoodBranchie::new, EntityWoodBranchie.class, 0x745A36, 0x57AD3F, EntityWoodBranchie.BLOCKS, new Tuple<>(6, EntityWoodBranchie::create));
    public static final EntityType<EntityVileEel> VILE_EEL = create(EntityVileEel::new, EntityVileEel.class, EntityClassification.AMBIENT, 1.5F, 1.8F, 0x313337, 0x987CAF, new SpawnEntry(40, 1, 1, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityLandwhale> LANDWHALE = create(EntityLandwhale::new, EntityLandwhale.class, EntityClassification.CREATURE, 1.8F, 2.0F, 0x587377, 0xE25AA5, new SpawnEntry(30, 1, 1, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityGiantGardenEel> GIANT_GARDEN_EEL = create(EntityGiantGardenEel::new, EntityGiantGardenEel.class, EntityClassification.CREATURE, 0.5f, 2.7f, 0xCECEAF, 0x7A745E, new SpawnEntry(30, 4, 8, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntitySkewerShrimp> SKEWER_SHRIMP = create(EntitySkewerShrimp::new, EntitySkewerShrimp.class, EntityClassification.CREATURE, 0.5f, 0.4f, 0xEA4E3C, 0xFFACA3, new SpawnEntry(30, 4, 8, BeastsBiomes.DRIED_REEF));
    //private static final EntityEntry WHIPPING_BARNACLE = create(EntityWhippingBarnacle.class, 0x278E6C, 0xEA8F9E, new SpawnEntry(EntityClassification.CREATURE, 50, 1, 3, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityHermitTurtle> HERMIT_TURTLE = create(EntityHermitTurtle::new, EntityHermitTurtle.class, EntityClassification.CREATURE, 0.7F, 0.7F, 0x8F8F8F, 0x63862E, new SpawnEntry(30, 1, 3, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityAnemoneCrawler> ANEMONE_CRAWLER = create(EntityAnemoneCrawler::new, EntityAnemoneCrawler.class, EntityClassification.CREATURE, 0.5f, 0.5f, 0xEF5C13, 0xDCE3E5);
    public static final EntityType<EntityLegfish> LEGFISH = create(EntityLegfish::new, EntityLegfish.class, EntityClassification.CREATURE, 0.3f, 0.3f, 0x6DAF78, 0xDB3E00, new SpawnEntry(30, 2, 4, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityScallop> SCALLOP = create(EntityScallop::new, EntityScallop.class, EntityClassification.MONSTER, 0.5f, 0.5f, 0xE8C7A7, 0x7C3318, new SpawnEntry(30, 2, 4, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityTrimola> TRIMOLA = create(EntityTrimola::new, EntityTrimola.class, EntityClassification.CREATURE, 1.0F, 1.2F, 0x261E1B, 0xA09783, new SpawnEntry(20, 2, 4, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntitySlimeSlug> SLIME_SLUG = create(EntitySlimeSlug::new, EntitySlimeSlug.class, EntityClassification.CREATURE, 0.8f, 1.0f, 0xD3524C, 0xFFDB66, new SpawnEntry(20, 2, 4, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityButterflyFish> BUTTERFLYFISH = create(EntityButterflyFish::new, EntityButterflyFish.class, EntityClassification.CREATURE, 0.3F, 0.3F, 0x5B3138, 0xEE5705, new SpawnEntry(20, 2, 4, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityFireflySquid> FIREFLY_SQUID = create(EntityFireflySquid::new, EntityFireflySquid.class, EntityClassification.CREATURE, 0.5f, 0.5f, 0x411A43, 0x50A870);
    public static final EntityType<EntityTupala> TUPALA = create(EntityTupala::new, EntityTupala.class, EntityClassification.AMBIENT, 0.3F, 0.3F, 0x411A43, 0x50A870);
    public static final EntityType<EntityGlowShrimp> GLOW_SHRIMP = create(EntityGlowShrimp::new, EntityGlowShrimp.class, EntityClassification.CREATURE, 0.3F, 0.3F, 0x411A43, 0x50A870);
    public static final EntityType<EntityAnglerQueen> ANGLER_QUEEN = create(EntityAnglerQueen::new, EntityAnglerQueen.class, EntityClassification.AMBIENT, 2.4F, 1.8F, 0x411A43, 0x50A870);
    public static final EntityType<EntityAnglerPup> ANGLER_PUP = create(EntityAnglerPup::new, EntityAnglerPup.class, EntityClassification.AMBIENT, 0.5F, 0.5F, 0x411A43, 0x50A870);
    public static final EntityType<EntityIsopod> ISOPOD = create(EntityIsopod::new, EntityIsopod.class, EntityClassification.CREATURE, 0.3F, 0.3F, 0x411A43, 0x50A870);

    private static <T extends BeastsBranchie> EntityType<T> createBranchie(EntityType.IFactory<T> factory, Class<T> cls, int prim, int sec, Collection<? extends Block> validBlocks, Tuple<Integer, Function<BlockEvent.BreakEvent, ? extends BeastsBranchie>> create) {
        BeastsBranchie.TYPES.put(validBlocks, create);
        return create(factory, cls, EntityClassification.CREATURE, 0.2F, 0.9F, prim, sec);
    }

    private static <T extends MobEntity> EntityType<T> create(EntityType.IFactory<T> factory, Class<T> cls, EntityClassification classification, float width, float height, int prim, int sec, SpawnEntry... spawns) {
        EntityType<T> type = create(factory, cls, classification, width, height);
        EGGS.put(type, new Tuple<>(prim, sec));
        if (spawns.length > 0) SPAWNS.put(type, spawns);
        return type;
    }

    private static <T extends Entity> EntityType<T> create(EntityType.IFactory<T> factory, Class<T> cls, EntityClassification classification, float width, float height) {
        String entityName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, cls.getSimpleName().replace("Entity", ""));
        EntityType<T> type = EntityType.Builder.create(factory, classification).size(width, height).build(entityName);
        type.setRegistryName(entityName);
        BeastsRegistries.ENTITIES.add(type);
        return type;
    }

    private static <T extends Entity> EntityType<T> create(EntityType.IFactory<T> factory, Class<T> cls, float width, float height) {
        return create(factory, cls, EntityClassification.MISC, width, height);
    }

    public static void registerEggs() {
        final Item.Properties properties = new Item.Properties().group(BeastsUtils.getRegistryTab());
        EGGS.forEach((type, egg) -> BeastsUtils.addToRegistry(new SpawnEggItem(type, egg.getA(), egg.getB(), properties), Objects.requireNonNull(type.getRegistryName()).getPath() + "_spawn_egg"));
    }

    public static class SpawnEntry {
        public int weight;
        public int min;
        public int max;
        public Iterable<Biome> biomes;

        public SpawnEntry(int weight, int min, int max, Iterable<Biome> biomes) {
            this.weight = weight;
            this.min = min;
            this.max = max;
            this.biomes = biomes;
        }

        public SpawnEntry(int weight, int min, int max, Biome... biomes) {
            this(weight, min, max, Arrays.asList(biomes));
        }
    }
}
