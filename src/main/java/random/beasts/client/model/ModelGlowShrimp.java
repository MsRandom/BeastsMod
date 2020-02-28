package random.beasts.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import random.beasts.common.entity.passive.EntityGlowShrimp;

public class ModelGlowShrimp extends EntityModel<EntityGlowShrimp> {
    public RendererModel body;
    public RendererModel leg;
    public RendererModel leg_1;
    public RendererModel leg_2;
    public RendererModel leg_3;
    public RendererModel leg_4;
    public RendererModel leg_5;
    public RendererModel leg_6;
    public RendererModel leg_7;
    public RendererModel tailBase;
    public RendererModel nozzle;
    public RendererModel tailMid;
    public RendererModel tailEnd;

    public ModelGlowShrimp() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.leg_7 = new RendererModel(this, 0, 0);
        this.leg_7.mirror = true;
        this.leg_7.setRotationPoint(0.5F, 0.5F, -1.5F);
        this.leg_7.addBox(0.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg_7, 0.0F, 0.2617993877991494F, 0.0F);
        this.leg_2 = new RendererModel(this, 0, 0);
        this.leg_2.setRotationPoint(-0.5F, 0.5F, 0.5F);
        this.leg_2.addBox(-2.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg_2, 0.0F, 0.08726646259971647F, 0.0F);
        this.tailBase = new RendererModel(this, 18, 0);
        this.tailBase.setRotationPoint(0.0F, 0.0F, 2.5F);
        this.tailBase.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(tailBase, 0.6981317007977318F, 0.0F, 0.0F);
        this.tailMid = new RendererModel(this, 29, 0);
        this.tailMid.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.tailMid.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(tailMid, 1.3962634015954636F, 0.0F, 0.0F);
        this.tailEnd = new RendererModel(this, 0, 14);
        this.tailEnd.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.tailEnd.addBox(-1.5F, 0.0F, 0.0F, 3, 0, 3, 0.0F);
        this.setRotateAngle(tailEnd, 0.8726646259971648F, 0.0F, 0.0F);
        this.leg_3 = new RendererModel(this, 0, 0);
        this.leg_3.setRotationPoint(-0.5F, 0.5F, 1.5F);
        this.leg_3.addBox(-2.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg_3, 0.0F, 0.2617993877991494F, 0.0F);
        this.leg_5 = new RendererModel(this, 0, 0);
        this.leg_5.mirror = true;
        this.leg_5.setRotationPoint(0.5F, 0.5F, 0.5F);
        this.leg_5.addBox(0.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg_5, 0.0F, -0.08726646259971647F, 0.0F);
        this.leg_1 = new RendererModel(this, 0, 0);
        this.leg_1.setRotationPoint(-0.5F, 0.5F, -0.5F);
        this.leg_1.addBox(-2.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg_1, 0.0F, -0.08726646259971647F, 0.0F);
        this.leg_6 = new RendererModel(this, 0, 0);
        this.leg_6.mirror = true;
        this.leg_6.setRotationPoint(0.5F, 0.5F, 1.5F);
        this.leg_6.addBox(0.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg_6, 0.0F, -0.2617993877991494F, 0.0F);
        this.body = new RendererModel(this, 0, 0);
        this.body.setRotationPoint(0.0F, 20.5F, -1.0F);
        this.body.addBox(-1.0F, -1.5F, -3.0F, 2, 3, 6, 0.0F);
        this.nozzle = new RendererModel(this, 18, 0);
        this.nozzle.setRotationPoint(0.0F, -1.0F, -1.0F);
        this.nozzle.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(nozzle, -0.5235987755982988F, 0.0F, 0.0F);
        this.leg = new RendererModel(this, 0, 0);
        this.leg.setRotationPoint(-0.5F, 0.5F, -1.5F);
        this.leg.addBox(-2.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(leg, 0.0F, -0.2617993877991494F, 0.0F);
        this.leg_4 = new RendererModel(this, 0, 0);
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
    public void render(EntityGlowShrimp entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(EntityGlowShrimp entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
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

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
