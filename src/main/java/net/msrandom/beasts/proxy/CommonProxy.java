package net.msrandom.beasts.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import net.msrandom.beasts.api.block.BeastsDoor;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.api.main.BeastsUtils;
import net.msrandom.beasts.client.init.BeastsCreativeTabs;
import net.msrandom.beasts.common.block.BlockPalmTreeLeaves;
import net.msrandom.beasts.common.block.BlockShell;
import net.msrandom.beasts.common.block.BlockShellPiece;
import net.msrandom.beasts.common.init.BeastsBlocks;
import net.msrandom.beasts.common.init.BeastsLootTables;
import net.msrandom.beasts.common.init.BeastsTileEntities;
import net.msrandom.beasts.common.init.BeastsTriggers;
import net.msrandom.beasts.common.network.BeastsGuiHandler;
import net.msrandom.beasts.common.network.BeastsPacketHandler;
import net.msrandom.beasts.common.world.biome.RealisticBiomeDriedReef;
import rtg.api.RTGAPI;
import rtg.util.ModCompat;

public class CommonProxy {

    public EntityPlayer getPlayer() {
        return null;
    }

    public EntityPlayer getPlayer(MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            return ctx.getServerHandler().player;
        }
        return null;
    }

    public boolean isClientSneaking() {
        return false;
    }

    public boolean isTrimolaAttacking() {
        return false;
    }

    public void preInit() {
        BeastsUtils.setRegistryTab(BeastsCreativeTabs.MAIN);
        BeastsTriggers.init();
        BeastsTileEntities.init();
        BeastsLootTables.init();
        BeastsGuiHandler.init();
    }

    public void init() {
        if (Loader.isModLoaded("rtg")) {
            EnumHelper.addEnum(ModCompat.Mods.class, BeastsReference.ID, new Class[0]);
            RTGAPI.RTG_BIOMES.addBiomes(new RealisticBiomeDriedReef());
        }
        registerOreDict();
        BeastsPacketHandler.initPackets();
    }

    public void registerEventRenders() {
    }

    public ModelBiped getArmorModel(Item armorItem, EntityEquipmentSlot armorSlot) {
        return null;
    }

    public String getArmorTexture(Item armorItem, EntityEquipmentSlot armorSlot) {
        return null;
    }

    public void setGraphicsLevel(BlockPalmTreeLeaves block, boolean enabled) {
    }

    private void registerOreDict() {
        //this could be done better
        OreDictionary.registerOre("logWood", BeastsBlocks.PALM_LOG);
        OreDictionary.registerOre("plankWood", BeastsBlocks.PALM_PLANKS);
        OreDictionary.registerOre("slabWood", BeastsBlocks.PALM_SLAB.half);
        OreDictionary.registerOre("stairWood", BeastsBlocks.PALM_STAIRS);
        OreDictionary.registerOre("fenceWood", BeastsBlocks.PALM_FENCE);
        OreDictionary.registerOre("fenceGateWood", BeastsBlocks.PALM_GATE);
        OreDictionary.registerOre("doorWood", BeastsDoor.DOOR_ITEMS.get(BeastsBlocks.PALM_DOOR));
        OreDictionary.registerOre("logJelly", BeastsBlocks.JELLY_WOOD);
        OreDictionary.registerOre("plankJelly", BeastsBlocks.JELLY_WOOD_PLANKS);
        OreDictionary.registerOre("slabJelly", BeastsBlocks.JELLY_WOOD_SLAB.half);
        OreDictionary.registerOre("stairJelly", BeastsBlocks.JELLY_WOOD_STAIRS);
        OreDictionary.registerOre("fenceJelly", BeastsBlocks.JELLY_WOOD_FENCE);
        OreDictionary.registerOre("fenceGateJelly", BeastsBlocks.JELLY_WOOD_GATE);
        OreDictionary.registerOre("doorJelly", BeastsDoor.DOOR_ITEMS.get(BeastsBlocks.JELLY_WOOD_DOOR));
        for (BlockShell shell : BeastsBlocks.SHELL_BLOCKS) OreDictionary.registerOre("blockShell", shell);
        for (BlockShellPiece shell : BeastsBlocks.SHELL_PIECES) OreDictionary.registerOre("pieceShell", shell);
    }
}
