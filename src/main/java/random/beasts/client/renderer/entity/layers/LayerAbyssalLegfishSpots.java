package random.beasts.client.renderer.entity.layers;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.api.main.BeastsReference;
import random.beasts.client.model.ModelAbyssalLegfish;
import random.beasts.client.renderer.entity.RenderAbyssalLegfish;
import random.beasts.common.entity.passive.EntityAbyssalLegfish;
import random.beasts.common.entity.passive.EntityLegfish;

import java.util.HashMap;
import java.util.Map;

public class LayerAbyssalLegfishSpots implements LayerRenderer<EntityAbyssalLegfish> {
    private static final Map<Integer, ResourceLocation[]> TEXTURES = new HashMap<>();
    private final RenderAbyssalLegfish renderer;
    private final ModelAbyssalLegfish[] types;

    public LayerAbyssalLegfishSpots(RenderAbyssalLegfish rendererIn) {
        ModelAbyssalLegfish type = (ModelAbyssalLegfish) rendererIn.getMainModel();
        types = new ModelAbyssalLegfish[]{type, new ModelAbyssalLegfish.Type2(), new ModelAbyssalLegfish.Type3()};
        this.renderer = rendererIn;
    }

    public void doRenderLayer(EntityAbyssalLegfish entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.renderer.bindTexture(this.getEntityTexture(entitylivingbaseIn));
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
        int i = 61680;
        int j = 61680;
        int k = 0;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        for (ModelRenderer part : this.renderer.getMainModel().boxList) {
            if (part != ((ModelAbyssalLegfish) this.renderer.getMainModel()).body)
                part.isHidden = true;
        }
        this.renderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        for (ModelRenderer part : this.renderer.getMainModel().boxList) {
            part.isHidden = false;
        }
        this.renderer.setLightmap(entitylivingbaseIn);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
    }

    protected ResourceLocation getEntityTexture(EntityAbyssalLegfish entity) {
        int type = entity.getType();
        ResourceLocation[] textures = TEXTURES.computeIfAbsent(type, k -> {
            ResourceLocation[] typeTextures = new ResourceLocation[EntityLegfish.VARIANTS.get(type)];
            for (int i = 0; i < typeTextures.length; i++)
                typeTextures[i] = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/spots_" + (type + 1) + "_" + (i + 1) + ".png");
            return typeTextures;
        });
        return textures[entity.getVariant()];
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}