/*package random.beasts.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelWhippingBarnacle;
import random.beasts.common.entity.monster.EntityWhippingBarnacle;

import javax.annotation.Nullable;

public class RenderWhippingBarnacle extends MobRenderer<EntityWhippingBarnacle, ModelWhippingBarnacle> {

    private static final ResourceLocation BLUE = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/whipping_barnacle/blue.png");
    private static final ResourceLocation GREEN = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/whipping_barnacle/green.png");

    public RenderWhippingBarnacle(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ModelWhippingBarnacle(), 0);
    }

    @Override
    protected void preRenderCallback(EntityWhippingBarnacle entitylivingbaseIn, float partialTickTime) {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
        Direction facing = entitylivingbaseIn.getFacing();
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
*/