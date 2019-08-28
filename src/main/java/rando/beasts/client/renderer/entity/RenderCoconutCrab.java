package rando.beasts.client.renderer.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rando.beasts.client.model.ModelCoconut;
import rando.beasts.common.entity.monster.EntityCoconutCrab;
import rando.beasts.common.utils.BeastsReference;

public class RenderCoconutCrab extends RenderLiving<EntityCoconutCrab> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/coconut_crab.png");

    public RenderCoconutCrab(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCoconut(), 0.1f);
        this.addLayer(new LayerCrabItem());
    }

    @Override
    protected void preRenderCallback(EntityCoconutCrab entitylivingbaseIn, float partialTickTime) {
        GlStateManager.translate(-0.2, 0, 0);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityCoconutCrab entity) {
        return TEXTURE;
    }

    @SideOnly(Side.CLIENT)
    public static class LayerCrabItem implements LayerRenderer<EntityCoconutCrab> {
        private EntityItem item = new EntityItem(Minecraft.getMinecraft().world);

        public void doRenderLayer(EntityCoconutCrab entity, float f, float f1, float f2, float f3, float f4, float f5, float f6) {
            if(!entity.getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
                if(!entity.isInvisible() && entity.getHeldItem(EnumHand.MAIN_HAND) != ItemStack.EMPTY) {
                    item.hoverStart = 0;
                    item.setItem(entity.getHeldItem(EnumHand.MAIN_HAND));
                    GlStateManager.pushMatrix();
                    GlStateManager.rotate(180, 0, 0, 1);
                    GlStateManager.rotate(90, 0, 1, 0);
                    GlStateManager.translate(0.6F, -1.5F, 0.25f);
                    GlStateManager.scale(0.7F, 0.7F, 0.7F);
                    Minecraft.getMinecraft().getRenderManager().renderEntity(item, 0, 0, 0, 0, 0, false);
                    GlStateManager.popMatrix();
                }
            }
        }

        @Override
        public boolean shouldCombineTextures() {
            return false;
        }
    }
}
