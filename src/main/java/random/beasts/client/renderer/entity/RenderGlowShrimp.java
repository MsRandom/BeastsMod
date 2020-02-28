package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.client.model.ModelGlowShrimp;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.passive.EntityGlowShrimp;

@OnlyIn(Dist.CLIENT)
public class RenderGlowShrimp extends MobRenderer<EntityGlowShrimp, ModelGlowShrimp> {

    private static final ResourceLocation SHRIMP = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/glow_shrimp.png");

    public RenderGlowShrimp(EntityRendererManager rm) {
        super(rm, new ModelGlowShrimp(), 0.3f);
    }

    protected ResourceLocation getEntityTexture(EntityGlowShrimp entity) {
        return SHRIMP;
    }
}