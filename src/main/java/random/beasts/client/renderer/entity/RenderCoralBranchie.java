package random.beasts.client.renderer.entity;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.common.entity.monster.EntityCoralBranchie;
import random.beasts.common.main.BeastsReference;

public class RenderCoralBranchie extends RenderBranchieBase<EntityCoralBranchie> {
    private static final Map<String, ResourceLocation> TEXTURES = new HashMap<>();

    public RenderCoralBranchie(RenderManager rm) {
        super(rm);
    }

    protected ResourceLocation getEntityTexture(EntityCoralBranchie entity) {
        return TEXTURES.putIfAbsent(entity.getVariant().getName(), new ResourceLocation(BeastsReference.ID, "textures/entity/branchie/coral/" + entity.getVariant().getName() + ".png"));
    }
}
