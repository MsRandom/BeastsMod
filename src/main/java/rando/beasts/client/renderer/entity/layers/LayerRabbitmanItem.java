package rando.beasts.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import rando.beasts.common.entity.passive.EntityRabbitman;

public class LayerRabbitmanItem implements LayerRenderer<EntityRabbitman> {


    @Override
    public void doRenderLayer(EntityRabbitman entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(entity.getVariant() == 5 && !entity.hasTarget) {
            GlStateManager.pushMatrix();
            if (entity.isSneaking()) GlStateManager.translate(0.0F, 0.2F, 0.0F);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 1.0F);
            GlStateManager.rotate(-5.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-25.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(20F, 1.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.1, 0.15, 0.35);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, entity.getHeldItemMainhand(), ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, false);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
