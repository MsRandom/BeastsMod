package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.client.model.ModelAnglerPup;
import random.beasts.common.entity.passive.EntityAnglerPup;

@OnlyIn(Dist.CLIENT)
public class RenderAnglerPup extends MobRenderer<EntityAnglerPup> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/angler_pup.png");

    public RenderAnglerPup(EntityRendererManager rm) {
        super(rm, new ModelAnglerPup(), 0.3f);
    }

    protected ResourceLocation getEntityTexture(EntityAnglerPup entity) {
        return TEXTURE;
    }
}