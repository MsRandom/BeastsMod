package random.beasts.proxy;

import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSlab;
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
import random.beasts.client.renderer.entity.RenderAnemoneCrawler;
import random.beasts.client.renderer.entity.RenderBeastsPainting;
import random.beasts.client.renderer.entity.RenderChorusBranchie;
import random.beasts.client.renderer.entity.RenderCoconutBomb;
import random.beasts.client.renderer.entity.RenderCoconutCrab;
import random.beasts.client.renderer.entity.RenderCoralBranchie;
import random.beasts.client.renderer.entity.RenderFallingCoconut;
import random.beasts.client.renderer.entity.RenderGiantGardenEel;
import random.beasts.client.renderer.entity.RenderHermitTurtle;
import random.beasts.client.renderer.entity.RenderLandwhale;
import random.beasts.client.renderer.entity.RenderLegfish;
import random.beasts.client.renderer.entity.RenderPufferfishDog;
import random.beasts.client.renderer.entity.RenderRabbitman;
import random.beasts.client.renderer.entity.RenderScallop;
import random.beasts.client.renderer.entity.RenderSkewerShrimp;
import random.beasts.client.renderer.entity.RenderSlimeSlug;
import random.beasts.client.renderer.entity.RenderThrownCoconut;
import random.beasts.client.renderer.entity.RenderTrimola;
import random.beasts.client.renderer.entity.RenderVileEel;
import random.beasts.client.renderer.entity.RenderWhippingBarnacle;
import random.beasts.client.renderer.entity.RenderWoodBranchie;
import random.beasts.client.renderer.tileentity.TileEntityCoconutRenderer;
import random.beasts.common.block.BlockAnemoneMouth;
import random.beasts.common.block.BlockPalmTreeLeaves;
import random.beasts.common.entity.item.EntityBeastsPainting;
import random.beasts.common.entity.item.EntityFallingCoconut;
import random.beasts.common.entity.item.EntityThrownCoconut;
import random.beasts.common.entity.monster.EntityChorusBranchie;
import random.beasts.common.entity.monster.EntityCoconutCrab;
import random.beasts.common.entity.monster.EntityCoralBranchie;
import random.beasts.common.entity.monster.EntityGiantGardenEel;
import random.beasts.common.entity.monster.EntityScallop;
import random.beasts.common.entity.monster.EntitySkewerShrimp;
import random.beasts.common.entity.monster.EntityTrimola;
import random.beasts.common.entity.monster.EntityVileEel;
import random.beasts.common.entity.monster.EntityWhippingBarnacle;
import random.beasts.common.entity.monster.EntityWoodBranchie;
import random.beasts.common.entity.passive.EntityAnemoneCrawler;
import random.beasts.common.entity.passive.EntityHermitTurtle;
import random.beasts.common.entity.passive.EntityLandwhale;
import random.beasts.common.entity.passive.EntityLegfish;
import random.beasts.common.entity.passive.EntityPufferfishDog;
import random.beasts.common.entity.passive.EntityRabbitman;
import random.beasts.common.entity.passive.EntitySlimeSlug;
import random.beasts.common.entity.projectile.EntityCoconutBomb;
import random.beasts.common.init.BeastsBlocks;
import random.beasts.common.tileentity.TileEntityCoconut;

public class ClientProxy extends CommonProxy {

	public static final TileEntityCoconutRenderer COCONUT_RENDERER = new TileEntityCoconutRenderer();

	@Override
	public void preInit() {
		super.preInit();
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoconut.class, COCONUT_RENDERER);
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
