package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.api.main.BeastsReference;
import random.beasts.client.model.ModelAbyssalLegfish;
import random.beasts.client.renderer.entity.layers.LayerAbyssalLegfishSpots;
import random.beasts.common.entity.passive.EntityAbyssalLegfish;
import random.beasts.common.entity.passive.EntityLegfish;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class RenderAbyssalLegfish extends RenderLiving<EntityAbyssalLegfish> {

    private static final Map<Integer, ResourceLocation[]> TEXTURES = new HashMap<>();
    private final ModelAbyssalLegfish[] types;

    public RenderAbyssalLegfish(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelAbyssalLegfish.Type1(), 0.1f);
        this.addLayer(new LayerAbyssalLegfishSpots(this));
        ModelAbyssalLegfish type = (ModelAbyssalLegfish) mainModel;
        types = new ModelAbyssalLegfish[]{type, new ModelAbyssalLegfish.Type2(), new ModelAbyssalLegfish.Type3()};
    }

    @Override
    public void doRender(EntityAbyssalLegfish entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.mainModel = types[entity.getType()];
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityAbyssalLegfish entity) {
        int type = entity.getType();
        ResourceLocation[] textures = TEXTURES.computeIfAbsent(type, k -> {
            ResourceLocation[] typeTextures = new ResourceLocation[EntityLegfish.VARIANTS.get(type)];
            for (int i = 0; i < typeTextures.length; i++)
                typeTextures[i] = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/abyssal_legfish_" + (type + 1) + "_" + (i + 1) + ".png");
            return typeTextures;
        });
        return textures[entity.getVariant()];
    }
}
