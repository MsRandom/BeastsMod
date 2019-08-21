package rando.beasts.client.proxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import rando.beasts.client.renderer.entity.RenderCoconutCrab;
import rando.beasts.client.renderer.entity.RenderLandwhale;
import rando.beasts.client.renderer.entity.RenderPufferfishDog;
import rando.beasts.client.renderer.entity.RenderRabbitman;
import rando.beasts.client.renderer.entity.RenderVileEel;
import rando.beasts.client.renderer.tileentity.TileEntityCoconutRenderer;
import rando.beasts.common.entity.EntityCoconutCrab;
import rando.beasts.common.entity.EntityLandwhale;
import rando.beasts.common.entity.EntityPufferfishDog;
import rando.beasts.common.entity.EntityRabbitman;
import rando.beasts.common.entity.EntityVileEel;
import rando.beasts.common.proxy.CommonProxy;
import rando.beasts.common.tileentity.TileEntityCoconut;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		super.preInit();
		this.registerRenders();
	}

	private void registerRenders() {
		RenderingRegistry.registerEntityRenderingHandler(EntityCoconutCrab.class, RenderCoconutCrab::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityPufferfishDog.class, RenderPufferfishDog::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRabbitman.class, RenderRabbitman::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityVileEel.class, RenderVileEel::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityLandwhale.class, RenderLandwhale::new);

		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoconut.class, new TileEntityCoconutRenderer());
	}
}
