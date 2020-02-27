package random.beasts.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelCoconut;
import random.beasts.common.BeastsMod;

import java.util.function.Consumer;

public class RenderCoconut {
    public static final ResourceLocation TEXTURE = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/coconut_crab.png");
    private static final ModelCoconut MODEL = new ModelCoconut();

    public static void render(double x, double y, double z, Consumer<ResourceLocation> bindTexture, boolean renderOutlines, int outline) {
        bindTexture.accept(TEXTURE);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        if (renderOutlines) {
            GlStateManager.enableColorMaterial();
            if (outline != -1) GlStateManager.setupSolidRenderingTextureCombine(outline);
        }
        //GlStateManager.disableCull();
        GlStateManager.translatef((float) x, (float) y, (float) z);
        //GlStateManager.enableRescaleNormal();
        GlStateManager.scalef(-1.0F, -1.0F, 1.0F);
        GlStateManager.translatef(-0.501F, -1.401F, 0.501F);
        //GlStateManager.enableAlpha();
        //GlStateManager.setActiveTexture(GLX.GL_TEXTURE1);
        //GlStateManager.setActiveTexture(GLX.GL_TEXTURE0);
        MODEL.render(null, 0, 0, -1, 0, 0, 0.0625F);
        if (renderOutlines) {
            GlStateManager.tearDownSolidRenderingTextureCombine();
            if (outline != -1) GlStateManager.disableColorMaterial();
        }
        //GlStateManager.disableRescaleNormal();
        //GlStateManager.setActiveTexture(GLX.GL_TEXTURE1);
        //GlStateManager.setActiveTexture(GLX.GL_TEXTURE0);
        //GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
