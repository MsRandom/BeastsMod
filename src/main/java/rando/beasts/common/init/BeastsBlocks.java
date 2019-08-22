package rando.beasts.common.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import rando.beasts.common.block.*;

import java.util.*;

public class BeastsBlocks {

	private static final Map<Block[], List<?>> CACHED_LISTS = new HashMap<>();

	public static final List<Block> LIST = new ArrayList<>();

	public static final Block COCONUT = new BlockCoconut();
	public static final Block WHITE_SAND = new BeastsBlock(Material.SAND, "white_sand");
	public static final BlockCoralPlant[] CORAL_PLANTS = new BlockCoralPlant[BlockCoral.Color.values().length];
	public static final BlockCoral[] CORAL_BLOCKS = new BlockCoral[BlockCoral.Color.values().length];

	public static final Block GLOW_ROOT_TOP = new BlockGlowRoot("glow_root_top", true);
	public static final Block GLOW_ROOT_BOTTOM = new BlockGlowRoot("glow_root_bottom", false);

	public static int indexOf(Block[] array, Block block) {
		if(!CACHED_LISTS.containsKey(array)) CACHED_LISTS.put(array, Arrays.asList(array));
		return CACHED_LISTS.get(array).indexOf(block);
	}

	static {
		for (int i = 0; i < BlockCoral.Color.values().length; i++) {
			CORAL_PLANTS[i] = new BlockCoralPlant(BlockCoral.Color.values()[i]);
			CORAL_BLOCKS[i] = new BlockCoral(BlockCoral.Color.values()[i]);
		}
 	}
}
