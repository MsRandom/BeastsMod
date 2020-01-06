package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.api.main.BeastsReference;
import random.beasts.client.model.ModelLegfish;
import random.beasts.common.entity.passive.EntityLegfish;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class RenderLegfish extends RenderLiving<EntityLegfish> {

    private static final Map<Integer, ResourceLocation[]> TEXTURES = new HashMap<>();
    private final ModelLegfish[] types;

    public RenderLegfish(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelLegfish.Type1(), 0.1f);
        ModelLegfish type = (ModelLegfish) mainModel;
        types = new ModelLegfish[]{type, new ModelLegfish.Type2(), new ModelLegfish.Type3()};
    }

    @Override
    public void doRender(EntityLegfish entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.mainModel = types[entity.getType()];
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityLegfish entity) {
        int type = entity.getType();
        ResourceLocation[] textures = TEXTURES.computeIfAbsent(type, k -> {
            ResourceLocation[] typeTextures = new ResourceLocation[EntityLegfish.VARIANTS.get(type)];
            for (int i = 0; i < typeTextures.length; i++)
                typeTextures[i] = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/type_" + (type + 1) + "_" + (i + 1) + ".png");
            return typeTextures;
        });
        return textures[entity.getVariant()];
    }
}
