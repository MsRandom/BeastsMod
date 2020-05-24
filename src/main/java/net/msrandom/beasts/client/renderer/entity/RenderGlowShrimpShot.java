package net.msrandom.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.client.model.ModelGlowShrimpProjectile;
import net.msrandom.beasts.common.entity.projectile.EntityGlowShrimpShot;

import javax.annotation.Nullable;

public class RenderGlowShrimpShot extends Render<EntityGlowShrimpShot> {

    private final ResourceLocation[] textures = new ResourceLocation[]{new ResourceLocation(BeastsReference.ID, "textures/entity/glow_shrimp/glow_shrimp_projectile0.png"),
            new ResourceLocation(BeastsReference.ID, "textures/entity/glow_shrimp/glow_shrimp_projectile1.png"), new ResourceLocation(BeastsReference.ID, "textures/entity/glow_shrimp/glow_shrimp_projectile2.png"),
            new ResourceLocation(BeastsReference.ID, "textures/entity/glow_shrimp/glow_shrimp_projectile3.png")};
    private final ModelGlowShrimpProjectile model = new ModelGlowShrimpProjectile();

    public RenderGlowShrimpShot(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityGlowShrimpShot entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 180, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        this.bindEntityTexture(entity);
        this.model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.075F);
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityGlowShrimpShot entity) {
        return textures[(int) entity.world.getWorldTime() % 4];
    }
}
