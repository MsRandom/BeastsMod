package rando.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import rando.beasts.common.entity.monster.EntityGiantGardenEel;

public class ModelGiantGardenEel extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer body1;
    public ModelRenderer body2;
    public ModelRenderer body3;
    public ModelRenderer head;

    public ModelGiantGardenEel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body2 = new ModelRenderer(this, 0, 18);
        this.body2.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.body2.addBox(-3.0F, -12.0F, -3.0F, 6, 12, 6, 0.0F);
        this.body3 = new ModelRenderer(this, 24, 18);
        this.body3.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.body3.addBox(-3.0F, -12.0F, -3.0F, 6, 12, 6, 0.0F);
        this.head = new ModelRenderer(this, 0, 36);
        this.head.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.head.addBox(-3.5F, -3.5F, -12.0F, 7, 7, 12, 0.0F);
        this.body1 = new ModelRenderer(this, 24, 0);
        this.body1.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.body1.addBox(-3.0F, -12.0F, -3.0F, 6, 12, 6, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 26.0F, 0.0F);
        this.body.addBox(-3.0F, -12.0F, -3.0F, 6, 12, 6, 0.0F);
        this.body1.addChild(this.body2);
        this.body2.addChild(this.body3);
        this.body3.addChild(this.head);
        this.body.addChild(this.body1);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        EntityGiantGardenEel gardenEel = (EntityGiantGardenEel)entityIn;
        this.body.rotateAngleX = (250 - gardenEel.slamTimer)/250f;
    }
}
