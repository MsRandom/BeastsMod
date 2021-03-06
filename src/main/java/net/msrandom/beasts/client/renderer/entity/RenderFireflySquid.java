package net.msrandom.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.client.model.ModelFireflySquid;
import net.msrandom.beasts.client.renderer.entity.layers.LayerFireflySquidSpots;
import net.msrandom.beasts.common.entity.passive.EntityFireflySquid;

import javax.annotation.Nullable;

public class RenderFireflySquid extends RenderLiving<EntityFireflySquid> {

    private static final ResourceLocation[] TEXTURES = new ResourceLocation[4];

    static {
        for (int i = 0; i < TEXTURES.length; i++)
            TEXTURES[i] = new ResourceLocation(BeastsReference.ID, "textures/entity/firefly_squid/firefly_squid_" + (i + 1) + ".png");
    }

    public RenderFireflySquid(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelFireflySquid(), 0.4f);
        this.addLayer(new LayerFireflySquidSpots(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityFireflySquid entity) {
        return TEXTURES[entity.getVariant()];
    }
}
