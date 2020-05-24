package net.msrandom.beasts.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.client.renderer.entity.RenderPufferfishDog;
import net.msrandom.beasts.common.entity.passive.EntityPufferfishDog;

public class LayerGlasses implements LayerRenderer<EntityPufferfishDog> {
    private static final ResourceLocation BUFFORD = new ResourceLocation(BeastsReference.ID, "textures/entity/pufferfish_dog/bufford.png");
    private static final ResourceLocation BUFFORD_INFLATED = new ResourceLocation(BeastsReference.ID, "textures/entity/pufferfish_dog/bufford_inflated.png");
    private final RenderPufferfishDog render;

    public LayerGlasses(RenderPufferfishDog render) {
        this.render = render;
    }

    public void doRenderLayer(EntityPufferfishDog entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entity.hasCustomName() && entity.getCustomNameTag().equalsIgnoreCase("Bufford") && !entity.isInvisible()) {
            this.render.bindTexture(entity.isInflated() ? BUFFORD_INFLATED : BUFFORD);
            this.render.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}
