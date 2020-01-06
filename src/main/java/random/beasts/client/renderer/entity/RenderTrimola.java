package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.api.main.BeastsReference;
import random.beasts.client.model.ModelTrimola;
import random.beasts.common.entity.monster.EntityTrimola;

import javax.annotation.Nullable;

public class RenderTrimola extends RenderLiving<EntityTrimola> {
    private static final ResourceLocation[] NORMAL_TEXTURES = new ResourceLocation[]{new ResourceLocation(BeastsReference.ID, "textures/entity/trimola/texture_1"), new ResourceLocation(BeastsReference.ID, "textures/entity/trimola/texture_2")};
    private static final ResourceLocation[] SADDLE_TEXTURES = new ResourceLocation[]{new ResourceLocation(BeastsReference.ID, "textures/entity/trimola/saddle_1"), new ResourceLocation(BeastsReference.ID, "textures/entity/trimola/saddle_2")};

    public RenderTrimola(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelTrimola(), 0.2f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityTrimola entity) {
        return entity.getSaddle().isEmpty() ? NORMAL_TEXTURES[entity.getVariant()] : SADDLE_TEXTURES[entity.getVariant()];
    }
}
