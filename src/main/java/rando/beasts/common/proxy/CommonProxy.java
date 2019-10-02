package rando.beasts.common.proxy;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import rando.beasts.common.init.BeastsTriggers;
import rando.beasts.common.main.BeastsMod;
import rando.beasts.common.tileentity.TileEntityCoconut;
import rando.beasts.common.utils.BeastsReference;
import rando.beasts.common.utils.handlers.GuiHandler;
import rando.beasts.common.utils.handlers.LootHandler;

public class CommonProxy {

	public void preInit(){
		//StructureRabbitVillagePieces.register();
		//GameRegistry.registerWorldGenerator((random, chunkX, chunkZ, world, chunkGenerator, chunkProvider) -> BeastsMod.RABBIT_VILLAGE.generate(world, random, world.getHeight(new BlockPos((chunkX * 16) + 8, 0, (chunkZ * 16) + 8))), 0);
		GameRegistry.registerTileEntity(TileEntityCoconut.class, new ResourceLocation(BeastsReference.ID, "coconut"));
		for(ICriterionTrigger<? extends ICriterionInstance> trigger : BeastsTriggers.LIST) CriteriaTriggers.register(trigger);
		ForgeModContainer.logCascadingWorldGeneration = false;
		LootHandler.registerLootTables();
		NetworkRegistry.INSTANCE.registerGuiHandler(BeastsMod.instance,new GuiHandler());
	}

	public ModelBiped getArmorModel(Item armorItem, EntityEquipmentSlot armorSlot){
		return null;
	}

	public String getArmorTexture(Item armorItem, EntityEquipmentSlot armorSlot){
		return null;
	}
}
