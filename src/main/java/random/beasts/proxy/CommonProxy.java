package random.beasts.proxy;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import random.beasts.api.main.BeastsUtils;
import random.beasts.client.init.BeastsCreativeTabs;
import random.beasts.common.init.BeastsLootTables;
import random.beasts.common.init.BeastsStructures;
import random.beasts.common.init.BeastsTriggers;
import random.beasts.common.init.BeastsWorldGenerators;
import random.beasts.common.network.BeastsGuiHandler;

public class CommonProxy {

    public PlayerEntity getPlayer() {
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
        BeastsStructures.init();
        BeastsTriggers.init();
        BeastsLootTables.init();
        BeastsGuiHandler.init();
        BeastsWorldGenerators.init();
    }

    public void registerEventRenders() {
    }

    public <A extends BipedModel<?>> A getArmorModel(Item armorItem, EquipmentSlotType armorSlot) {
        return null;
    }

    public String getArmorTexture(Item armorItem, EquipmentSlotType armorSlot) {
        return null;
    }

    /*private void registerOreDict() {
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
    }*/
}
