package rando.beasts.common.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import rando.beasts.common.block.*;

public class BeastsBlocks {

	public static final List<Block> LIST = new ArrayList<>();

	public static final Block COCONUT = new BlockCoconut();
    public static final Block GLOW_ROOT_TOP = new BlockGlowRoot("glow_root_top", true);
    public static final Block GLOW_ROOT_BOTTOM = new BlockGlowRoot("glow_root_bottom", false);
    public static final Block CORAL_BLOCK = new BlockCoral();
    public static final Block CORAL_SAPLING = new BlockCoralSapling();
    //this is a map so any coral plant can be gotten by the color or index/ordinal
    public static final Map<CoralColor, BlockCoralPlant> CORAL_PLANTS = new HashMap<>();

	static {
		for (CoralColor color : CoralColor.values()) CORAL_PLANTS.put(color, new BlockCoralPlant(color));
 	}
}
