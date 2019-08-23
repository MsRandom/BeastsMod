package rando.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import rando.beasts.client.model.ModelSkewerShrimp;
import rando.beasts.common.entity.monster.EntitySkewerShrimp;
import rando.beasts.common.utils.BeastsReference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderSkewerShrimp extends RenderLiving<EntitySkewerShrimp> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/skewer_shrimp.png");

    public RenderSkewerShrimp(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelSkewerShrimp(), 0.1f);
    }

    @Override
    protected void preRenderCallback(EntitySkewerShrimp entitylivingbaseIn, float partialTickTime) {
//        GlStateManager.translate(-0.2, 0, 0);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntitySkewerShrimp entity) {
        return TEXTURE;
    }
}
