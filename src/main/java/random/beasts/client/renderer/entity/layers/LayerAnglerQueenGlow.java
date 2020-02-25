package random.beasts.client.renderer.entity.layers;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelAnglerQueen;
import random.beasts.client.renderer.entity.RenderAnglerQueen;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.monster.EntityAnglerQueen;

public class LayerAnglerQueenGlow extends LayerRenderer<EntityAnglerQueen, ModelAnglerQueen> {
    private static final ResourceLocation ANGLER_QUEEN_GLOW = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/angler_queen/angler_queen_glow.png");

    public LayerAnglerQueenGlow(RenderAnglerQueen entityRendererIn) {
        super(entityRendererIn);
    }

    public void render(EntityAnglerQueen entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.bindTexture(ANGLER_QUEEN_GLOW);
        //GlStateManager.enableBlend();
        //GlStateManager.disableAlpha();
        //GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
        int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float) j, (float) k);
        GlStateManager.enableLighting();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().gameRenderer.setupFogColor(true);
        this.getEntityModel().light.isHidden = false;
        this.getEntityModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.getEntityModel().light.isHidden = true;
        Minecraft.getInstance().gameRenderer.setupFogColor(false);
        i = entitylivingbaseIn.getBrightnessForRender();
        j = i % 65536;
        k = i / 65536;
        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float) j, (float) k);
        GlStateManager.depthMask(true);
        //GlStateManager.disableBlend();
        //GlStateManager.enableAlpha();
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}