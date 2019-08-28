package rando.beasts.common.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import rando.beasts.common.block.*;

import java.util.*;

public class BeastsBlocks {

	public static final List<Block> LIST = new ArrayList<>();

	public static final Block COCONUT = new BlockCoconut();
    public static final Block GLOW_ROOT_TOP = new BlockGlowRoot("glow_root_top", true);
    public static final Block GLOW_ROOT_BOTTOM = new BlockGlowRoot("glow_root_bottom", false);
    public static final Block CORAL_BLOCK = new BlockCoral();
    public static final Block CORAL_SAPLING = new BlockCoralSapling();
    public static final List<BlockCoralPlant> CORAL_PLANTS = new ArrayList<>();

	static {
		for (int i = 0; i < BlockCoral.Color.values().length; i++) CORAL_PLANTS.add(i, new BlockCoralPlant(BlockCoral.Color.values()[i]));
 	}
}
