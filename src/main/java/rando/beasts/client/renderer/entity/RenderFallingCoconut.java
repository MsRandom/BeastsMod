package rando.beasts.client.renderer.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import rando.beasts.client.proxy.ClientProxy;
import rando.beasts.common.entity.item.EntityFallingCoconut;

public class RenderFallingCoconut extends Render<EntityFallingCoconut> {

    public RenderFallingCoconut(RenderManager rendermanagerIn) {
        super(rendermanagerIn);
    }

    @Override
    public void doRender(EntityFallingCoconut entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.translate(0.501F, 0, -0.501F);
        ClientProxy.COCONUT_RENDERER.render(null, x, y, z, partialTicks, -1, 1);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityFallingCoconut entity) {
        return RenderCoconutCrab.TEXTURE;
    }
}
