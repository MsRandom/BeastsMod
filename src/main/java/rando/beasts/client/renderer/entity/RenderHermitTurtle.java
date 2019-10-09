package rando.beasts.client.renderer.entity;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import rando.beasts.client.model.ModelHermitTurtle;
import rando.beasts.common.entity.passive.EntityHermitTurtle;
import rando.beasts.common.utils.BeastsReference;

public class RenderHermitTurtle extends RenderLiving<EntityHermitTurtle> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/hermit_turtle.png");

    public RenderHermitTurtle(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelHermitTurtle(), 0.1f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityHermitTurtle entity) {
        return TEXTURE;
    }
}
