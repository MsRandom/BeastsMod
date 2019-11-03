package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelAnemoneCrawler;
import random.beasts.common.entity.passive.EntityAnemoneCrawler;
import random.beasts.common.main.BeastsReference;

import javax.annotation.Nullable;

public class RenderAnemoneCrawler extends RenderLiving<EntityAnemoneCrawler> {

    private ResourceLocation[] textures;

    public RenderAnemoneCrawler(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelAnemoneCrawler(), 0.1f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityAnemoneCrawler entity) {
        if(textures == null) {
            textures = new ResourceLocation[3];
            for (int i = 0; i < textures.length; i++) textures[i] = new ResourceLocation(BeastsReference.ID, "textures/entity/anemone_crawler/texture_" + (i + 1) + ".png");
        }
        return textures[entity.getVariant()];
    }
}
