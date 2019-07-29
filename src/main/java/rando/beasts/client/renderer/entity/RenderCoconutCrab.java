package rando.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import rando.beasts.client.model.ModelCoconut;
import rando.beasts.common.entity.EntityCoconutCrab;
import rando.beasts.common.utils.BeastsReference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderCoconutCrab extends RenderLiving<EntityCoconutCrab> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/coconut_crab.png");

    public RenderCoconutCrab(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCoconut(), 0.1f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityCoconutCrab entity) {
        return TEXTURE;
    }
}
