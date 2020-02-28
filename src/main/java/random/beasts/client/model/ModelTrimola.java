package random.beasts.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import random.beasts.common.entity.monster.EntityTrimola;

public class ModelTrimola extends EntityModel<EntityTrimola> {
    public RendererModel body;
    public RendererModel head;
    public RendererModel backfin;
    public RendererModel backleg;
    public RendererModel leftfrontleg;
    public RendererModel rightfrontleg;
    public RendererModel tail1;
    public RendererModel tail2;

    public ModelTrimola() {
        this.textureWidth = 96;
        this.textureHeight = 64;
        this.leftfrontleg = new RendererModel(this, 67, 15);
        this.leftfrontleg.setRotationPoint(2.0F, 6.0F, -5.0F);
        this.leftfrontleg.addBox(-1.5F, 0.0F, -2.0F, 3, 9, 4, 0.0F);
        this.tail1 = new RendererModel(this, 0, 32);
        this.tail1.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.tail1.addBox(-0.5F, -5.0F, 0.0F, 1, 10, 5, 0.0F);
        this.head = new RendererModel(this, 33, 0);
        this.head.setRotationPoint(0.0F, 0.0F, -9.0F);
        this.head.addBox(-2.5F, -5.0F, -4.0F, 5, 10, 4, 0.0F);
        this.rightfrontleg = new RendererModel(this, 51, 15);
        this.rightfrontleg.mirror = true;
        this.rightfrontleg.setRotationPoint(-2.0F, 6.0F, -5.0F);
        this.rightfrontleg.addBox(-1.5F, 0.0F, -2.0F, 3, 9, 4, 0.0F);
        this.backfin = new RendererModel(this, 52, 0);
        this.backfin.setRotationPoint(0.0F, -6.0F, 5.0F);
        this.backfin.addBox(-0.5F, -10.0F, -2.0F, 1, 10, 4, 0.0F);
        this.body = new RendererModel(this, 0, 0);
        this.body.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.body.addBox(-3.5F, -6.0F, -9.0F, 7, 12, 18, 0.0F);
        this.backleg = new RendererModel(this, 64, 0);
        this.backleg.setRotationPoint(0.0F, 6.0F, 6.0F);
        this.backleg.addBox(-1.5F, 0.0F, -2.0F, 3, 9, 4, 0.0F);
        this.tail2 = new RendererModel(this, 13, 32);
        this.tail2.setRotationPoint(0.0F, 0.0F, 5.0F);
        this.tail2.addBox(-0.5F, -2.0F, 0.0F, 1, 4, 5, 0.0F);
        this.body.addChild(this.leftfrontleg);
        this.body.addChild(this.tail1);
        this.body.addChild(this.head);
        this.body.addChild(this.rightfrontleg);
        this.body.addChild(this.backfin);
        this.body.addChild(this.backleg);
        this.tail1.addChild(this.tail2);
    }

    @Override
    public void render(EntityTrimola entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(EntityTrimola entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        this.body.rotateAngleX = 0.0F;
        this.backleg.rotateAngleX = 0.0F;
        this.body.rotationPointY = 9;
        if (entityIn.isRearing()) {
            limbSwing = entityIn.ticksExisted;
            limbSwingAmount = 0.23F;
            this.body.rotateAngleX = (float) Math.toRadians(-30.0F);
            this.body.rotationPointY = 7;
            this.backleg.rotateAngleX = (float) Math.toRadians(30.0F);
            float speed = 2.0F;
            float degree = 1.0F;
            this.leftfrontleg.rotateAngleX = MathHelper.cos((limbSwing * speed * 0.4F) + (float) Math.PI) * (degree * 1.8F) * limbSwingAmount * 0.5F;
            this.rightfrontleg.rotateAngleX = MathHelper.cos((limbSwing * speed * 0.4F) + (float) Math.PI) * (degree * -1.8F) * limbSwingAmount * 0.5F;
        }
        else {
/*            int attackTicks = ((EntityTrimola) entityIn).attackTicks;
            if (attackTicks > 0) {
                limbSwing = entityIn.ticksExisted;
                limbSwingAmount = 0.3f;
                if (attackTicks > 150) attackTicks = 150 - (attackTicks - 150);
                this.body.rotateAngleX = attackTicks / 150f;
            }*/
            this.leftfrontleg.rotateAngleX = MathHelper.cos(limbSwing * 0.5f) * 0.4f * limbSwingAmount;
            this.rightfrontleg.rotateAngleX = MathHelper.cos(limbSwing * 0.5f + (float) Math.PI) * 0.4f * limbSwingAmount;
            this.backleg.rotateAngleX = MathHelper.cos(limbSwing * 0.5f + (float) Math.PI) * 0.4f * limbSwingAmount;
        /*if(attackTicks == 0) {
            this.backleg.rotateAngleX = MathHelper.cos(limbSwing * 0.5f + ((float) Math.PI / 2)) * 0.4f * limbSwingAmount;
        }*/
        }
    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}