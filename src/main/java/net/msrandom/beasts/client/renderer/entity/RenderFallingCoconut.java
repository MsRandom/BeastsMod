package net.msrandom.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.msrandom.beasts.client.renderer.RenderCoconut;
import net.msrandom.beasts.common.entity.item.EntityFallingCoconut;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderFallingCoconut extends Render<EntityFallingCoconut> {

    public RenderFallingCoconut(RenderManager rendermanagerIn) {
        super(rendermanagerIn);
    }

    @Override
    public void doRender(EntityFallingCoconut entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        RenderCoconut.render(x, y, z, this::bindTexture, this.renderOutlines, this.getTeamColor(entity));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityFallingCoconut entity) {
        return RenderCoconut.TEXTURE;
    }
}
