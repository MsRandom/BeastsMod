package net.msrandom.beasts.common.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.msrandom.beasts.api.block.*;
import net.msrandom.beasts.common.block.*;
import net.msrandom.beasts.common.world.gen.feature.WorldGenJellyfishTrees;
import net.msrandom.beasts.common.world.gen.feature.WorldGenPalmTrees;

import java.util.HashMap;
import java.util.Map;

public class BeastsBlocks {

    public static final Block ABYSSAL_GRASS = new BlockAbyssalGrass();
    public static final Block ABYSSAL_ORE = new BlockAbyssalOre();
    public static final Block ABYSSAL_SAND = new BlockAbyssalSand();
    public static final Block ABYSSAL_SANDSTONE = new BlockAbyssalStone("abyssal_sandstone");
    public static final Block ABYSSAL_SANDSTONE_CHISELED = new BlockAbyssalStone("abyssal_sandstone_chiseled");
    public static final Block ABYSSAL_SANDSTONE_SMOOTH = new BlockAbyssalStone("abyssal_sandstone_smooth");
    public static final Block ABYSSAL_STONE = new BlockAbyssalStone("abyssal_stone");
    public static final Block ABYSSAL_TENDRILS = new BlockAbyssalTendrils();
    public static final Block ABYSSAL_VENT = new BlockAbyssalVentMouth();
    public static final Block GLOW_CORAL_PINK = new BlockGlowCoral("glow_coral_pink");
    public static final Block GLOW_CORAL_BLUE = new BlockGlowCoral("glow_coral_blue");
    public static final Block TENTACLE_GRASS = new BlockTentacleGrass();
    public static final Block COCONUT = new BlockCoconut();
    public static final Block CORAL_BLOCK = new BlockCoral();
    public static final Block CORAL_SAPLING = new BlockCoralSapling();
    public static final Block JELLYWOOD_SAPLING = new BeastsSapling("jellyfish_sapling", WorldGenJellyfishTrees::new);
    public static final Block PALM_SAPLING = new BeastsSapling("palm_sapling", WorldGenPalmTrees::new);
    public static final Block TENTACLE = new BlockTentacle();
    public static final Block TUBEWORM_CROP = new BlockTubewormCrop();
    public static final Block PALM_LEAVES = new BlockPalmTreeLeaves();
    public static final Block PALM_LOG = new BeastsLog("palm_log");
    public static final Block PALM_PLANKS = new BeastsBlock(Material.WOOD, "palm_planks").setHardness(2.0F).setResistance(5.0F);
    public static final BeastsDoor PALM_DOOR = new BeastsDoor("palm");
    public static final Block PALM_FENCE = new BeastsFence("palm");
    public static final Block PALM_GATE = new BeastsFenceGate("palm");
    public static final Block PALM_TRAPDOOR = new BeastsTrapdoor("palm");
    public static final Block PALM_STAIRS = new BeastsStairs(PALM_PLANKS, "palm");
    public static final BeastsSlab PALM_SLAB = new BeastsSlab("palm");
    public static final Block JELLY_WOOD = new BeastsLog("jellywood").setLightLevel(0.5F);
    public static final Block JELLY_LEAVES = new BlockJellyfishLeaves();
    public static final Block JELLY_WOOD_PLANKS = new BeastsBlock(Material.WOOD, "jellywood_planks").setHardness(2.0F).setResistance(5.0F);
    public static final BeastsDoor JELLY_WOOD_DOOR = new BeastsDoor("jellywood");
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
    public static final Block SHELL_BRICK = new BlockShell("shell_brick");
    public static final Block SHELL_STAIRS = new BeastsStairs(SHELL_BRICK, "shell");
    public static final BeastsSlab SHELL_SLAB = new BeastsSlab("shell");
    public static final BlockShell[] SHELL_BLOCKS = {new BlockShell("shell_block"), new BlockShell("brown_shell_block"), new BlockShell("light_brown_shell_block"), new BlockShell("tan_shell_block")};
    public static final BlockShellPiece[] SHELL_PIECES = {new BlockShellPiece("shell_piece"), new BlockShellPiece("brown_shell_piece"), new BlockShellPiece("light_brown_shell_piece"), new BlockShellPiece("tan_shell_piece")};

    static {
        for (CoralColor color : CoralColor.values()) CORAL_PLANTS.put(color, new BlockCoralPlant(color));
    }
}
