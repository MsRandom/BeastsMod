package net.msrandom.beasts.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.client.renderer.entity.RenderAnglerPup;
import net.msrandom.beasts.common.entity.passive.EntityAnglerPup;

@SideOnly(Side.CLIENT)
public class LayerAnglerPupCollar implements LayerRenderer<EntityAnglerPup> {
    private static final ResourceLocation COLLAR = new ResourceLocation(BeastsReference.ID, "textures/entity/collar.png");
    private final RenderAnglerPup render;

    public LayerAnglerPupCollar(RenderAnglerPup render) {
        this.render = render;
    }

    public void doRenderLayer(EntityAnglerPup entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entity.isTamed() && !entity.isInvisible()) {
            this.render.bindTexture(COLLAR);
            float[] colors = entity.getCollarColor().getColorComponentValues();
            GlStateManager.color(colors[0], colors[1], colors[2]);
            this.render.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}
