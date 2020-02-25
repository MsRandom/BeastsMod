package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelFireflySquid;
import random.beasts.client.renderer.entity.layers.LayerFireflySquidSpots;
import random.beasts.common.entity.passive.EntityFireflySquid;

import javax.annotation.Nullable;

public class RenderFireflySquid extends MobRenderer<EntityFireflySquid> {

    private static final ResourceLocation[] TEXTURES = new ResourceLocation[4];

    static {
        for (int i = 0; i < TEXTURES.length; i++)
            TEXTURES[i] = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/firefly_squid/firefly_squid_" + (i + 1) + ".png");
    }

    public RenderFireflySquid(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelFireflySquid(), 0.4f);
        this.addLayer(new LayerFireflySquidSpots(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityFireflySquid entity) {
        return TEXTURES[entity.getVariant()];
    }
}
