package rando.beasts.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import rando.beasts.common.block.BlockPalmTreeLeaves;
import rando.beasts.common.init.BeastsLootTables;
import rando.beasts.common.init.BeastsStructures;
import rando.beasts.common.init.BeastsTileEntities;
import rando.beasts.common.init.BeastsTriggers;

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
