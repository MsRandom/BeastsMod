package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelGiantGardenEel;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.monster.EntityGiantGardenEel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderGiantGardenEel extends MobRenderer<EntityGiantGardenEel, ModelGiantGardenEel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/giant_garden_eel.png");

    public RenderGiantGardenEel(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelGiantGardenEel(), 0.3f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityGiantGardenEel entity) {
        return TEXTURE;
    }
}
