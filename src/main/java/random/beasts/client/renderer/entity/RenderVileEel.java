package random.beasts.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.client.model.ModelVileEel;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.monster.EntityVileEel;

@OnlyIn(Dist.CLIENT)
public class RenderVileEel extends MobRenderer<EntityVileEel, ModelVileEel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/vile_eel.png");

    public RenderVileEel(EntityRendererManager rm) {
        super(rm, new ModelVileEel(), 1.0F);
    }

    @Override
    protected void preRenderCallback(EntityVileEel e, float partialTickTime) {
        GlStateManager.scalef(1.5F, 1.5F, 1.5F);
        GlStateManager.translatef(0.0F, 0.0F, 0.0F);
        super.preRenderCallback(e, partialTickTime);
    }

    protected ResourceLocation getEntityTexture(EntityVileEel entity) {
        return TEXTURE;
    }
}