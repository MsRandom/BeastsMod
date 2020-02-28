package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.client.model.ModelTupala;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.passive.EntityTupala;

@OnlyIn(Dist.CLIENT)
public class RenderTupala extends MobRenderer<EntityTupala, ModelTupala> {

    private static final ResourceLocation TUPALA = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/tupala.png");

    public RenderTupala(EntityRendererManager rm) {
        super(rm, new ModelTupala(), 0.4f);
    }

    protected ResourceLocation getEntityTexture(EntityTupala entity) {
        return TUPALA;
    }
}