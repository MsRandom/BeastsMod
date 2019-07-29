package rando.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rando.beasts.client.renderer.entity.layers.LayerMonkeyItem;
import rando.beasts.common.utils.BeastsReference;
import rando.beasts.client.model.ModelMonkeyGremlin;
import rando.beasts.common.entity.EntityMonkeyGremlin;

@SideOnly(Side.CLIENT)
public class RenderMonkeyGremlin extends RenderLiving<EntityMonkeyGremlin> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/monkey_gremlin.png");

	public RenderMonkeyGremlin(RenderManager a) {
		super(a, new ModelMonkeyGremlin(), 0.5F);
		this.addLayer(new LayerMonkeyItem());
	}

	protected ResourceLocation getEntityTexture(EntityMonkeyGremlin entity) {
		return TEXTURE;
	}

}