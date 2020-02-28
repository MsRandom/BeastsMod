package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelSkewerShrimp;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.monster.EntitySkewerShrimp;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderSkewerShrimp extends MobRenderer<EntitySkewerShrimp, ModelSkewerShrimp> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/skewer_shrimp.png");

    public RenderSkewerShrimp(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelSkewerShrimp(), 0.2f);
    }

    @Override
    protected void preRenderCallback(EntitySkewerShrimp entitylivingbaseIn, float partialTickTime) {
//        GlStateManager.translatef(-0.2, 0, 0);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntitySkewerShrimp entity) {
        return TEXTURE;
    }
}
