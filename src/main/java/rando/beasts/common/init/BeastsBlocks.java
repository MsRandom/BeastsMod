package rando.beasts.common.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import rando.beasts.common.block.BeastsBlock;
import rando.beasts.common.block.BeastsLog;
import rando.beasts.common.block.BeastsSapling;
import rando.beasts.common.block.BlockCoconut;
import rando.beasts.common.block.BlockCoral;
import rando.beasts.common.block.BlockCoralPlant;
import rando.beasts.common.block.BlockCoralSapling;
import rando.beasts.common.block.BlockGlowRoot;
import rando.beasts.common.block.BlockHermitShell;
import rando.beasts.common.block.BlockJellyWoodDoor;
import rando.beasts.common.block.BlockJellyWoodFence;
import rando.beasts.common.block.BlockJellyWoodFenceGate;
import rando.beasts.common.block.BlockJellyfishLeaves;
import rando.beasts.common.block.BlockPalmTreeLeaves;
import rando.beasts.common.block.BlockTentacle;
import rando.beasts.common.block.CoralColor;
import rando.beasts.common.world.gen.feature.WorldGenJellyfishTrees;
import rando.beasts.common.world.gen.feature.WorldGenPalmTrees;

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
    //this is a map so any coral plant can be gotten by the color or index/ordinal
    public static final Map<CoralColor, BlockCoralPlant> CORAL_PLANTS = new HashMap<>();

	static {
		for (CoralColor color : CoralColor.values()) CORAL_PLANTS.put(color, new BlockCoralPlant(color));
 	}
}
