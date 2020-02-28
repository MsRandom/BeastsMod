package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelHermitTurtle;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.passive.EntityHermitTurtle;

import javax.annotation.Nullable;

public class RenderHermitTurtle extends MobRenderer<EntityHermitTurtle, ModelHermitTurtle> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/hermit_turtle.png");

    public RenderHermitTurtle(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ModelHermitTurtle(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityHermitTurtle entity) {
        return TEXTURE;
    }
}
