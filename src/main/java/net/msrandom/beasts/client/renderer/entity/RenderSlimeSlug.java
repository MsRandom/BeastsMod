package net.msrandom.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.client.model.ModelSlimeSlug;
import net.msrandom.beasts.common.entity.passive.EntitySlimeSlug;

import javax.annotation.Nullable;

public class RenderSlimeSlug extends RenderLiving<EntitySlimeSlug> {

    private static final ResourceLocation[] TEXTURES = new ResourceLocation[4];

    static {
        for (int i = 0; i < TEXTURES.length; i++)
            TEXTURES[i] = new ResourceLocation(BeastsReference.ID, "textures/entity/slime_slug/texture_" + (i + 1) + ".png");
    }

    public RenderSlimeSlug(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelSlimeSlug(), 0.4f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySlimeSlug entity) {
        return TEXTURES[entity.getVariant()];
    }
}
