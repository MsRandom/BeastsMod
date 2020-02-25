package random.beasts.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelFireflySquid;
import random.beasts.client.renderer.entity.RenderFireflySquid;
import random.beasts.common.entity.passive.EntityFireflySquid;

public class LayerFireflySquidSpots<T extends EntityFireflySquid> implements LayerRenderer<T> {
    private static final ResourceLocation SPOTS_BLUE = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/firefly_squid/firefly_squid_blue_spots.png");
    private static final ResourceLocation SPOTS_YELLOW = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/firefly_squid/firefly_squid_yellow_spots.png");
    private final RenderFireflySquid squidRenderer;

    public LayerFireflySquidSpots(RenderFireflySquid squidRendererIn) {
        this.squidRenderer = squidRendererIn;
    }

    public void doRenderLayer(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entitylivingbaseIn instanceof EntityFireflySquid) {
    		if(((EntityFireflySquid)entitylivingbaseIn).getVariant()==0 || ((EntityFireflySquid)entitylivingbaseIn).getVariant()==2)
    	        this.squidRenderer.bindTexture(SPOTS_YELLOW);
    		else
    	        this.squidRenderer.bindTexture(SPOTS_BLUE);
    		
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
            int i = 61680;
            int j = 61680;
            int k = 0;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
            GlStateManager.enableLighting();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getInstance().entityRenderer.setupFogColor(true);
            ((ModelFireflySquid) this.squidRenderer.getMainModel()).finLeft.isHidden = true;
            ((ModelFireflySquid)this.squidRenderer.getMainModel()).finRight.isHidden = true;
            this.squidRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            ((ModelFireflySquid)this.squidRenderer.getMainModel()).finLeft.isHidden = false;
            ((ModelFireflySquid) this.squidRenderer.getMainModel()).finRight.isHidden = false;
            Minecraft.getInstance().entityRenderer.setupFogColor(false);
            this.squidRenderer.setLightmap(entitylivingbaseIn);
            GlStateManager.depthMask(true);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
	}
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}