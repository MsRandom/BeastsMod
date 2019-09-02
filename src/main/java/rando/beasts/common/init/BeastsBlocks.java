package rando.beasts.common.init;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rando.beasts.common.block.*;
import rando.beasts.common.world.gen.feature.WorldGenJellyfishTrees;

public class BeastsBlocks {

	public static final List<Block> LIST = new ArrayList<>();

	public static final Block COCONUT = new BlockCoconut();
    public static final Block GLOW_ROOT_TOP = new BlockGlowRoot(true);
    public static final Block GLOW_ROOT_BOTTOM = new BlockGlowRoot(false);
    public static final Block CORAL_BLOCK = new BlockCoral();
    public static final Block CORAL_SAPLING = new BlockCoralSapling();
    public static final Block JELLYFISH_SAPLING = new BeastsSapling("jellyfish_sapling", new WorldGenJellyfishTrees(true));
    public static final Block TENTACLE = new BlockTentacle();
    public static final Block JELLY_WOOD = new BlockJellyfisLog();
    public static final Block JELLY_LEAVES = new BlockJellyfishLeaves();
    public static final Block JELLY_WOOD_PLANKS = new BeastsBlock(Material.WOOD, "jellywood_planks");
    public static final Block JELLY_WOOD_DOOR = new BlockJellyWoodDoor();
    public static final Block JELLY_WOOD_FENCE = new BlockJellyWoodFence();
    public static final Block JELLY_WOOD_GATE = new BlockJellyWoodFenceGate();
    //this is a map so any coral plant can be gotten by the color or index/ordinal
    public static final Map<CoralColor, BlockCoralPlant> CORAL_PLANTS = new HashMap<>();

	static {
		for (CoralColor color : CoralColor.values()) CORAL_PLANTS.put(color, new BlockCoralPlant(color));
 	}
}
