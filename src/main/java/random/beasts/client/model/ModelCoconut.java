package random.beasts.client.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import random.beasts.common.entity.monster.EntityCoconutCrab;

@SuppressWarnings("WeakerAccess")
public class ModelCoconut extends EntityModel<EntityCoconutCrab> {
    public RendererModel body;
    public RendererModel leg7;
    public RendererModel leg8;
    public RendererModel leg6;
    public RendererModel leg5;
    public RendererModel leg1;
    public RendererModel leg2;
    public RendererModel leg3;
    public RendererModel leg4;
    public RendererModel eye;
    public RendererModel eye_1;
    public RendererModel armLeft;
    public RendererModel armRight;
    public RendererModel shell;
    public RendererModel clawLeft;
    public RendererModel clawRight;

    public ModelCoconut() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.shell = new RendererModel(this, 0, 0);
        this.shell.setRotationPoint(0.0F, -2.0F, 2.0F);
        this.shell.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
        this.setRotateAngle(shell, -0.2617993877991494F, 0.0F, 0.0F);
        this.leg7 = new RendererModel(this, 0, 0);
        this.leg7.setRotationPoint(-2.0F, 2.2F, 0.5F);
        this.leg7.addBox(0.0F, 0.0F, -0.5F, 0, 4, 1, 0.0F);
        this.setRotateAngle(leg7, 0.08726646259971647F, 0.0F, 0.3490658503988659F);
        this.eye = new RendererModel(this, 4, 0);
        this.eye.setRotationPoint(-1.5F, 0.0F, -4.0F);
        this.eye.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.clawLeft = new RendererModel(this, 50, 0);
        this.clawLeft.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.clawLeft.addBox(-1.5F, -0.5F, -3.0F, 3, 1, 3, 0.0F);
        this.setRotateAngle(clawLeft, 0.0F, -0.17453292519943295F, 0.0F);
        this.clawRight = new RendererModel(this, 24, 4);
        this.clawRight.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.clawRight.addBox(-1.5F, -0.5F, -3.0F, 3, 1, 3, 0.0F);
        this.setRotateAngle(clawRight, 0.0F, 0.17453292519943295F, 0.0F);
        this.leg4 = new RendererModel(this, 0, 0);
        this.leg4.setRotationPoint(2.0F, 2.0F, 1.5F);
        this.leg4.addBox(0.0F, 0.0F, -0.5F, 0, 4, 1, 0.0F);
        this.setRotateAngle(leg4, 0.17453292519943295F, 0.0F, -0.2617993877991494F);
        this.leg8 = new RendererModel(this, 0, 0);
        this.leg8.setRotationPoint(-2.0F, 2.0F, 1.5F);
        this.leg8.addBox(0.0F, 0.0F, -0.5F, 0, 4, 1, 0.0F);
        this.setRotateAngle(leg8, 0.17453292519943295F, 0.0F, 0.2617993877991494F);
        this.armRight = new RendererModel(this, 32, 0);
        this.armRight.setRotationPoint(2.5F, 2.0F, -3.0F);
        this.armRight.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(armRight, 0.0F, -0.3490658503988659F, 0.0F);
        this.leg1 = new RendererModel(this, 0, 0);
        this.leg1.setRotationPoint(2.0F, 2.3F, -1.5F);
        this.leg1.addBox(0.0F, 0.0F, -0.5F, 0, 4, 1, 0.0F);
        this.setRotateAngle(leg1, -0.08726646259971647F, 0.0F, -0.3490658503988659F);
        this.body = new RendererModel(this, 32, 0);
        this.body.setRotationPoint(0.0F, 18.5F, 0.0F);
        this.body.addBox(-2.5F, 0.0F, -4.0F, 5, 3, 8, 0.0F);
        this.leg2 = new RendererModel(this, 0, 0);
        this.leg2.setRotationPoint(2.0F, 2.3F, -0.5F);
        this.leg2.addBox(0.0F, 0.0F, -0.5F, 0, 4, 1, 0.0F);
        this.setRotateAngle(leg2, 0.0F, 0.0F, -0.3490658503988659F);
        this.eye_1 = new RendererModel(this, 4, 0);
        this.eye_1.setRotationPoint(1.5F, 0.0F, -4.0F);
        this.eye_1.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.armLeft = new RendererModel(this, 24, 0);
        this.armLeft.setRotationPoint(-2.5F, 2.0F, -3.0F);
        this.armLeft.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(armLeft, 0.0F, 0.3490658503988659F, 0.0F);
        this.leg5 = new RendererModel(this, 0, 0);
        this.leg5.setRotationPoint(-2.0F, 2.3F, -1.5F);
        this.leg5.addBox(0.0F, 0.0F, -0.5F, 0, 4, 1, 0.0F);
        this.setRotateAngle(leg5, -0.08726646259971647F, 0.0F, 0.3490658503988659F);
        this.leg3 = new RendererModel(this, 0, 0);
        this.leg3.setRotationPoint(2.0F, 2.2F, 0.5F);
        this.leg3.addBox(0.0F, 0.0F, -0.5F, 0, 4, 1, 0.0F);
        this.setRotateAngle(leg3, 0.08726646259971647F, 0.0F, -0.3490658503988659F);
        this.leg6 = new RendererModel(this, 0, 0);
        this.leg6.setRotationPoint(-2.0F, 2.3F, -0.5F);
        this.leg6.addBox(0.0F, 0.0F, -0.5F, 0, 4, 1, 0.0F);
        this.setRotateAngle(leg6, 0.0F, 0.0F, 0.3490658503988659F);
        this.body.addChild(this.shell);
        this.armLeft.addChild(this.clawLeft);
        this.armRight.addChild(this.clawRight);
        this.body.addChild(this.leg7);
        this.body.addChild(this.eye);
        this.body.addChild(this.leg4);
        this.body.addChild(this.leg8);
        this.body.addChild(this.armRight);
        this.body.addChild(this.leg1);
        this.body.addChild(this.leg2);
        this.body.addChild(this.eye_1);
        this.body.addChild(this.armLeft);
        this.body.addChild(this.leg5);
        this.body.addChild(this.leg3);
        this.body.addChild(this.leg6);
    }

    @Override
    public void render(EntityCoconutCrab entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entityIn == null || !entityIn.isOut()) GlStateManager.translated(0, 1.58, 0);
        GlStateManager.rotated(90, 0, 1, 0);
        if (entityIn != null && entityIn.isOut()) this.body.render(scale);
        else this.shell.render(scale);
    }

    @Override
    public void setRotationAngles(EntityCoconutCrab entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.leg1.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount + 0.2f;
        this.leg4.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount + 0.2f;
        this.leg5.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg6.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg7.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount + 0.2f;
        this.leg8.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount + 0.2f;
    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
