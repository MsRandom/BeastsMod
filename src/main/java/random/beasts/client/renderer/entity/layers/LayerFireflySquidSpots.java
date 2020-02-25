package random.beasts.client.renderer.entity.layers;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelFireflySquid;
import random.beasts.client.renderer.entity.RenderFireflySquid;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.passive.EntityFireflySquid;

public class LayerFireflySquidSpots extends LayerRenderer<EntityFireflySquid, ModelFireflySquid> {
    private static final ResourceLocation SPOTS_BLUE = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/firefly_squid/firefly_squid_blue_spots.png");
    private static final ResourceLocation SPOTS_YELLOW = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/firefly_squid/firefly_squid_yellow_spots.png");

    public LayerFireflySquidSpots(RenderFireflySquid entityRendererIn) {
        super(entityRendererIn);
    }

    public void render(EntityFireflySquid entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entitylivingbaseIn.getVariant() == 0 || entitylivingbaseIn.getVariant() == 2)
            this.bindTexture(SPOTS_YELLOW);
        else
            this.bindTexture(SPOTS_BLUE);

        GlStateManager.enableBlend();
        GlStateManager.disableAlphaTest();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 61680.0F, 0.0F);
        GlStateManager.enableLighting();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().gameRenderer.setupFogColor(true);
        this.getEntityModel().finLeft.isHidden = true;
        this.getEntityModel().finRight.isHidden = true;
        this.getEntityModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.getEntityModel().finLeft.isHidden = false;
        this.getEntityModel().finRight.isHidden = false;
        Minecraft.getInstance().gameRenderer.setupFogColor(false);
        this.func_215334_a(entitylivingbaseIn);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableAlphaTest();
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
