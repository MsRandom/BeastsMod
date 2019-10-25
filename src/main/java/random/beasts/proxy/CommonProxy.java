package random.beasts.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import random.beasts.common.block.BlockPalmTreeLeaves;
import random.beasts.common.init.BeastsLootTables;
import random.beasts.common.init.BeastsStructures;
import random.beasts.common.init.BeastsTileEntities;
import random.beasts.common.init.BeastsTriggers;

public class CommonProxy {

    public void preInit() {
        BeastsStructures.init();
        BeastsTriggers.init();
        BeastsTileEntities.init();
        BeastsLootTables.init();
    }

    public ModelBiped getArmorModel(Item armorItem, EntityEquipmentSlot armorSlot){
        return null;
    }

    public String getArmorTexture(Item armorItem, EntityEquipmentSlot armorSlot){
        return null;
    }

    public void setGraphicsLevel(BlockPalmTreeLeaves block, boolean enabled) {}
}
