package net.msrandom.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.client.model.ModelWhippingBarnacle;
import net.msrandom.beasts.common.entity.monster.EntityWhippingBarnacle;

import javax.annotation.Nullable;

public class RenderWhippingBarnacle extends RenderLiving<EntityWhippingBarnacle> {

    private static final ResourceLocation BLUE = new ResourceLocation(BeastsReference.ID, "textures/entity/whipping_barnacle/blue.png");
    private static final ResourceLocation GREEN = new ResourceLocation(BeastsReference.ID, "textures/entity/whipping_barnacle/green.png");

    public RenderWhippingBarnacle(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelWhippingBarnacle(), 0);
    }

    @Override
    protected void preRenderCallback(EntityWhippingBarnacle entitylivingbaseIn, float partialTickTime) {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
        EnumFacing facing = entitylivingbaseIn.getFacing();
        switch (facing.getAxis()) {
            case X:
                //GlStateManager.rotate(90, 0, 0, facing.getAxisDirection().getOffset());
                break;
            case Z:
                GlStateManager.rotate(90, facing.getAxisDirection().getOffset(), 0, 0);
                break;
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityWhippingBarnacle entity) {
        return entity.getColor() == 1 ? GREEN : BLUE;
    }
}
