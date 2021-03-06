package net.msrandom.beasts.common.world.storage.loot;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.common.init.BeastsLootTables;

import java.util.function.Supplier;

public class BeastsLootTable {

    public final Supplier<LootEntryTable> tableSupplier;
    public final ResourceLocation id;

    public BeastsLootTable(String location, String replace, Supplier<LootEntryTable> apply) {
        this.tableSupplier = apply;
        this.id = new ResourceLocation(BeastsReference.ID, location);
        BeastsLootTables.TABLES.put(new ResourceLocation(replace), this);
    }
}
