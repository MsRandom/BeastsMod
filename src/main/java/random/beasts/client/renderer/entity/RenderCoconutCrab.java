package random.beasts.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.client.model.ModelCoconut;
import random.beasts.client.renderer.RenderCoconut;
import random.beasts.common.entity.monster.EntityCoconutCrab;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderCoconutCrab extends MobRenderer<EntityCoconutCrab> {

    public RenderCoconutCrab(EntityRendererManager rendermanagerIn) {
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
        return RenderCoconut.TEXTURE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class LayerCrabItem implements LayerRenderer<EntityCoconutCrab> {
        private ItemEntity item = new ItemEntity(Minecraft.getInstance().world);

        public void doRenderLayer(EntityCoconutCrab entity, float f, float f1, float f2, float f3, float f4, float f5, float f6) {
            if (!entity.getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
                if (!entity.isInvisible() && entity.getHeldItem(EnumHand.MAIN_HAND) != ItemStack.EMPTY) {
                    item.hoverStart = 0;
                    item.setItem(entity.getHeldItem(EnumHand.MAIN_HAND));
                    GlStateManager.pushMatrix();
                    GlStateManager.rotate(180, 0, 0, 1);
                    GlStateManager.rotate(90, 0, 1, 0);
                    GlStateManager.translate(0.6F, -1.5F, 0.25f);
                    GlStateManager.scale(0.7F, 0.7F, 0.7F);
                    Minecraft.getInstance().getEntityRendererManager().renderEntity(item, 0, 0, 0, 0, 0, false);
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
