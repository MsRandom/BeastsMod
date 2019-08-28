package rando.beasts.common.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import rando.beasts.common.block.BlockCoconut;
import rando.beasts.common.block.BlockCoral;
import rando.beasts.common.block.BlockCoralPlant;
import rando.beasts.common.block.BlockCoralSapling;
import rando.beasts.common.block.BlockGlowRoot;

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
