package random.beasts.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import random.beasts.common.entity.monster.EntityVileEel;
import random.beasts.common.entity.passive.EntityPufferfishDog;

public class ModelVileEel extends EntityModel {
    public RendererModel body;
    public RendererModel jaw;
    public RendererModel head;
    public RendererModel legLeft;
    public RendererModel armLeft;
    public RendererModel legRight;
    public RendererModel armRight;
    public RendererModel tailBase;
    public RendererModel teeth;
    public RendererModel tail;
    public RendererModel tailFin;
    public RendererModel tailTip;

    public ModelVileEel() {
        this.textureWidth = 96;
        this.textureHeight = 96;
        this.legRight = new RendererModel(this, 0, 0);
        this.legRight.setRotationPoint(-2.0F, 0.0F, 6.0F);
        this.legRight.addBox(-5.0F, 0.0F, -2.5F, 5, 14, 5, 0.0F);
        this.setRotateAngle(legRight, 0.0F, 0.0F, 0.17453292519943295F);
        this.armLeft = new RendererModel(this, 0, 34);
        this.armLeft.setRotationPoint(2.0F, 2.0F, -5.0F);
        this.armLeft.addBox(0.0F, 0.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(armLeft, -0.2617993877991494F, 0.0F, -0.2617993877991494F);
        this.teeth = new RendererModel(this, 0, 53);
        this.teeth.setRotationPoint(0.0F, 0.0F, -9.5F);
        this.teeth.addBox(-3.5F, -3.0F, -4.0F, 7, 3, 14, 0.0F);
        this.tailFin = new RendererModel(this, 31, 30);
        this.tailFin.setRotationPoint(0.0F, -1.0F, 4.0F);
        this.tailFin.addBox(0.0F, -12.0F, -7.0F, 0, 9, 12, 0.0F);
        this.setRotateAngle(tailFin, 0.6108652381980153F, 0.0F, 0.0F);
        this.legLeft = new RendererModel(this, 0, 0);
        this.legLeft.setRotationPoint(2.0F, 0.0F, 6.0F);
        this.legLeft.addBox(0.0F, 0.0F, -2.5F, 5, 14, 5, 0.0F);
        this.setRotateAngle(legLeft, 0.0F, 0.0F, -0.17453292519943295F);
        this.body = new RendererModel(this, 0, 0);
        this.body.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.body.addBox(-5.5F, -7.0F, -10.0F, 11, 14, 20, 0.0F);
        this.tailBase = new RendererModel(this, 48, 45);
        this.tailBase.setRotationPoint(0.0F, -2.0F, 7.0F);
        this.tailBase.addBox(-3.5F, -5.0F, 0.0F, 7, 10, 12, 0.0F);
        this.setRotateAngle(tailBase, -0.6108652381980153F, 0.0F, 0.0F);
        this.head = new RendererModel(this, 48, 20);
        this.head.setRotationPoint(0.0F, -1.5F, -10.0F);
        this.head.addBox(-3.0F, -5.0F, -13.0F, 6, 6, 14, 0.0F);
        this.armRight = new RendererModel(this, 0, 34);
        this.armRight.setRotationPoint(-2.0F, 2.0F, -5.0F);
        this.armRight.addBox(-5.0F, 0.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(armRight, -0.2617993877991494F, 0.0F, 0.2617993877991494F);
        this.tailTip = new RendererModel(this, 0, 75);
        this.tailTip.setRotationPoint(0.0F, 0.0F, 10.0F);
        this.tailTip.addBox(-2.0F, -3.0F, 0.0F, 4, 6, 12, 0.0F);
        this.setRotateAngle(tailTip, 0.3490658503988659F, 0.0F, 0.0F);
        this.tail = new RendererModel(this, 30, 67);
        this.tail.setRotationPoint(0.0F, 0.0F, 10.0F);
        this.tail.addBox(-3.0F, -4.0F, 0.0F, 6, 8, 12, 0.0F);
        this.setRotateAngle(tail, 0.08726646259971647F, 0.0F, 0.0F);
        this.jaw = new RendererModel(this, 42, 0);
        this.jaw.setRotationPoint(0.0F, -0.5F, -10.0F);
        this.jaw.addBox(-4.0F, 0.0F, -14.0F, 8, 6, 14, 0.0F);
        this.body.addChild(this.legRight);
        this.body.addChild(this.armLeft);
        this.jaw.addChild(this.teeth);
        this.tailBase.addChild(this.tailFin);
        this.body.addChild(this.legLeft);
        this.body.addChild(this.tailBase);
        this.body.addChild(this.head);
        this.body.addChild(this.armRight);
        this.tail.addChild(this.tailTip);
        this.tailBase.addChild(this.tail);
        this.body.addChild(this.jaw);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (this.isChild) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * f5, 1.65F * f5);
        }
        if (entity instanceof EntityPufferfishDog && ((EntityPufferfishDog) entity).isInflated()) this.body.render(f5);
        else this.body.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        float speed = 0.75f, degree = 0.75f;
        this.armLeft.rotateAngleX = MathHelper.cos(limbSwing * speed) * degree * limbSwingAmount;
        this.armRight.rotateAngleX = MathHelper.cos(limbSwing * speed + (float) Math.PI) * degree * limbSwingAmount;
        this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * speed + (float) Math.PI) * degree * limbSwingAmount;
        this.legRight.rotateAngleX = MathHelper.cos(limbSwing * speed) * degree * limbSwingAmount;
        this.tailBase.rotateAngleY = MathHelper.cos(20 + limbSwing * speed * 0.7F) * degree * limbSwingAmount;

        if (entityIn instanceof EntityVileEel) {

            if (!entityIn.getPassengers().isEmpty()) {
                this.head.rotateAngleX = (float) Math.toRadians(-35);
                this.jaw.rotateAngleX = (float) Math.toRadians(30);
            } else {
                this.jaw.rotateAngleX = 0;
                this.head.rotateAngleX = 0;
            }

        }

    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
