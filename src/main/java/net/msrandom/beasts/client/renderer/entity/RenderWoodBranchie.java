package net.msrandom.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.common.entity.monster.EntityWoodBranchie;

import java.util.HashMap;
import java.util.Map;

public class RenderWoodBranchie extends RenderBranchieBase<EntityWoodBranchie> {
    private static final Map<String, ResourceLocation> TEXTURES = new HashMap<>();

    public RenderWoodBranchie(RenderManager rm) {
        super(rm);
    }

    protected ResourceLocation getEntityTexture(EntityWoodBranchie entity) {
        return TEXTURES.putIfAbsent(entity.getVariant().getName(), new ResourceLocation(BeastsReference.ID, "textures/entity/branchie/wood/" + entity.getVariant().getName() + ".png"));
    }
}
