package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.common.entity.monster.EntityWoodBranchie;

import java.util.HashMap;
import java.util.Map;

public class RenderWoodBranchie extends RenderBranchieBase<EntityWoodBranchie> {
    private static final Map<String, ResourceLocation> TEXTURES = new HashMap<>();

    public RenderWoodBranchie(EntityRendererManager rm) {
        super(rm);
    }

    protected ResourceLocation getEntityTexture(EntityWoodBranchie entity) {
        return TEXTURES.putIfAbsent(entity.getVariant().getName(), new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/branchie/wood/" + entity.getVariant().getName() + ".png"));
    }
}
