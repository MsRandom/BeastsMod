package random.beasts.client.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;

public class ModelSpartapodArmor extends BipedModel<LivingEntity> {

    public RendererModel helmetBase;
    public RendererModel headFin;

    public ModelSpartapodArmor() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.bipedHeadwear = new RendererModel(this, 24, 0);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHeadwear.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, 0.0F);
        this.bipedHead = new RendererModel(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.headFin = new RendererModel(this, 65, 21);
        this.headFin.setRotationPoint(0.0F, -7.0F, 1.0F);
        this.headFin.addBox(-0.5F, -6.0F, -5.0F, 1, 11, 12, 0.0F);
        this.helmetBase = new RendererModel(this, 64, 0);
        this.helmetBase.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.helmetBase.addBox(-5.0F, -9.0F, -5.0F, 10, 11, 10, 0.0F);
        this.helmetBase.addChild(this.headFin);
        this.bipedHead.addChild(this.helmetBase);
    }

    public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        if (entityIn instanceof ArmorStandEntity) {
            ArmorStandEntity e = (ArmorStandEntity) entityIn;
            this.bipedHead.rotateAngleX = (float) Math.toRadians(e.getHeadRotation().getX());
            this.bipedHead.rotateAngleY = (float) Math.toRadians(e.getHeadRotation().getY());
            this.bipedHead.rotateAngleZ = (float) Math.toRadians(e.getHeadRotation().getZ());
            this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
            this.bipedBody.rotateAngleX = (float) Math.toRadians(e.getBodyRotation().getX());
            this.bipedBody.rotateAngleY = (float) Math.toRadians(e.getBodyRotation().getY());
            this.bipedBody.rotateAngleZ = (float) Math.toRadians(e.getBodyRotation().getZ());
            this.bipedLeftArm.rotateAngleX = (float) Math.toRadians(e.getLeftArmRotation().getX());
            this.bipedLeftArm.rotateAngleY = (float) Math.toRadians(e.getLeftArmRotation().getY());
            this.bipedLeftArm.rotateAngleZ = (float) Math.toRadians(e.getLeftArmRotation().getZ());
            this.bipedRightArm.rotateAngleX = (float) Math.toRadians(e.getRightArmRotation().getX());
            this.bipedRightArm.rotateAngleY = (float) Math.toRadians(e.getRightArmRotation().getY());
            this.bipedRightArm.rotateAngleZ = (float) Math.toRadians(e.getRightArmRotation().getZ());
            this.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(e.getLeftLegRotation().getX());
            this.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(e.getLeftLegRotation().getY());
            this.bipedLeftLeg.rotateAngleZ = (float) Math.toRadians(e.getLeftLegRotation().getZ());
            this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
            this.bipedRightLeg.rotateAngleX = (float) Math.toRadians(e.getRightLegRotation().getX());
            this.bipedRightLeg.rotateAngleY = (float) Math.toRadians(e.getRightLegRotation().getY());
            this.bipedRightLeg.rotateAngleZ = (float) Math.toRadians(e.getRightLegRotation().getZ());
            this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
            copyModelAngles(this.bipedHead, this.bipedHeadwear);
        } else {
            super.setRotationAngles(entityIn, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, limbSwing);
        }
    }
    
    /*@Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.bipedHeadwear.render(f5);
        this.bipedHead.render(f5);
    }*/
}
