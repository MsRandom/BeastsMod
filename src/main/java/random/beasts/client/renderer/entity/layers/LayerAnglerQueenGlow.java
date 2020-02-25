package random.beasts.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.api.main.BeastsReference;
import random.beasts.client.model.ModelAnglerQueen;
import random.beasts.client.renderer.entity.RenderAnglerQueen;
import random.beasts.common.entity.monster.EntityAnglerQueen;

public class LayerAnglerQueenGlow implements LayerRenderer<EntityAnglerQueen> {
    private static final ResourceLocation ANGLER_QUEEN_GLOW = new ResourceLocation(BeastsReference.ID, "textures/entity/angler_queen/angler_queen_glow.png");
    private final RenderAnglerQueen queenRenderer;

    public LayerAnglerQueenGlow(RenderAnglerQueen queenRendererIn) {
        this.queenRenderer = queenRendererIn;
    }

    public void doRenderLayer(EntityAnglerQueen entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.queenRenderer.bindTexture(ANGLER_QUEEN_GLOW);
        //GlStateManager.enableBlend();
        //GlStateManager.disableAlpha();
        //GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
        int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        ((ModelAnglerQueen)this.queenRenderer.getMainModel()).light.isHidden = false;
        this.queenRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        ((ModelAnglerQueen)this.queenRenderer.getMainModel()).light.isHidden = true;
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        i = entitylivingbaseIn.getBrightnessForRender();
        j = i % 65536;
        k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        GlStateManager.depthMask(true);
        //GlStateManager.disableBlend();
        //GlStateManager.enableAlpha();
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}