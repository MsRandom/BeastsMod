package rando.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import rando.beasts.client.model.ModelHermitTurtle;
import rando.beasts.client.model.ModelWhippingBarnacle;
import rando.beasts.common.entity.monster.EntityWhippingBarnacle;
import rando.beasts.common.entity.passive.EntityHermitTurtle;
import rando.beasts.common.utils.BeastsReference;

import javax.annotation.Nullable;

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
