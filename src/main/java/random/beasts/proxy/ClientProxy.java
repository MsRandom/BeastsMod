package random.beasts.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import random.beasts.client.renderer.entity.RenderBeastsPainting;
import random.beasts.client.renderer.entity.RenderChorusBranchie;
import random.beasts.client.renderer.entity.RenderCoconutBomb;
import random.beasts.client.renderer.entity.RenderCoconutCrab;
import random.beasts.client.renderer.entity.RenderCoralBranchie;
import random.beasts.client.renderer.entity.RenderFallingCoconut;
import random.beasts.client.renderer.entity.RenderGiantGardenEel;
import random.beasts.client.renderer.entity.RenderHermitTurtle;
import random.beasts.client.renderer.entity.RenderLandwhale;
import random.beasts.client.renderer.entity.RenderPufferfishDog;
import random.beasts.client.renderer.entity.RenderRabbitman;
import random.beasts.client.renderer.entity.RenderSkewerShrimp;
import random.beasts.client.renderer.entity.RenderVileEel;
import random.beasts.client.renderer.entity.RenderWhippingBarnacle;
import random.beasts.client.renderer.entity.RenderWoodBranchie;
import random.beasts.client.renderer.tileentity.TileEntityCoconutRenderer;
import random.beasts.common.block.BlockPalmTreeLeaves;
import random.beasts.common.entity.item.EntityBeastsPainting;
import random.beasts.common.entity.item.EntityFallingCoconut;
import random.beasts.common.entity.monster.EntityChorusBranchie;
import random.beasts.common.entity.monster.EntityCoconutCrab;
import random.beasts.common.entity.monster.EntityCoralBranchie;
import random.beasts.common.entity.monster.EntityGiantGardenEel;
import random.beasts.common.entity.monster.EntitySkewerShrimp;
import random.beasts.common.entity.monster.EntityVileEel;
import random.beasts.common.entity.monster.EntityWhippingBarnacle;
import random.beasts.common.entity.monster.EntityWoodBranchie;
import random.beasts.common.entity.passive.EntityHermitTurtle;
import random.beasts.common.entity.passive.EntityLandwhale;
import random.beasts.common.entity.passive.EntityPufferfishDog;
import random.beasts.common.entity.passive.EntityRabbitman;
import random.beasts.common.entity.projectile.EntityCoconutBomb;
import random.beasts.common.tileentity.TileEntityCoconut;
import random.beasts.common.main.BeastsReference;

public class ClientProxy extends CommonProxy {

	public static final TileEntityCoconutRenderer COCONUT_RENDERER = new TileEntityCoconutRenderer();

	@Override
	public void preInit() {
		super.preInit();
		this.registerRenders();
	}

	private void registerRenders() {
		//maybe we should find a better way of doing this.. it's currently really messy
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
