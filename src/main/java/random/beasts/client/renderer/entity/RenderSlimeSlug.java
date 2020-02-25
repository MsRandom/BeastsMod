package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelSlimeSlug;
import random.beasts.common.entity.passive.EntitySlimeSlug;

import javax.annotation.Nullable;

public class RenderSlimeSlug extends MobRenderer<EntitySlimeSlug> {

    private static final ResourceLocation[] TEXTURES = new ResourceLocation[4];

    static {
        for (int i = 0; i < TEXTURES.length; i++)
            TEXTURES[i] = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/slime_slug/texture_" + (i + 1) + ".png");
    }

    public RenderSlimeSlug(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelSlimeSlug(), 0.4f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySlimeSlug entity) {
        return TEXTURES[entity.getVariant()];
    }
}
