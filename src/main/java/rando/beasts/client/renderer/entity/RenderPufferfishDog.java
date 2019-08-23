package rando.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import rando.beasts.common.utils.BeastsReference;
import rando.beasts.client.model.ModelPufferFishDog;
import rando.beasts.client.renderer.entity.layers.LayerCollar;
import rando.beasts.common.entity.passive.EntityPufferfishDog;

import javax.annotation.Nullable;

public class RenderPufferfishDog extends RenderLiving<EntityPufferfishDog> {

    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(BeastsReference.ID, "textures/entity/pufferfish_dog/normal.png");
    private static final ResourceLocation TEXTURE_INFLATED = new ResourceLocation(BeastsReference.ID, "textures/entity/pufferfish_dog/inflated.png");

    public RenderPufferfishDog(RenderManager manager) {
        super(manager, new ModelPufferFishDog(), 0.3f);
        this.addLayer(new LayerCollar(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityPufferfishDog entity) {
        return entity.getInflated()?TEXTURE_INFLATED:TEXTURE_NORMAL;
    }
}

