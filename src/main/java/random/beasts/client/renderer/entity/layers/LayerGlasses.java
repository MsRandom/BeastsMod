package random.beasts.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelPufferFishDog;
import random.beasts.client.renderer.entity.RenderPufferfishDog;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.passive.EntityPufferfishDog;

public class LayerGlasses extends LayerRenderer<EntityPufferfishDog, ModelPufferFishDog> {
    private static final ResourceLocation BUFFORD = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/pufferfish_dog/bufford.png");
    private static final ResourceLocation BUFFORD_INFLATED = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/pufferfish_dog/bufford_inflated.png");

    public LayerGlasses(RenderPufferfishDog render) {
        super(render);
    }

    public void render(EntityPufferfishDog entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entity.hasCustomName() && entity.getCustomName().getString().equalsIgnoreCase("Bufford") && !entity.isInvisible()) {
            this.bindTexture(entity.isInflated() ? BUFFORD_INFLATED : BUFFORD);
            this.getEntityModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}
