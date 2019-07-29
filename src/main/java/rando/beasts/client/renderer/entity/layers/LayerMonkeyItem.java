package rando.beasts.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.math.MathHelper;
import rando.beasts.common.entity.EntityMonkeyGremlin;

public class LayerMonkeyItem implements LayerRenderer<EntityMonkeyGremlin> {

    @Override
    public void doRenderLayer(EntityMonkeyGremlin entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, -1.0F);
        if(entity.limbSwingAmount > 0.2) GlStateManager.translate(0.0625, 0, -2*(MathHelper.cos(20 + limbSwing * (0.1062F * 2.4F) + (float)Math.PI) * (-1.3F * 0.6F) * limbSwingAmount * 0.5F + 0.5F) - 0.425F);
        else GlStateManager.translate(0.0625, 0, -2*(MathHelper.cos(20.5f + entity.ticksExisted * (0.1062F * 1.1F) + (float)Math.PI) * (-1.3F * 0.6F) * 0.3F * 0.5F + 0.5F) - 0.425F);
        Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, entity.getStolenItem(), ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, false);
        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
