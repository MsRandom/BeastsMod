package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.monster.EntityCoralBranchie;

import java.util.HashMap;
import java.util.Map;

public class RenderCoralBranchie extends RenderBranchieBase<EntityCoralBranchie> {
    private static final Map<String, ResourceLocation> TEXTURES = new HashMap<>();

    public RenderCoralBranchie(EntityRendererManager rm) {
        super(rm);
    }

    protected ResourceLocation getEntityTexture(EntityCoralBranchie entity) {
        return TEXTURES.putIfAbsent(entity.getVariant().getName(), new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/branchie/coral/" + entity.getVariant().getName() + ".png"));
    }
}
