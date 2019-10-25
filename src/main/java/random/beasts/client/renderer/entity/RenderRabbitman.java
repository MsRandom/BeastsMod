package random.beasts.client.renderer.entity;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelRabbitman;
import random.beasts.client.renderer.entity.layers.LayerRabbitmanItem;
import random.beasts.common.entity.passive.EntityRabbitman;
import random.beasts.common.main.BeastsReference;

public class RenderRabbitman extends RenderLiving<EntityRabbitman> {

    private static final ResourceLocation[] TEXTURES = new ResourceLocation[EntityRabbitman.VARIANTS];

    public RenderRabbitman(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelRabbitman(), 0.5F);
        this.addLayer(new LayerRabbitmanItem());
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityRabbitman entity) {
        return TEXTURES[entity.getVariant()];
    }

    static {
        for (int i = 0; i < TEXTURES.length; i++) TEXTURES[i] = new ResourceLocation(BeastsReference.ID, "textures/entity/rabbitman/texture_" + (i+1) + ".png");
    }
}
