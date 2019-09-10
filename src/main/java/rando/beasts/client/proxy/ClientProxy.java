package rando.beasts.client.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import rando.beasts.client.model.ModelHermitHelm;
import rando.beasts.client.model.ModelSpartapodArmor;
import rando.beasts.client.renderer.entity.*;
import rando.beasts.client.renderer.tileentity.TileEntityCoconutRenderer;
import rando.beasts.common.entity.item.EntityBeastsPainting;
import rando.beasts.common.entity.item.EntityFallingCoconut;
import rando.beasts.common.entity.monster.*;
import rando.beasts.common.entity.passive.EntityHermitTurtle;
import rando.beasts.common.entity.passive.EntityLandwhale;
import rando.beasts.common.entity.passive.EntityPufferfishDog;
import rando.beasts.common.entity.passive.EntityRabbitman;
import rando.beasts.common.entity.projectile.EntityCoconutBomb;
import rando.beasts.common.init.BeastsItems;
import rando.beasts.common.proxy.CommonProxy;
import rando.beasts.common.tileentity.TileEntityCoconut;

import java.util.HashMap;
import java.util.Map;

public class ClientProxy extends CommonProxy {

	public static final TileEntityCoconutRenderer COCONUT_RENDERER = new TileEntityCoconutRenderer();
	private static final Map<EntityEquipmentSlot, Map<Item, ModelBiped>> ARMORS = new HashMap<>();

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
		return ARMORS.get(armorSlot).get(armorItem);
	}

	static {
		ARMORS.put(EntityEquipmentSlot.FEET, new HashMap<>());
		ARMORS.put(EntityEquipmentSlot.LEGS, new HashMap<>());
		ARMORS.put(EntityEquipmentSlot.CHEST, new HashMap<>());
		ARMORS.put(EntityEquipmentSlot.HEAD, new HashMap<>());
		ARMORS.get(EntityEquipmentSlot.HEAD).put(BeastsItems.SPARTAPOD_HELMET, new ModelSpartapodArmor());
		ARMORS.get(EntityEquipmentSlot.HEAD).put(BeastsItems.HERMIT_HELM, new ModelHermitHelm());
	}
}
