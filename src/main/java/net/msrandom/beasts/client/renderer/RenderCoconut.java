package net.msrandom.beasts.client.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.client.model.ModelCoconut;

import java.util.function.Consumer;

public class RenderCoconut {
    public static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/coconut_crab.png");
    private static final ModelCoconut MODEL = new ModelCoconut();

    public static void render(double x, double y, double z, Consumer<ResourceLocation> bindTexture, boolean renderOutlines, int outline) {
        bindTexture.accept(TEXTURE);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        if (renderOutlines) {
            GlStateManager.enableColorMaterial();
            if (outline != -1) GlStateManager.enableOutlineMode(outline);
        }
        //GlStateManager.disableCull();
        GlStateManager.translate((float) x, (float) y, (float) z);
        //GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.translate(-0.501F, -1.401F, 0.501F);
        //GlStateManager.enableAlpha();
        //GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        //GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        MODEL.render(null, 0, 0, -1, 0, 0, 0.0625F);
        if (renderOutlines) {
            GlStateManager.disableOutlineMode();
            if (outline != -1) GlStateManager.disableColorMaterial();
        }
        //GlStateManager.disableRescaleNormal();
        //GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        //GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        //GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
