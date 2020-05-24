package net.msrandom.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelGlowShrimp - Coda
 * Created using Tabula 7.1.0
 */
public class ModelGlowShrimp extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer leg;
    public ModelRenderer leg_1;
    public ModelRenderer leg_2;
    public ModelRenderer leg_3;
    public ModelRenderer leg_4;
    public ModelRenderer leg_5;
    public ModelRenderer leg_6;
    public ModelRenderer leg_7;
    public ModelRenderer tailBase;
    public ModelRenderer nozzle;
    public ModelRenderer tailMid;
    public ModelRenderer tailEnd;

    public ModelGlowShrimp() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.leg_7 = new ModelRenderer(this, 0, 0);
        this.leg_7.mirror = true;
        this.leg_7.setRotationPoint(0.5F, 0.5F, -1.5F);
        this.leg_7.addBox(0.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg_7, 0.0F, 0.2617993877991494F, 0.0F);
        this.leg_2 = new ModelRenderer(this, 0, 0);
        this.leg_2.setRotationPoint(-0.5F, 0.5F, 0.5F);
        this.leg_2.addBox(-2.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg_2, 0.0F, 0.08726646259971647F, 0.0F);
        this.tailBase = new ModelRenderer(this, 18, 0);
        this.tailBase.setRotationPoint(0.0F, 0.0F, 2.5F);
        this.tailBase.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(tailBase, 0.6981317007977318F, 0.0F, 0.0F);
        this.tailMid = new ModelRenderer(this, 29, 0);
        this.tailMid.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.tailMid.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(tailMid, 1.3962634015954636F, 0.0F, 0.0F);
        this.tailEnd = new ModelRenderer(this, 0, 14);
        this.tailEnd.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.tailEnd.addBox(-1.5F, 0.0F, 0.0F, 3, 0, 3, 0.0F);
        this.setRotateAngle(tailEnd, 0.8726646259971648F, 0.0F, 0.0F);
        this.leg_3 = new ModelRenderer(this, 0, 0);
        this.leg_3.setRotationPoint(-0.5F, 0.5F, 1.5F);
        this.leg_3.addBox(-2.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg_3, 0.0F, 0.2617993877991494F, 0.0F);
        this.leg_5 = new ModelRenderer(this, 0, 0);
        this.leg_5.mirror = true;
        this.leg_5.setRotationPoint(0.5F, 0.5F, 0.5F);
        this.leg_5.addBox(0.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg_5, 0.0F, -0.08726646259971647F, 0.0F);
        this.leg_1 = new ModelRenderer(this, 0, 0);
        this.leg_1.setRotationPoint(-0.5F, 0.5F, -0.5F);
        this.leg_1.addBox(-2.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg_1, 0.0F, -0.08726646259971647F, 0.0F);
        this.leg_6 = new ModelRenderer(this, 0, 0);
        this.leg_6.mirror = true;
        this.leg_6.setRotationPoint(0.5F, 0.5F, 1.5F);
        this.leg_6.addBox(0.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg_6, 0.0F, -0.2617993877991494F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 20.5F, -1.0F);
        this.body.addBox(-1.0F, -1.5F, -3.0F, 2, 3, 6, 0.0F);
        this.nozzle = new ModelRenderer(this, 18, 0);
        this.nozzle.setRotationPoint(0.0F, -1.0F, -1.0F);
        this.nozzle.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(nozzle, -0.5235987755982988F, 0.0F, 0.0F);
        this.leg = new ModelRenderer(this, 0, 0);
        this.leg.setRotationPoint(-0.5F, 0.5F, -1.5F);
        this.leg.addBox(-2.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg, 0.0F, -0.2617993877991494F, 0.0F);
        this.leg_4 = new ModelRenderer(this, 0, 0);
        this.leg_4.mirror = true;
        this.leg_4.setRotationPoint(0.5F, 0.5F, -0.5F);
        this.leg_4.addBox(0.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg_4, 0.0F, 0.08726646259971647F, 0.0F);
        this.body.addChild(this.leg_7);
        this.body.addChild(this.leg_2);
        this.body.addChild(this.tailBase);
        this.tailBase.addChild(this.tailMid);
        this.tailMid.addChild(this.tailEnd);
        this.body.addChild(this.leg_3);
        this.body.addChild(this.leg_5);
        this.body.addChild(this.leg_1);
        this.body.addChild(this.leg_6);
        this.body.addChild(this.nozzle);
        this.body.addChild(this.leg);
        this.body.addChild(this.leg_4);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        float speed = 0.75f, degree = 0.75f;

        this.leg.rotateAngleX = MathHelper.cos(limbSwing * speed) * degree * limbSwingAmount;
        this.leg_1.rotateAngleX = MathHelper.cos(60 + limbSwing * speed) * degree * limbSwingAmount;
        this.leg_2.rotateAngleX = MathHelper.cos(limbSwing * speed) * degree * limbSwingAmount;
        this.leg_3.rotateAngleX = MathHelper.cos(60 + limbSwing * speed) * degree * limbSwingAmount;

        this.leg_4.rotateAngleX = MathHelper.cos(limbSwing * speed) * degree * limbSwingAmount;
        this.leg_5.rotateAngleX = MathHelper.cos(60 + limbSwing * speed) * degree * limbSwingAmount;
        this.leg_6.rotateAngleX = MathHelper.cos(limbSwing * speed) * degree * limbSwingAmount;
        this.leg_7.rotateAngleX = MathHelper.cos(60 + limbSwing * speed) * degree * limbSwingAmount;

        this.tailBase.rotateAngleY = MathHelper.cos(20 + limbSwing * speed * 0.7F) * degree * limbSwingAmount;
        this.tailMid.rotateAngleY = MathHelper.cos(10 + limbSwing * speed * 0.7F) * degree * limbSwingAmount;
        this.tailEnd.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.7F) * degree * limbSwingAmount;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
