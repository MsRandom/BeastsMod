package random.beasts.api.main;

import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.IWorldGenerator;
import random.beasts.api.world.gen.structure.BeastsStructure;

public class BeastsRegistries {
    //TODO Move all other registries here
    public static final BeastsRegistry<Block> BLOCKS = new BeastsRegistry<>();
    public static final BeastsRegistry<Item> ITEMS = new BeastsRegistry<>();
    public static final BeastsRegistry<Biome> BIOMES = new BeastsRegistry<>();
    public static final BeastsRegistry<EntityType<?>> ENTITIES = new BeastsRegistry<>();
    public static final BeastsRegistry<TileEntityType<?>> TILE_ENTITIES = new BeastsRegistry<>();
    public static final BeastsRegistry<BeastsStructure> STRUCTURES = new BeastsRegistry<>();
    public static final BeastsRegistry<IWorldGenerator> GENERATORS = new BeastsRegistry<>();
    public static final BeastsRegistry<ICriterionTrigger<? extends ICriterionInstance>> ADVANCEMENTS = new BeastsRegistry<>();
}
