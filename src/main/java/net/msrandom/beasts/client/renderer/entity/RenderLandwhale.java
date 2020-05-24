package net.msrandom.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.client.model.ModelLandwhale;
import net.msrandom.beasts.common.entity.passive.EntityLandwhale;

@SideOnly(Side.CLIENT)
public class RenderLandwhale extends RenderLiving<EntityLandwhale> {
    private static final ResourceLocation NORMAL = new ResourceLocation(BeastsReference.ID, "textures/entity/landwhale/normal.png");
    private static final ResourceLocation SHEARED = new ResourceLocation(BeastsReference.ID, "textures/entity/landwhale/sheared.png");
    private static final ResourceLocation SADDLE = new ResourceLocation(BeastsReference.ID, "textures/entity/landwhale/saddle.png");

    public RenderLandwhale(RenderManager rm) {
        super(rm, new ModelLandwhale(), 1.0F);

    }

    @Override
    protected void preRenderCallback(EntityLandwhale e, float partialTickTime) {
        GlStateManager.scale(1.5F, 1.5F, 1.5F);
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        super.preRenderCallback(e, partialTickTime);
    }

    protected ResourceLocation getEntityTexture(EntityLandwhale entity) {
        return !entity.inventory.getStackInSlot(0).isEmpty() ? SADDLE : entity.getSheared() ? SHEARED : NORMAL;
    }
}