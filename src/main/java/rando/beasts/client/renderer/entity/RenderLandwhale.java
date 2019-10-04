package rando.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rando.beasts.client.model.ModelLandwhale;
import rando.beasts.common.entity.passive.EntityLandwhale;
import rando.beasts.common.utils.BeastsReference;

@SideOnly(Side.CLIENT)
public class RenderLandwhale extends RenderLiving<EntityLandwhale> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/landwhale.png");
	private static final ResourceLocation TEXTURE_NO_CORAL = new ResourceLocation(BeastsReference.ID, "textures/entity/landwhale_nocoral.png");
	private static final ResourceLocation SADDLE = new ResourceLocation(BeastsReference.ID, "textures/entity/landwhale_saddle.png");

	@Override
	protected void preRenderCallback(EntityLandwhale e, float partialTickTime) {
		GlStateManager.scale(1.5F, 1.5F, 1.5F);
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		super.preRenderCallback(e, partialTickTime);
	}

	
	public RenderLandwhale(RenderManager rm) {
		super(rm, new ModelLandwhale(), 1.0F);

	}

	protected ResourceLocation getEntityTexture(EntityLandwhale entity) {
		return !entity.getSaddle().isEmpty() ? SADDLE : entity.getSheared() ? TEXTURE_NO_CORAL : TEXTURE;
	}
}