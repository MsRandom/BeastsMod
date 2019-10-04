package rando.beasts.client.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import rando.beasts.client.renderer.entity.*;
import rando.beasts.client.renderer.tileentity.TileEntityCoconutRenderer;
import rando.beasts.common.block.BlockPalmTreeLeaves;
import rando.beasts.common.entity.item.EntityBeastsPainting;
import rando.beasts.common.entity.item.EntityFallingCoconut;
import rando.beasts.common.entity.monster.*;
import rando.beasts.common.entity.passive.EntityHermitTurtle;
import rando.beasts.common.entity.passive.EntityLandwhale;
import rando.beasts.common.entity.passive.EntityPufferfishDog;
import rando.beasts.common.entity.passive.EntityRabbitman;
import rando.beasts.common.entity.projectile.EntityCoconutBomb;
import rando.beasts.common.init.BeastsBlocks;
import rando.beasts.common.init.BeastsItems;
import rando.beasts.common.item.IHandleMeta;
import rando.beasts.common.proxy.CommonProxy;
import rando.beasts.common.tileentity.TileEntityCoconut;
import rando.beasts.common.utils.BeastsReference;

import java.util.Objects;

public class ClientProxy extends CommonProxy {

	public static final TileEntityCoconutRenderer COCONUT_RENDERER = new TileEntityCoconutRenderer();

	@Override
	public void preInit() {
		super.preInit();
		this.registerRenders();
	}

	private void registerRenders() {
		RenderingRegistry.registerEntityRenderingHandler(EntityCoconutCrab.class, RenderCoconutCrab::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityPufferfishDog.class, RenderPufferfishDog::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRabbitman.class, RenderRabbitman::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySkewerShrimp.class, RenderSkewerShrimp::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGiantGardenEel.class, RenderGiantGardenEel::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityVileEel.class, RenderVileEel::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityLandwhale.class, RenderLandwhale::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCoconutBomb.class, RenderCoconutBomb::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCoralBranchie.class, RenderCoralBranchie::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityChorusBranchie.class, RenderChorusBranchie::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityWoodBranchie.class, RenderWoodBranchie::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBeastsPainting.class, RenderBeastsPainting::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFallingCoconut.class, RenderFallingCoconut::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityWhippingBarnacle.class, RenderWhippingBarnacle::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityHermitTurtle.class, RenderHermitTurtle::new);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoconut.class, COCONUT_RENDERER);
	}

	@Override
	public ModelBiped getArmorModel(Item armorItem, EntityEquipmentSlot armorSlot) {
		return ArmorData.MODELS.get(armorSlot).get(armorItem);
	}

	@Override
	public String getArmorTexture(Item armorItem, EntityEquipmentSlot armorSlot) {
		String texture = ArmorData.TEXTURES.get(armorSlot).get(armorItem);
		return texture == null ? null : BeastsReference.ID + ":textures/models/armor/" + texture + ".png";
	}
	
	public void setGraphicsLevel(BlockPalmTreeLeaves block, boolean enabled) {
		block.setGraphicsLevel(enabled);
	}
}
