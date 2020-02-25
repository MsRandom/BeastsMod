package random.beasts.client.renderer.entity.layers;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.client.model.ModelPufferFishDog;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.passive.EntityPufferfishDog;

@OnlyIn(Dist.CLIENT)
public class LayerCollar extends LayerRenderer<EntityPufferfishDog, ModelPufferFishDog> {
    private static final ResourceLocation COLLAR = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/pufferfish_dog/collar.png");

    public LayerCollar(IEntityRenderer<EntityPufferfishDog, ModelPufferFishDog> entityRendererIn) {
        super(entityRendererIn);
    }

    public void render(EntityPufferfishDog entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entity.isTamed() && !entity.isInvisible()) {
            this.bindTexture(COLLAR);
            float[] colors = entity.getCollarColor().getColorComponentValues();
            GlStateManager.color3f(colors[0], colors[1], colors[2]);
            this.getEntityModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}
