package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.api.entity.BeastsBranchie;
import random.beasts.client.model.ModelBranchie;

@OnlyIn(Dist.CLIENT)
public abstract class RenderBranchieBase<T extends BeastsBranchie> extends MobRenderer<T> {
    public RenderBranchieBase(EntityRendererManager rm) {
        super(rm, new ModelBranchie(), 0.1F);
    }

    @Override
    protected void preRenderCallback(T e, float partialTickTime) {
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        super.preRenderCallback(e, partialTickTime);
    }
}
