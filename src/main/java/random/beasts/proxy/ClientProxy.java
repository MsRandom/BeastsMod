package random.beasts.proxy;

import net.minecraft.block.*;
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
import random.beasts.api.block.BeastsSlab;
import random.beasts.api.item.IHandleMeta;
import random.beasts.api.main.BeastsReference;
import random.beasts.api.main.BeastsRegistries;
import random.beasts.client.renderer.entity.*;
import random.beasts.client.renderer.tileentity.TileEntityCoconutRenderer;
import random.beasts.common.block.BlockAnemoneMouth;
import random.beasts.common.block.BlockPalmTreeLeaves;
import random.beasts.common.entity.item.EntityBeastsPainting;
import random.beasts.common.entity.item.EntityFallingCoconut;
import random.beasts.common.entity.item.EntityThrownCoconut;
import random.beasts.common.entity.monster.*;
import random.beasts.common.entity.passive.*;
import random.beasts.common.entity.projectile.EntityCoconutBomb;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.tileentity.TileEntityCoconut;

import java.util.Objects;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		super.preInit();
		ClientRegistry.registerKeyBinding(EntityTrimola.ATTACK);
		this.registerEntityRenders();
	}

	private void registerEntityRenders() {
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
		RenderingRegistry.registerEntityRenderingHandler(EntityAnemoneCrawler.class, RenderAnemoneCrawler::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityLegfish.class, RenderLegfish::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityScallop.class, RenderScallop::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownCoconut.class, RenderThrownCoconut::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTrimola.class, RenderTrimola::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySlimeSlug.class, RenderSlimeSlug::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityButterflyFish.class, RenderButterflyFish::new);
		registerTileEntityRenders();
	}

	public void registerEventRenders() {
		for (Item item : BeastsRegistries.ITEMS) {
			if (item instanceof IHandleMeta) {
				IHandleMeta metaItem = (IHandleMeta) item;
				for (int i = 0; i < metaItem.getDamage(); i++)
					ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(BeastsReference.ID, Objects.requireNonNull(item.getRegistryName()).getResourcePath() + "_" + metaItem.handleMeta(i)), "inventory"));
			} else
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
		}
		for (Block block : BeastsRegistries.BLOCKS) {
			Item item = Item.getItemFromBlock(block);
			if (item != Items.AIR)
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(block.getRegistryName()), "inventory"));
		}
		ModelLoader.setCustomStateMapper(BeastsBlocks.JELLY_WOOD_DOOR, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
		ModelLoader.setCustomStateMapper(BeastsBlocks.JELLY_WOOD_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
		ModelLoader.setCustomStateMapper(BeastsBlocks.PALM_DOOR, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
		ModelLoader.setCustomStateMapper(BeastsBlocks.PALM_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
		ModelLoader.setCustomStateMapper(BeastsBlocks.PALM_LEAVES, new StateMap.Builder().ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
		ModelLoader.setCustomStateMapper(BeastsBlocks.JELLY_LEAVES, new StateMap.Builder().ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
		ModelLoader.setCustomStateMapper(BeastsBlocks.ANEMONE_MOUTH, new StateMap.Builder().ignore(BlockAnemoneMouth.FED).build());
		ModelLoader.setCustomStateMapper(BeastsBlocks.JELLY_WOOD_SLAB.full, new StateMap.Builder().ignore(BlockSlab.HALF, BeastsSlab.VARIANT).build());
		ModelLoader.setCustomStateMapper(BeastsBlocks.PALM_SLAB.full, new StateMap.Builder().ignore(BlockSlab.HALF, BeastsSlab.VARIANT).build());
	}

	private void registerTileEntityRenders() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoconut.class, new TileEntityCoconutRenderer());
	}

	@Override
	public ModelBiped getArmorModel(Item armorItem, EntityEquipmentSlot armorSlot) {
		return ArmorData.MODELS.getFrom(armorItem, armorSlot);
	}

	@Override
	public String getArmorTexture(Item armorItem, EntityEquipmentSlot armorSlot) {
		String texture = ArmorData.TEXTURES.getFrom(armorItem, armorSlot);
		return texture == null ? null : BeastsReference.ID + ":textures/models/armor/" + texture + ".png";
	}

	public void setGraphicsLevel(BlockPalmTreeLeaves block, boolean enabled) {
		block.setGraphicsLevel(enabled);
	}
}
