package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.client.model.ModelLandwhale;
import random.beasts.common.entity.passive.EntityLandwhale;

@OnlyIn(Dist.CLIENT)
public class RenderLandwhale extends MobRenderer<EntityLandwhale> {
    private static final ResourceLocation NORMAL = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/landwhale/normal.png");
    private static final ResourceLocation SHEARED = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/landwhale/sheared.png");
    private static final ResourceLocation SADDLE = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/landwhale/saddle.png");

    public RenderLandwhale(EntityRendererManager rm) {
        super(rm, new ModelLandwhale(), 1.0F);

    }

    @Override
    protected void preRenderCallback(EntityLandwhale e, float partialTickTime) {
        GlStateManager.scale(1.5F, 1.5F, 1.5F);
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        super.preRenderCallback(e, partialTickTime);
    }

    protected ResourceLocation getEntityTexture(EntityLandwhale entity) {
        return !entity.inventory.getStackInSlot(0).isEmpty() ? SADDLE : entity.getSheared() ? SHEARED : NORMAL;
    }
}