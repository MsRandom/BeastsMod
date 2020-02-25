package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelTrimola;
import random.beasts.common.entity.monster.EntityTrimola;

import javax.annotation.Nullable;

public class RenderTrimola extends MobRenderer<EntityTrimola> {
    private static final ResourceLocation[] NORMAL_TEXTURES = new ResourceLocation[]{new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/trimola/texture_1.png"), new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/trimola/texture_2.png")};
    private static final ResourceLocation[] SADDLE_TEXTURES = new ResourceLocation[]{new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/trimola/saddle_1.png"), new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/trimola/saddle_2.png")};

    public RenderTrimola(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelTrimola(), 0.6f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityTrimola entity) {
        return entity.getSaddle().isEmpty() ? NORMAL_TEXTURES[entity.getVariant()] : SADDLE_TEXTURES[entity.getVariant()];
    }
}
