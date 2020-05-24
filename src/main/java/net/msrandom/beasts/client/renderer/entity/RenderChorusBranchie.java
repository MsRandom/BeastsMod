package net.msrandom.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.common.entity.monster.EntityChorusBranchie;

public class RenderChorusBranchie extends RenderBranchieBase<EntityChorusBranchie> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsReference.ID, "textures/entity/branchie/chorus.png");

    public RenderChorusBranchie(RenderManager rm) {
        super(rm);
    }

    protected ResourceLocation getEntityTexture(EntityChorusBranchie entity) {
        return TEXTURE;
    }
}
