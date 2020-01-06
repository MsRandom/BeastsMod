package random.beasts.api.main;

import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.IWorldGenerator;
import random.beasts.api.world.gen.structure.BeastsStructure;

import java.util.ArrayList;
import java.util.List;

public class BeastsRegistries {
    //TODO Move all other registries here
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<Biome> BIOMES = new ArrayList<>();
    public static final List<BeastsStructure> STRUCTURES = new ArrayList<>();
    public static final List<IWorldGenerator> GENERATORS = new ArrayList<>();
    public static final List<ICriterionTrigger<? extends ICriterionInstance>> ADVANCEMENTS = new ArrayList<>();
}
