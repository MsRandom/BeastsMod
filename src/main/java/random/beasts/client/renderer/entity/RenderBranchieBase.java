package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import random.beasts.client.model.ModelBranchie;
import random.beasts.entity.BeastsBranchie;

@SideOnly(Side.CLIENT)
public abstract class RenderBranchieBase<T extends BeastsBranchie> extends RenderLiving<T> {
    public RenderBranchieBase(RenderManager rm) {
        super(rm, new ModelBranchie(), 0.1F);
    }

    @Override
    protected void preRenderCallback(T e, float partialTickTime) {
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        super.preRenderCallback(e, partialTickTime);
    }
}
