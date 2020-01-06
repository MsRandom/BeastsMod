package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.api.main.BeastsReference;
import random.beasts.client.model.ModelGiantGardenEel;
import random.beasts.common.entity.monster.EntityGiantGardenEel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderGiantGardenEel extends RenderLiving<EntityGiantGardenEel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/giant_garden_eel.png");

    public RenderGiantGardenEel(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelGiantGardenEel(), 0.3f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityGiantGardenEel entity) {
        return TEXTURE;
    }
}
