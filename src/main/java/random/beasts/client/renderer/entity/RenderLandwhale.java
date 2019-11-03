package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import random.beasts.client.model.ModelLandwhale;
import random.beasts.common.entity.passive.EntityLandwhale;
import random.beasts.common.main.BeastsReference;

@SideOnly(Side.CLIENT)
public class RenderLandwhale extends RenderLiving<EntityLandwhale> {
	private static final ResourceLocation NORMAL = new ResourceLocation(BeastsReference.ID, "textures/entity/landwhale/normal.png");
	private static final ResourceLocation SHEARED = new ResourceLocation(BeastsReference.ID, "textures/entity/landwhale/sheared.png");
	private static final ResourceLocation SADDLE = new ResourceLocation(BeastsReference.ID, "textures/entity/landwhale/saddle.png");

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
		return !entity.getSaddle().isEmpty() ? SADDLE : entity.getSheared() ? SHEARED : NORMAL;
	}
}