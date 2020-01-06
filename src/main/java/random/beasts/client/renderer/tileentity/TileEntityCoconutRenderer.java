package random.beasts.client.renderer.tileentity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import random.beasts.client.model.ModelCoconut;
import random.beasts.client.renderer.entity.RenderCoconutCrab;
import random.beasts.common.tileentity.TileEntityCoconut;

public class TileEntityCoconutRenderer extends TileEntitySpecialRenderer<TileEntityCoconut> {

    private final ModelCoconut model = new ModelCoconut();

    @Override
    public void render(TileEntityCoconut te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (te != null) super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.translate(-0.501F, -1.401F, 0.501F);
        GlStateManager.enableAlpha();
        GlStateManager.disableLighting();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableColorMaterial();
        bindTexture(RenderCoconutCrab.TEXTURE);
        model.render(null, 0, 0, -1, 0, 0, 0.0625F);
        GlStateManager.disableOutlineMode();
        GlStateManager.disableColorMaterial();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }
}
