package rando.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import rando.beasts.client.model.ModelCoconut;
import rando.beasts.client.model.ModelGiantGardenEel;
import rando.beasts.common.entity.EntityGiantGardenEel;
import rando.beasts.common.utils.BeastsReference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderGiantGardenEel extends RenderLiving<EntityGiantGardenEel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/giant_garden_eel.png");

    public RenderGiantGardenEel(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelGiantGardenEel(), 0.3f);
    }

    @Override
    protected void preRenderCallback(EntityGiantGardenEel entitylivingbaseIn, float partialTickTime) {
//        GlStateManager.translate(-0.2, 0, 0);
        if(entitylivingbaseIn.slam){
            GlStateManager.rotate(90, 1, 0,0);
        }
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityGiantGardenEel entity) {
        return TEXTURE;
    }
}
