package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelScallop;
import random.beasts.common.entity.monster.EntityScallop;

import javax.annotation.Nullable;

public class RenderScallop extends MobRenderer<EntityScallop> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/scallop.png");

    public RenderScallop(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelScallop(), 0.2f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityScallop entity) {
        return TEXTURE;
    }
}
