package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelAnemoneCrawler;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.passive.EntityAnemoneCrawler;

import javax.annotation.Nullable;

public class RenderAnemoneCrawler extends MobRenderer<EntityAnemoneCrawler, ModelAnemoneCrawler> {

    private static final ResourceLocation[] TEXTURES = new ResourceLocation[4];

    static {
        for (int i = 0; i < TEXTURES.length; i++)
            TEXTURES[i] = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/anemone_crawler/texture_" + (i + 1) + ".png");
    }

    public RenderAnemoneCrawler(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelAnemoneCrawler(), 0.4f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityAnemoneCrawler entity) {
        return TEXTURES[entity.getVariant()];
    }
}
