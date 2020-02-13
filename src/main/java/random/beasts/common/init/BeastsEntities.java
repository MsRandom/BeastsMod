package random.beasts.common.init;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import org.apache.commons.lang3.StringUtils;
import random.beasts.api.configuration.BeastsSpawnConfig;
import random.beasts.api.entity.BeastsBranchie;
import random.beasts.api.main.BeastsReference;
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

    public static final List<EntityEntry> LIST = new ArrayList<>();

    private static int entityId = 0;
    private static final EntityEntry PUFFERFISH_DOG = createEntry(EntityPufferfishDog.class, 0xFBA70C, 0x429BBA);
    //private static final EntityEntry RABBITMAN = createEntry(EntityRabbitman.class, 0x4E362D, 0xE5E5E5);
    private static final EntityEntry COCONUT_CRAB = createEntry(EntityCoconutCrab.class, 0x3C1C11, 0xA16745);
    private static final EntityEntry CORAL_BRANCHIE = createBranchie(EntityCoralBranchie.class, 0xEDEC4C, 0xD6549B, BeastsBlocks.CORAL_PLANTS.values(), new Tuple<>(2, EntityCoralBranchie::create));
    private static final EntityEntry CHORUS_BRANCHIE = createBranchie(EntityChorusBranchie.class, 0x401A40, 0xEED6EE, Collections.singletonList(Blocks.CHORUS_PLANT), new Tuple<>(2, EntityChorusBranchie::create));
    private static final EntityEntry WOOD_BRANCHIE = createBranchie(EntityWoodBranchie.class, 0x745A36, 0x57AD3F, Arrays.asList(Blocks.LOG, Blocks.LOG2), new Tuple<>(6, EntityWoodBranchie::create));
    private static final EntityEntry VILE_EEL = createEntry(EntityVileEel.class, 0x313337, 0x987CAF);
    private static final EntityEntry LANDWHALE = createEntry(EntityLandwhale.class, 0x587377, 0xE25AA5);
    private static final EntityEntry COCONADE = createEntry(EntityCoconutBomb.class);
    private static final EntityEntry GIANT_GARDEN_EEL = createEntry(EntityGiantGardenEel.class, 0xCECEAF, 0x7A745E);
    private static final EntityEntry SKEWER_SHRIMP = createEntry(EntitySkewerShrimp.class, 0xEA4E3C, 0xFFACA3);
    private static final EntityEntry BEASTS_PAINTING = createEntry(EntityBeastsPainting.class);
    private static final EntityEntry FALLING_COCONUT = createEntry(EntityFallingCoconut.class);
    //private static final EntityEntry WHIPPING_BARNACLE = createEntry(EntityWhippingBarnacle.class, 0x278E6C, 0xEA8F9E, new SpawnEntry(EnumCreatureType.CREATURE, 50, 1, 3, BeastsBiomes.DRIED_REEF));
    private static final EntityEntry HERMIT_TURTLE = createEntry(EntityHermitTurtle.class, 0x8F8F8F, 0x63862E);
    private static final EntityEntry ANEMONE_CRAWLER = createEntry(EntityAnemoneCrawler.class, 0xEF5C13, 0xDCE3E5);
    private static final EntityEntry LEGFISH = createEntry(EntityLegfish.class, 0x6DAF78, 0xDB3E00);
    private static final EntityEntry SCALLOP = createEntry(EntityScallop.class, 0xE8C7A7, 0x7C3318);
    private static final EntityEntry TRIMOLA = createEntry(EntityTrimola.class, 0x261E1B, 0xA09783);
    private static final EntityEntry SLIME_SLUG = createEntry(EntitySlimeSlug.class, 0xD3524C, 0xFFDB66);
    private static final EntityEntry BUTTERFLYFISH = createEntry(EntityButterflyFish.class, 0x5B3138, 0xEE5705);
    private static final EntityEntry THROWN_COCONUT = createEntry(EntityThrownCoconut.class);

    private static EntityEntry createBranchie(Class<? extends BeastsBranchie> cls, int prim, int sec, Collection<? extends Block> validBlocks, Tuple<Integer, Function<BlockEvent.BreakEvent, ? extends BeastsBranchie>> create) {
        BeastsBranchie.TYPES.put(validBlocks, create);
        return createEntry(cls, prim, sec);
    }

    private static <T extends Entity> EntityEntry createEntry(Class<T> cls, int prim, int sec) {
        EntityEntryBuilder<T> builder = getBuilder(cls).egg(prim, sec);
/*        for (SpawnEntry spawn : spawns)
            builder.spawn(spawn.type, spawn.weight, spawn.min, spawn.max, spawn.biomes);*/
        EntityEntry entry = builder.build();
        LIST.add(entry);
        return entry;
    }

    private static EntityEntry createEntry(Class<? extends Entity> cls) {
        EntityEntry entry = getBuilder(cls).build();
        LIST.add(entry);
        return entry;
    }

    private static <T extends Entity> EntityEntryBuilder<T> getBuilder(Class<T> cls) {
        String entityName = StringUtils.join(cls.getSimpleName().replace("Entity", "").split("(?=[A-Z])"), "_").toLowerCase();
        EntityEntryBuilder<T> builder = EntityEntryBuilder.create();
        builder.entity(cls).id(new ResourceLocation(BeastsReference.ID, entityName), entityId++).name(entityName).tracker(90, 1, true);
        return builder;
    }

    public static class SpawnEntry {
        public EnumCreatureType type;
        public int weight;
        public int min;
        public int max;
        public Iterable<String> biomes;
        public Class entity;

        public SpawnEntry(Class e, EnumCreatureType type, int weight, int min, int max, Iterable<String> biomes) {
            this.type = type;
            this.weight = weight;
            this.min = min;
            this.max = max;
            this.entity = e;
            this.biomes = biomes;
            BeastsSpawnConfig.SPAWNS.add(this);
        }

        public SpawnEntry(Class e, EnumCreatureType type, int weight, int min, int max, String... biomes) {
            this(e, type, weight, min, max, Arrays.asList(biomes));
        }
    }
}
