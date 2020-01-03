package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelHermitTurtle;
import random.beasts.common.entity.passive.EntityHermitTurtle;
import random.beasts.main.BeastsReference;

import javax.annotation.Nullable;

public class RenderHermitTurtle extends RenderLiving<EntityHermitTurtle> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/hermit_turtle.png");

    public RenderHermitTurtle(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelHermitTurtle(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityHermitTurtle entity) {
        return TEXTURE;
    }
}
