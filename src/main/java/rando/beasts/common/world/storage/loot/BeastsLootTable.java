package rando.beasts.common.world.storage.loot;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntryTable;
import rando.beasts.common.init.BeastsLootTables;
import rando.beasts.common.main.BeastsReference;

import java.util.function.Supplier;

public class BeastsLootTable {

	public final Supplier<LootEntryTable> tableSupplier;
	public final ResourceLocation id;

	public BeastsLootTable(String location, String replace, Supplier<LootEntryTable> apply) {
		this.tableSupplier = apply;
		this.id = new ResourceLocation(BeastsReference.ID, location);
		BeastsLootTables.TABLES.put(replace, this);
	}
}