package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelRabbitman;
import random.beasts.client.renderer.entity.layers.LayerRabbitmanItem;
import random.beasts.common.entity.passive.EntityRabbitman;

import javax.annotation.Nullable;

public class RenderRabbitman extends MobRenderer<EntityRabbitman> {

    private static final ResourceLocation[] TEXTURES = new ResourceLocation[EntityRabbitman.VARIANTS];

    static {
        for (int i = 0; i < TEXTURES.length; i++)
            TEXTURES[i] = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/rabbitman/texture_" + (i + 1) + ".png");
    }

    public RenderRabbitman(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelRabbitman(), 0.5F);
        this.addLayer(new LayerRabbitmanItem());
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityRabbitman entity) {
        return TEXTURES[entity.getVariant()];
    }
}
