package rando.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import rando.beasts.client.model.ModelWhippingBarnacle;
import rando.beasts.common.entity.monster.EntityWhippingBarnacle;
import rando.beasts.common.utils.BeastsReference;

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
        GlStateManager.rotate(90, facing.getAxis() == EnumFacing.Axis.X ? facing.getAxisDirection().getOffset() : 0, 0, facing.getAxis() == EnumFacing.Axis.Z ? facing.getAxisDirection().getOffset() : 0);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityWhippingBarnacle entity) {
        return entity.getColor() == 1 ? GREEN : BLUE;
    }
}
