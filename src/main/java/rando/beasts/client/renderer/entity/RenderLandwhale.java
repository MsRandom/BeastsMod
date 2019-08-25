package rando.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rando.beasts.client.model.ModelLandwhale;
import rando.beasts.common.entity.passive.EntityLandwhale;
import rando.beasts.common.utils.BeastsReference;

@SideOnly(Side.CLIENT)
public class RenderLandwhale extends RenderLiving<EntityLandwhale> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID,
			"textures/entity/landwhale.png");

	@Override
	protected void preRenderCallback(EntityLandwhale e, float partialTickTime) {
		GlStateManager.scale(1.5F, 1.5F, 1.5F);
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		super.preRenderCallback(e, partialTickTime);
	}

	
	public RenderLandwhale(RenderManager rm) {
		super(rm, new ModelLandwhale(), 1.3F);

	}

	protected ResourceLocation getEntityTexture(EntityLandwhale entity) {
		return TEXTURE;
	}
}