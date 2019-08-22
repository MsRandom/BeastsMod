package rando.beasts.common.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import rando.beasts.common.main.BeastsMod;
import rando.beasts.common.tileentity.TileEntityCoconut;
import rando.beasts.common.utils.BeastsReference;
import rando.beasts.common.world.gen.structure.StructureRabbitVillagePieces;

public class CommonProxy {

	public void preInit(){
		StructureRabbitVillagePieces.register();
		GameRegistry.registerWorldGenerator((random, chunkX, chunkZ, world, chunkGenerator, chunkProvider) -> BeastsMod.RABBIT_VILLAGE.generate(world, random, world.getHeight(new BlockPos((chunkX * 16) + 8, 0, (chunkZ * 16) + 8))), 0);
		GameRegistry.registerTileEntity(TileEntityCoconut.class, new ResourceLocation(BeastsReference.ID, "coconut"));
		ForgeModContainer.logCascadingWorldGeneration = false;
	}

	public ModelBiped getArmorModel(Item armorItem, int id){
		return null;
	}

}
