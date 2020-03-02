package random.beasts.client.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import random.beasts.common.entity.passive.EntityHermitTurtle;

public class ModelHermitTurtle extends EntityModel<EntityHermitTurtle> {
    public RendererModel shell1;
    public RendererModel shell2;
    public RendererModel head;
    public RendererModel shell3;
    public RendererModel leftArm;
    public RendererModel rightArm;

    public ModelHermitTurtle() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.shell3 = new RendererModel(this, 38, 21);
        this.shell3.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.shell3.addBox(-2.5F, -4.0F, -2.5F, 5, 4, 5, 0.0F);
        this.setRotateAngle(shell3, 0.0F, 0.0F, -0.2617993877991494F);
        this.head = new RendererModel(this, 39, 0);
        this.head.setRotationPoint(0.0F, 1.5F, -6.0F);
        this.head.addBox(-2.5F, -2.5F, -5.5F, 5, 5, 6, 0.0F);
        this.shell1 = new RendererModel(this, 0, 0);
        this.shell1.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.shell1.addBox(-6.0F, -4.0F, -6.0F, 12, 8, 12, 0.0F);
        this.leftArm = new RendererModel(this, 64, 9);
        this.leftArm.mirror = true;
        this.leftArm.setRotationPoint(4.0F, 0.5F, 0.0F);
        this.leftArm.addBox(-1.5F, -1.0F, -4.0F, 3, 3, 6, 0.0F);
        this.setRotateAngle(leftArm, 0.0F, -0.3490658503988659F, 0.0F);
        this.shell2 = new RendererModel(this, 0, 22);
        this.shell2.setRotationPoint(2.0F, -4.0F, 0.0F);
        this.shell2.addBox(-4.5F, -3.0F, -4.5F, 9, 6, 9, 0.0F);
        this.setRotateAngle(shell2, 0.0F, 0.0F, 0.5235987755982988F);
        this.rightArm = new RendererModel(this, 64, 9);
        this.rightArm.setRotationPoint(-4.0F, 0.5F, 0.0F);
        this.rightArm.addBox(-1.5F, -1.0F, -4.0F, 3, 3, 6, 0.0F);
        this.setRotateAngle(rightArm, 0.0F, 0.3490658503988659F, 0.0F);
        this.shell2.addChild(this.shell3);
        this.shell1.addChild(this.head);
        this.head.addChild(this.leftArm);
        this.shell1.addChild(this.shell2);
        this.head.addChild(this.rightArm);
    }

    @Override
    public void render(EntityHermitTurtle entity, float f, float f1, float f2, float f3, float f4, float f5) {
        EntityHermitTurtle turtle = entity;
        this.head.offsetZ = turtle.exitTicks / 50f;
        this.leftArm.rotateAngleY = (turtle.exitTicks / 125f) - 0.2f;
        this.rightArm.rotateAngleY = (turtle.exitTicks / -125f) + 0.2f;
        if (turtle.isChild()) {
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
            GlStateManager.translatef(0.0F, 24.0F * f5, 0.0F);
        }
        if (turtle.isSneaking()) GlStateManager.translatef(0.0F, 0.2F, 0.0F);
        this.shell1.render(f5);
    }

    @Override
    public void setRotationAngles(EntityHermitTurtle entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.1f) * 0.2f * limbSwingAmount;
        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.1f) * -0.2f * limbSwingAmount;
    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
