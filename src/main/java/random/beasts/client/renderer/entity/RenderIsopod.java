package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.client.model.ModelIsopod;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.monster.EntityIsopod;

@OnlyIn(Dist.CLIENT)
public class RenderIsopod extends MobRenderer<EntityIsopod> {

    private static final ResourceLocation SPARTAPOD = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/spartapod.png");
    private static final ResourceLocation ATHAPOD = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/athapod.png");

    public RenderIsopod(EntityRendererManager rm) {
        super(rm, new ModelIsopod(), 0.4f);
    }

    protected ResourceLocation getEntityTexture(EntityIsopod entity) {
        return SPARTAPOD;
    }
}