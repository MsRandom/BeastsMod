package net.msrandom.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.client.model.ModelTupala;
import net.msrandom.beasts.common.entity.passive.EntityTupala;

@SideOnly(Side.CLIENT)
public class RenderTupala extends RenderLiving<EntityTupala> {

    private static final ResourceLocation TUPALA = new ResourceLocation(BeastsReference.ID, "textures/entity/tupala.png");

    public RenderTupala(RenderManager rm) {
        super(rm, new ModelTupala(), 0.4f);
    }

    @Override
    protected void preRenderCallback(EntityTupala entitylivingbaseIn, float partialTickTime) {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
        if (entitylivingbaseIn.isHiding()) GlStateManager.translate(0, 0.45, 0);
    }

    protected ResourceLocation getEntityTexture(EntityTupala entity) {
        return TUPALA;
    }
}