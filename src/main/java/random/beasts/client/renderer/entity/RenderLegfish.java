package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelLegfish;
import random.beasts.common.entity.passive.EntityLegfish;
import random.beasts.common.main.BeastsReference;

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
        ModelLegfish model = types[entity.getType()];
        if (mainModel != model) this.mainModel = model;
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityLegfish entity) {
        int type = entity.getType();
        ResourceLocation[] typeTextures = TEXTURES.computeIfAbsent(type, k -> {
            ResourceLocation[] temp = new ResourceLocation[EntityLegfish.VARIANTS.get(type)];
            for (int i = 0; i < temp.length; i++)
                temp[i] = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/type_" + (type + 1) + "_" + (i + 1) + ".png");
            return temp;
        });
        return typeTextures[entity.getVariant()];
    }
}
