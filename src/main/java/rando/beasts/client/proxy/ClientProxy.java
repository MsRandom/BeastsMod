package rando.beasts.client.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import rando.beasts.client.model.ModelSpartapodArmor;
import rando.beasts.client.renderer.entity.*;
import rando.beasts.client.renderer.tileentity.TileEntityCoconutRenderer;
import rando.beasts.common.entity.*;
import rando.beasts.common.entity.projectile.EntityCoconutBomb;
import rando.beasts.common.init.BeastsItems;
import rando.beasts.common.item.BeastsItem;
import rando.beasts.common.proxy.CommonProxy;
import rando.beasts.common.tileentity.TileEntityCoconut;

public class ClientProxy extends CommonProxy {

	ModelSpartapodArmor SPARTAPOD = new ModelSpartapodArmor();

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
		RenderingRegistry.registerEntityRenderingHandler(EntityCoconutBomb.class, new IRenderFactory<EntityCoconutBomb>() {
			@Override
			public Render<? super EntityCoconutBomb> createRenderFor(RenderManager manager) {
				return new RenderSnowball(manager, BeastsItems.COCONADE, Minecraft.getMinecraft().getRenderItem());
			}
		});
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoconut.class, new TileEntityCoconutRenderer());
	}

	@Override
	public ModelBiped getArmorModel(Item armorItem, int id) {
		if(armorItem == BeastsItems.SPARTAPOD_HELMET) {
			switch (id) {
				case 0:
					return SPARTAPOD;
				case 1:
					return SPARTAPOD;
			}
		}
		return null;
	}
}
