package random.beasts.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.*;
import random.beasts.common.entity.passive.EntityAnemoneCrawler;
import random.beasts.common.entity.passive.EntityLegfish;
import random.beasts.common.main.BeastsReference;

import javax.annotation.Nullable;

public class RenderLegfish extends RenderLiving<EntityLegfish> {

    private ResourceLocation LEGFISH_ORANGE = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/legfish_orange.png");
    private ResourceLocation LEGFISH_YELLOW = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/legfish_yellow.png");

    private ResourceLocation LEGFISH2_BLACK = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/legfish2_black.png");
    private ResourceLocation LEGFISH2_BLUE = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/legfish2_blue.png");
    private ResourceLocation LEGFISH2_GREEN = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/legfish2_green.png");
    private ResourceLocation LEGFISH2_RED = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/legfish2_red.png");
    private ResourceLocation LEGFISH2_YELLOW = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/legfish2_yellow.png");

    private ResourceLocation LEGFISH3_BLUE = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/legfish3_blue.png");
    private ResourceLocation LEGFISH3_PINK = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/legfish3_pink.png");
    private ResourceLocation LEGFISH3_RED = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/legfish3_red.png");
    private ResourceLocation LEGFISH3_YELLOW = new ResourceLocation(BeastsReference.ID, "textures/entity/legfish/legfish3_yellow.png");

    public RenderLegfish(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelNone(), 0.1f);
        this.addLayer(new LayerModelVariant(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityLegfish entity) {
        switch(entity.getVariant()){
            case 0:
            default:
                return LEGFISH_ORANGE;
            case 1:
                return LEGFISH_YELLOW;
            case 2:
                return LEGFISH2_BLACK;
            case 3:
                return LEGFISH2_BLUE;
            case 4:
                return LEGFISH2_GREEN;
            case 5:
                return LEGFISH2_RED;
            case 6:
                return LEGFISH2_YELLOW;
            case 7:
                return LEGFISH3_BLUE;
            case 8:
                return LEGFISH3_PINK;
            case 9:
                return LEGFISH3_RED;
            case 10:
                return LEGFISH3_YELLOW;
        }
    }

    public class LayerModelVariant implements LayerRenderer<EntityLegfish>
    {
        private final RenderLegfish pigRenderer;
        //im mad at cody so im leaving the word pig here
        private final ModelLegfish pigModel = new ModelLegfish();
        private final ModelLegfish2 pigModel2 = new ModelLegfish2();
        private final ModelLegfish3 pigModel3 = new ModelLegfish3();

        public LayerModelVariant(RenderLegfish pigRendererIn)
        {
            this.pigRenderer = pigRendererIn;
        }

        public void doRenderLayer(EntityLegfish entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
        {
            ModelBase model = null;
            if(entitylivingbaseIn.getVariant() <= 6 && entitylivingbaseIn.getVariant() >= 2)
                model = pigModel2;
            else if(entitylivingbaseIn.getVariant() >= 7 && entitylivingbaseIn.getVariant() <= 10)
                model = pigModel3;
            else
                model = pigModel;

            this.pigRenderer.bindTexture(pigRenderer.getEntityTexture(entitylivingbaseIn));
            model.setModelAttributes(this.pigRenderer.getMainModel());
            model.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }

        public boolean shouldCombineTextures()
        {
            return true;
        }
    }

}
