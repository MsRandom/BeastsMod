package random.beasts.api.main;

import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;

public class BeastsRegistries {
    //TODO Move all other registries here
    public static final BeastsRegistry<Block> BLOCKS = new BeastsRegistry<>();
    public static final BeastsRegistry<Item> ITEMS = new BeastsRegistry<>();
    public static final BeastsRegistry<Biome> BIOMES = new BeastsRegistry<>();
    public static final BeastsRegistry<ICriterionTrigger<? extends ICriterionInstance>> ADVANCEMENTS = new BeastsRegistry<>();
}
