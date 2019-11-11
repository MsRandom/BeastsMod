package random.beasts.common.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import random.beasts.common.block.*;
import random.beasts.common.world.gen.feature.WorldGenJellyfishTrees;
import random.beasts.common.world.gen.feature.WorldGenPalmTrees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeastsBlocks {

	public static final List<Block> LIST = new ArrayList<>();

	public static final Block COCONUT = new BlockCoconut();
    public static final Block GLOW_ROOT_TOP = new BlockGlowRoot(true);
    public static final Block GLOW_ROOT_BOTTOM = new BlockGlowRoot(false);
    public static final Block CORAL_BLOCK = new BlockCoral();
    public static final Block CORAL_SAPLING = new BlockCoralSapling();
    public static final Block JELLYWOOD_SAPLING = new BeastsSapling("jellyfish_sapling", WorldGenJellyfishTrees::new);
    public static final Block PALM_SAPLING = new BeastsSapling("palm_sapling", WorldGenPalmTrees::new);
    public static final Block TENTACLE = new BlockTentacle();
    public static final Block PALM_LEAVES = new BlockPalmTreeLeaves();
    public static final Block PALM_LOG = new BeastsLog("palm_log");
    public static final Block PALM_PLANKS = new BeastsBlock(Material.WOOD, "palm_planks");
    public static final Block PALM_DOOR = new BeastsDoor("palm");
    public static final Block PALM_FENCE = new BeastsFence("palm");
    public static final Block PALM_GATE = new BeastsFenceGate("palm");
    public static final Block PALM_TRAPDOOR = new BeastsTrapdoor("palm");
    public static final Block PALM_STAIRS = new BeastsStairs(PALM_PLANKS, "palm");
    public static final BeastsSlab PALM_SLAB = new BeastsSlab("palm");
    public static final Block JELLY_WOOD = new BeastsLog("jellywood").setLightLevel(0.5F);
    public static final Block JELLY_LEAVES = new BlockJellyfishLeaves();
    public static final Block JELLY_WOOD_PLANKS = new BeastsBlock(Material.WOOD, "jellywood_planks");
    public static final Block JELLY_WOOD_DOOR = new BeastsDoor("jellywood");
    public static final Block JELLY_WOOD_FENCE = new BeastsFence("jellywood");
    public static final Block JELLY_WOOD_GATE = new BeastsFenceGate("jellywood");
    public static final Block JELLY_WOOD_TRAPDOOR = new BeastsTrapdoor("jellywood");
    public static final Block JELLY_WOOD_STAIRS = new BeastsStairs(JELLY_WOOD, "jellywood");
    public static final BeastsSlab JELLY_WOOD_SLAB = new BeastsSlab("jellywood");
    public static final Block HERMIT_SHELL = new BlockHermitShell();
    public static final Block ANEMONE_MOUTH = new BlockAnemoneMouth();
    public static final Block ANEMONE_STALK = new BeastsAnemoneBlock("stalk");
    public static final Block ANEMONE_TENTACLE = new BeastsAnemoneBlock("tentacle");
    //this is a map so any coral plant can be gotten by the color or index/ordinal
    public static final Map<CoralColor, BlockCoralPlant> CORAL_PLANTS = new HashMap<>();

	static {
		for (CoralColor color : CoralColor.values()) CORAL_PLANTS.put(color, new BlockCoralPlant(color));
 	}
}
