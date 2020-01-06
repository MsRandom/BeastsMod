package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.api.main.BeastsReference;
import random.beasts.client.model.ModelScallop;
import random.beasts.common.entity.monster.EntityScallop;

import javax.annotation.Nullable;

public class RenderScallop extends RenderLiving<EntityScallop> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/scallop.png");

    public RenderScallop(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelScallop(), 0.2f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityScallop entity) {
        return TEXTURE;
    }
}
