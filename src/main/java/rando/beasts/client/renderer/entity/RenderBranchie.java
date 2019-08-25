package rando.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rando.beasts.client.model.ModelBranchie;
import rando.beasts.common.entity.monster.EntityBranchie;
import rando.beasts.common.utils.BeastsReference;

@SideOnly(Side.CLIENT)
public class RenderBranchie extends RenderLiving<EntityBranchie> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/branchie_yellow.png");

	public RenderBranchie(RenderManager rm) {
		super(rm, new ModelBranchie(), 0.1F);
	}

	@Override
	protected void preRenderCallback(EntityBranchie e, float partialTickTime) {
		GlStateManager.scale(1.0F, 1.0F, 1.0F);
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		super.preRenderCallback(e, partialTickTime);
	}

	protected ResourceLocation getEntityTexture(EntityBranchie entity) {
		return TEXTURE;
	}
}