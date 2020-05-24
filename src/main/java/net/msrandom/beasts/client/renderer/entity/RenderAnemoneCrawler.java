package net.msrandom.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.client.model.ModelAnemoneCrawler;
import net.msrandom.beasts.common.entity.passive.EntityAnemoneCrawler;

import javax.annotation.Nullable;

public class RenderAnemoneCrawler extends RenderLiving<EntityAnemoneCrawler> {

    private static final ResourceLocation[] TEXTURES = new ResourceLocation[4];

    static {
        for (int i = 0; i < TEXTURES.length; i++)
            TEXTURES[i] = new ResourceLocation(BeastsReference.ID, "textures/entity/anemone_crawler/texture_" + (i + 1) + ".png");
    }

    public RenderAnemoneCrawler(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelAnemoneCrawler(), 0.4f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityAnemoneCrawler entity) {
        return TEXTURES[entity.getVariant()];
    }
}
