package random.beasts.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import random.beasts.common.block.BlockPalmTreeLeaves;
import random.beasts.common.init.*;
import random.beasts.common.network.BeastsGuiHandler;

public class CommonProxy {

    public void preInit() {
        BeastsStructures.init();
        BeastsTriggers.init();
        BeastsTileEntities.init();
        BeastsLootTables.init();
        BeastsGuiHandler.init();
        BeastsWorldGenerators.init();
    }

    public void init() {
        registerOreDict();
    }

    public ModelBiped getArmorModel(Item armorItem, EntityEquipmentSlot armorSlot){
        return null;
    }

    public String getArmorTexture(Item armorItem, EntityEquipmentSlot armorSlot){
        return null;
    }

    public void setGraphicsLevel(BlockPalmTreeLeaves block, boolean enabled) {}

    private void registerOreDict() {
        //this could be done better
        OreDictionary.registerOre("logWood", BeastsBlocks.PALM_LOG);
        OreDictionary.registerOre("plankWood", BeastsBlocks.PALM_PLANKS);
        OreDictionary.registerOre("slabWood", BeastsBlocks.PALM_SLAB.half);
        OreDictionary.registerOre("stairWood", BeastsBlocks.PALM_STAIRS);
        OreDictionary.registerOre("fenceWood", BeastsBlocks.PALM_FENCE);
        OreDictionary.registerOre("fenceGateWood", BeastsBlocks.PALM_GATE);
        OreDictionary.registerOre("doorWood", BeastsBlocks.PALM_DOOR);
        OreDictionary.registerOre("logWood", BeastsBlocks.JELLY_WOOD);
        OreDictionary.registerOre("plankWood", BeastsBlocks.JELLY_WOOD_PLANKS);
        OreDictionary.registerOre("slabWood", BeastsBlocks.JELLY_WOOD_SLAB.half);
        OreDictionary.registerOre("stairWood", BeastsBlocks.JELLY_WOOD_STAIRS);
        OreDictionary.registerOre("fenceWood", BeastsBlocks.JELLY_WOOD_FENCE);
        OreDictionary.registerOre("fenceGateWood", BeastsBlocks.JELLY_WOOD_GATE);
        OreDictionary.registerOre("doorWood", BeastsBlocks.JELLY_WOOD_DOOR);
        OreDictionary.registerOre("blockShell", BeastsBlocks.SHELL_BLOCK);
        OreDictionary.registerOre("blockShell", BeastsBlocks.BROWN_SHELL_BLOCK);
        OreDictionary.registerOre("blockShell", BeastsBlocks.LIGHT_BROWN_SHELL_BLOCK);
        OreDictionary.registerOre("blockShell", BeastsBlocks.TAN_SHELL_BLOCK);
    }
}
