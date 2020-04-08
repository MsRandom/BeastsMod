package random.beasts.common.init;

import com.google.common.base.CaseFormat;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import random.beasts.api.entity.BeastsBranchie;
import random.beasts.common.entity.item.EntityBeastsPainting;
import random.beasts.common.entity.item.EntityFallingCoconut;
import random.beasts.common.entity.item.EntityThrownCoconut;
import random.beasts.common.entity.monster.*;
import random.beasts.common.entity.passive.*;
import random.beasts.common.entity.projectile.EntityCoconutBomb;
import random.beasts.common.entity.projectile.EntityGlowShrimpShot;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

@SuppressWarnings("unused")
public class BeastsEntities {
    public static final List<EntityType<?>> LIST = new ArrayList<>();
    public static final Map<EntityType<? extends EntityLiving>, SpawnEntry[]> SPAWNS = new HashMap<>();
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public static final Map<Class<? extends Entity>, Tuple<Float, Float>> SIZES = new HashMap<>();
    private static final Map<EntityType<?>, Tuple<Integer, Integer>> EGGS = new HashMap<>();
    private static int entityId = 0;
    public static final EntityType<EntityCoconutBomb> COCONADE = create(EntityCoconutBomb::new, EntityCoconutBomb.class, 0.25F, 0.25F);
    public static final EntityType<EntityBeastsPainting> BEASTS_PAINTING = create(EntityBeastsPainting::new, EntityBeastsPainting.class, 0.5F, 0.5F);
    public static final EntityType<EntityFallingCoconut> FALLING_COCONUT = create(EntityFallingCoconut::new, EntityFallingCoconut.class, 0.98F, 0.98F);
    public static final EntityType<EntityThrownCoconut> THROWN_COCONUT = create(EntityThrownCoconut::new, EntityThrownCoconut.class, 0.25F, 0.25F);
    public static final EntityType<EntityPufferfishDog> PUFFERFISH_DOG = create(EntityPufferfishDog::new, EntityPufferfishDog.class, EnumCreatureType.CREATURE, 0.5f, 0.5f, 0xFBA70C, 0x429BBA, new SpawnEntry(30, 1, 1, BeastsBiomes.DRIED_REEF));

    public static final EntityType<EntityCoconutCrab> COCONUT_CRAB = create(EntityCoconutCrab::new, EntityCoconutCrab.class, EnumCreatureType.CREATURE, 0.5f, 0.4f, 0x3C1C11, 0xA16745, new SpawnEntry(30, 2, 4, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityCoralBranchie> CORAL_BRANCHIE = createBranchie(EntityCoralBranchie::new, EntityCoralBranchie.class, 0xEDEC4C, 0xD6549B, BeastsBlocks.CORAL_PLANTS.values(), new Tuple<>(2, EntityCoralBranchie::create));
    public static final EntityType<EntityChorusBranchie> CHORUS_BRANCHIE = createBranchie(EntityChorusBranchie::new, EntityChorusBranchie.class, 0x401A40, 0xEED6EE, Collections.singletonList(Blocks.CHORUS_PLANT), new Tuple<>(2, EntityChorusBranchie::create));
    public static final EntityType<EntityWoodBranchie> WOOD_BRANCHIE = createBranchie(EntityWoodBranchie::new, EntityWoodBranchie.class, 0x745A36, 0x57AD3F, Arrays.asList(Blocks.LOG, Blocks.LOG2), new Tuple<>(6, EntityWoodBranchie::create));
    public static final EntityType<EntityVileEel> VILE_EEL = create(EntityVileEel::new, EntityVileEel.class, EnumCreatureType.AMBIENT, 1.5F, 1.8F, 0x313337, 0x987CAF, new SpawnEntry(40, 1, 1, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityLandwhale> LANDWHALE = create(EntityLandwhale::new, EntityLandwhale.class, EnumCreatureType.CREATURE, 1.8F, 2.0F, 0x587377, 0xE25AA5, new SpawnEntry(30, 1, 1, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityGiantGardenEel> GIANT_GARDEN_EEL = create(EntityGiantGardenEel::new, EntityGiantGardenEel.class, EnumCreatureType.CREATURE, 0.5f, 2.7f, 0xCECEAF, 0x7A745E, new SpawnEntry(30, 4, 8, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntitySkewerShrimp> SKEWER_SHRIMP = create(EntitySkewerShrimp::new, EntitySkewerShrimp.class, EnumCreatureType.CREATURE, 0.5f, 0.4f, 0xEA4E3C, 0xFFACA3, new SpawnEntry(30, 4, 8, BeastsBiomes.DRIED_REEF));
    //private static final EntityEntry WHIPPING_BARNACLE = create(EntityWhippingBarnacle.class, 0x278E6C, 0xEA8F9E, new SpawnEntry(EnumCreatureType.CREATURE, 50, 1, 3, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityHermitTurtle> HERMIT_TURTLE = create(EntityHermitTurtle::new, EntityHermitTurtle.class, EnumCreatureType.CREATURE, 0.7F, 0.7F, 0x8F8F8F, 0x63862E, new SpawnEntry(30, 1, 3, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityAnemoneCrawler> ANEMONE_CRAWLER = create(EntityAnemoneCrawler::new, EntityAnemoneCrawler.class, EnumCreatureType.CREATURE, 0.5f, 0.5f, 0xEF5C13, 0xDCE3E5);
    public static final EntityType<EntityLegfish> LEGFISH = create(EntityLegfish::new, EntityLegfish.class, EnumCreatureType.CREATURE, 0.3f, 0.3f, 0x6DAF78, 0xDB3E00, new SpawnEntry(30, 2, 4, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityScallop> SCALLOP = create(EntityScallop::new, EntityScallop.class, EnumCreatureType.MONSTER, 0.5f, 0.5f, 0xE8C7A7, 0x7C3318, new SpawnEntry(30, 2, 4, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityTrimola> TRIMOLA = create(EntityTrimola::new, EntityTrimola.class, EnumCreatureType.CREATURE, 1.0F, 1.2F, 0x261E1B, 0xA09783, new SpawnEntry(20, 2, 4, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntitySlimeSlug> SLIME_SLUG = create(EntitySlimeSlug::new, EntitySlimeSlug.class, EnumCreatureType.CREATURE, 0.8f, 1.0f, 0xD3524C, 0xFFDB66, new SpawnEntry(20, 2, 4, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityButterflyFish> BUTTERFLYFISH = create(EntityButterflyFish::new, EntityButterflyFish.class, EnumCreatureType.CREATURE, 0.3F, 0.3F, 0x5B3138, 0xEE5705, new SpawnEntry(20, 2, 4, BeastsBiomes.DRIED_REEF));
    public static final EntityType<EntityFireflySquid> FIREFLY_SQUID = create(EntityFireflySquid::new, EntityFireflySquid.class, EnumCreatureType.CREATURE, 0.5f, 0.5f, 0x411A43, 0x50A870);
    public static final EntityType<EntityTupala> TUPALA = create(EntityTupala::new, EntityTupala.class, EnumCreatureType.AMBIENT, 0.3F, 0.3F, 0xCFDBE4, 0x772D2C);
    public static final EntityType<EntityGlowShrimp> GLOW_SHRIMP = create(EntityGlowShrimp::new, EntityGlowShrimp.class, EnumCreatureType.CREATURE, 0.3F, 0.3F, 0x0C4089, 0x38A0BC);
    public static final EntityType<EntityAnglerQueen> ANGLER_QUEEN = create(EntityAnglerQueen::new, EntityAnglerQueen.class, EnumCreatureType.AMBIENT, 2.4F, 1.8F, 0x33251E, 0x26E2FF);
    public static final EntityType<EntityAnglerPup> ANGLER_PUP = create(EntityAnglerPup::new, EntityAnglerPup.class, EnumCreatureType.AMBIENT, 0.5F, 0.5F, 0x33251E, 0x4FC8DA);
    public static final EntityType<EntityIsopod> ISOPOD = create(EntityIsopod::new, EntityIsopod.class, EnumCreatureType.CREATURE, 0.3F, 0.3F, 0xCCCBCD, 0xCCB03F);
    public static final EntityType<EntityAbyssalLegfish> ABYSSAL_LEGFISH = create(EntityAbyssalLegfish::new, EntityAbyssalLegfish.class, EnumCreatureType.CREATURE, 0.3F, 0.3F, 0x3A6053, 0xA9609D);
    public static final EntityType<EntityGlowShrimpShot> GLOW_SHRIMP_SHOT = create(EntityGlowShrimpShot::new, EntityGlowShrimpShot.class, 0.25F, 0.25F);

    private static <T extends BeastsBranchie> EntityType<T> createBranchie(Function<World, T> factory, Class<T> cls, int prim, int sec, Collection<? extends Block> validBlocks, Tuple<Integer, Function<BlockEvent.BreakEvent, ? extends BeastsBranchie>> create) {
        BeastsBranchie.TYPES.put(validBlocks, create);
        return create(factory, cls, EnumCreatureType.CREATURE, 0.2F, 0.9F, prim, sec);
    }

    private static <T extends EntityLiving> EntityType<T> create(Function<World, T> factory, Class<T> cls, EnumCreatureType classification, float width, float height, int prim, int sec, SpawnEntry... spawns) {
        EntityType<T> type = create(factory, cls, classification, width, height);
        EGGS.put(type, new Tuple<>(prim, sec));
        if (spawns.length > 0) SPAWNS.put(type, spawns);
        return type;
    }

    private static <T extends Entity> EntityType<T> create(Function<World, T> factory, Class<T> cls, @Nullable EnumCreatureType classification, float width, float height) {
        String entityName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, cls.getSimpleName().replace("Entity", ""));
        EntityType<T> type = EntityType.Builder.create(factory, cls, classification).size(width, height).build(entityName);
        type.setRegistryName(entityName);
        LIST.add(type);
        return type;
    }

    private static <T extends Entity> EntityType<T> create(Function<World, T> factory, Class<T> cls, float width, float height) {
        //this is since in 1.14 we need creature classifications for everything, set as null in 1.12, but replace null with MISC in 1.14
        return create(factory, cls, null, width, height);
    }

    public static void registerEggs() {
        EGGS.forEach((type, egg) -> type.base.egg(egg.getFirst(), egg.getSecond()));
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

    //Wrapper for the forge EntityEntry that's identical to 1.14 so porting to it is easier
    public static class EntityType<T extends Entity> {
        private final EntityEntryBuilder<T> base;
        private final Class<T> cls;
        private final EnumCreatureType classification;
        private final Function<World, T> factory;
        private String name;
        private EntityEntry built;

        public EntityType(EntityEntryBuilder<T> base, Class<T> cls, EnumCreatureType classification, Function<World, T> factory) {
            base.entity(cls);
            this.base = base;
            this.cls = cls;
            this.classification = classification;
            this.factory = factory;
        }

        public Class<T> getEntityClass() {
            return this.cls;
        }

        public EnumCreatureType getClassification() {
            return this.classification;
        }

        public T create(World world) {
            return factory.apply(world);
        }

        public final void setRegistryName(String name) {
            this.name = name;
        }

        public final String getRegistryName() {
            return this.name;
        }

        public EntityEntry getFinal() {
            if (this.built == null) this.built = this.base.id(name, entityId++).build();
            return this.built;
        }

        public static class Builder<T extends Entity> {
            private final Function<World, T> factory;
            private final Class<T> cls;
            private final EnumCreatureType classification;
            private Tuple<Float, Float> size;

            private Builder(Function<World, T> factoryIn, Class<T> cls, EnumCreatureType classificationIn) {
                this.factory = factoryIn;
                this.cls = cls;
                this.classification = classificationIn;
            }

            public static <T extends Entity> EntityType.Builder<T> create(Function<World, T> factoryIn, Class<T> cls, EnumCreatureType classificationIn) {
                return new EntityType.Builder<>(factoryIn, cls, classificationIn);
            }

            public EntityType.Builder<T> size(float width, float height) {
                SIZES.put(cls, new Tuple<>(width, height));
                return this;
            }

            public EntityType<T> build(String id) {
                EntityEntryBuilder<T> builder = EntityEntryBuilder.create();
                builder.name(id).tracker(40, 2, true);
                return new EntityType<>(builder, cls, classification, factory);
            }
        }
    }
}
