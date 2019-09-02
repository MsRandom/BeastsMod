package rando.beasts.common.utils.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import rando.beasts.common.utils.BeastsReference;

public class LootHandler {
	private static final String[] TABLES = {"inject/fish"};
	
	public static void registerLootTables() {
		for(String s : TABLES) LootTableList.register(new ResourceLocation(BeastsReference.ID, s));
	}
	
	public static LootPool getInjectPool(String entryName) {
		return new LootPool(new LootEntry[] { getInjectEntry(entryName, 1) }, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0), "beasts_inject_pool");
	}

	public static LootEntryTable getInjectEntry(String name, int weight) {
		return new LootEntryTable(new ResourceLocation(BeastsReference.ID, "inject/" + name), weight, 0, new LootCondition[0], "beasts_inject_entry");
	}

}
