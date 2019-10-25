package random.beasts.common.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import random.beasts.common.block.*;
import random.beasts.common.world.gen.feature.WorldGenJellyfishTrees;
import random.beasts.common.world.gen.feature.WorldGenPalmTrees;

public class BeastsBlocks {

	public static final List<Block> LIST = new ArrayList<>();

	public static final Block COCONUT = new BlockCoconut();
    public static final Block GLOW_ROOT_TOP = new BlockGlowRoot(true);
    public static final Block GLOW_ROOT_BOTTOM = new BlockGlowRoot(false);
    public static final Block CORAL_BLOCK = new BlockCoral();
    public static final Block CORAL_SAPLING = new BlockCoralSapling();
    public static final Block JELLYFISH_SAPLING = new BeastsSapling("jellyfish_sapling", WorldGenJellyfishTrees::new);
    public static final Block PALM_SAPLING = new BeastsSapling("palm_sapling", WorldGenPalmTrees::new);
    public static final Block TENTACLE = new BlockTentacle();
    public static final Block PALM_LEAVES = new BlockPalmTreeLeaves();
    public static final Block PALM_LOG = new BeastsLog("palm_log");
    public static final Block PALM_PLANKS = new BeastsBlock(Material.WOOD, "palm_planks");
    public static final Block JELLY_WOOD = new BeastsLog("jellywood").setLightLevel(0.5F);
    public static final Block JELLY_LEAVES = new BlockJellyfishLeaves();
    public static final Block JELLY_WOOD_PLANKS = new BeastsBlock(Material.WOOD, "jellywood_planks");
    public static final Block JELLY_WOOD_DOOR = new BlockJellyWoodDoor();
    public static final Block JELLY_WOOD_FENCE = new BlockJellyWoodFence();
    public static final Block JELLY_WOOD_GATE = new BlockJellyWoodFenceGate();
    public static final Block HERMIT_SHELL = new BlockHermitShell();
    public static final Block ANEMONE_MOUTH = new BlockAnemoneMouth();
    public static final Block ANEMONE_STALK = new BeastsBlock(Material.CLAY, "anemone_stalk");
    public static final Block ANEMONE_TENTACLE = new BeastsBlock(Material.CLAY, "anemone_tentacle");
    //this is a map so any coral plant can be gotten by the color or index/ordinal
    public static final Map<CoralColor, BlockCoralPlant> CORAL_PLANTS = new HashMap<>();

	static {
		for (CoralColor color : CoralColor.values()) CORAL_PLANTS.put(color, new BlockCoralPlant(color));
 	}
}
