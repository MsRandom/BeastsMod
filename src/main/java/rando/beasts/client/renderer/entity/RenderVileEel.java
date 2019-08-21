package rando.beasts.client.renderer.entity;

import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rando.beasts.client.model.ModelVileEel;
import rando.beasts.common.entity.EntityVileEel;
import rando.beasts.common.utils.BeastsReference;

@SideOnly(Side.CLIENT)
public class RenderVileEel extends RenderLiving<EntityVileEel> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID,
			"textures/entity/vileeel.png");

	@Override
	protected void preRenderCallback(EntityVileEel e, float partialTickTime) {
		GlStateManager.scale(1.5F, 1.5F, 1.5F);
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		super.preRenderCallback(e, partialTickTime);
	}

	public RenderVileEel(RenderManager rm) {
		super(rm, new ModelVileEel(), 1.0F);

	}

	protected ResourceLocation getEntityTexture(EntityVileEel entity) {
		return TEXTURE;
	}
}