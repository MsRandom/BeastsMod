package random.beasts.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

public class ModelSpartapodArmor extends ModelBiped {

    public ModelRenderer helmetBase;
    public ModelRenderer headFin;

    public ModelSpartapodArmor() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.bipedHeadwear = new ModelRenderer(this, 24, 0);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHeadwear.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, 0.0F);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.headFin = new ModelRenderer(this, 65, 11);
        this.headFin.setRotationPoint(0.0F, -9.0F, 0.5F);
        this.headFin.addBox(0.0F, -6.0F, -6.0F, 0, 12, 13, 0.0F);
        this.helmetBase = new ModelRenderer(this, 64, 0);
        this.helmetBase.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.helmetBase.addBox(-5.0F, -9.0F, -5.0F, 10, 11, 10, 0.0F);
        this.helmetBase.addChild(this.headFin);
        this.bipedHead.addChild(this.helmetBase);
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        if (entityIn instanceof EntityArmorStand) {
            EntityArmorStand e = (EntityArmorStand) entityIn;
            float f = 0.017453292F;
            this.bipedHead.rotateAngleX = f * e.getHeadRotation().getX();
            this.bipedHead.rotateAngleY = f * e.getHeadRotation().getY();
            this.bipedHead.rotateAngleZ = f * e.getHeadRotation().getZ();
            this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
            this.bipedBody.rotateAngleX = f * e.getBodyRotation().getX();
            this.bipedBody.rotateAngleY = f * e.getBodyRotation().getY();
            this.bipedBody.rotateAngleZ = f * e.getBodyRotation().getZ();
            this.bipedLeftArm.rotateAngleX = f * e.getLeftArmRotation().getX();
            this.bipedLeftArm.rotateAngleY = f * e.getLeftArmRotation().getY();
            this.bipedLeftArm.rotateAngleZ = f * e.getLeftArmRotation().getZ();
            this.bipedRightArm.rotateAngleX = f * e.getRightArmRotation().getX();
            this.bipedRightArm.rotateAngleY = f * e.getRightArmRotation().getY();
            this.bipedRightArm.rotateAngleZ = f * e.getRightArmRotation().getZ();
            this.bipedLeftLeg.rotateAngleX = f * e.getLeftLegRotation().getX();
            this.bipedLeftLeg.rotateAngleY = f * e.getLeftLegRotation().getY();
            this.bipedLeftLeg.rotateAngleZ = f * e.getLeftLegRotation().getZ();
            this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
            this.bipedRightLeg.rotateAngleX = f * e.getRightLegRotation().getX();
            this.bipedRightLeg.rotateAngleY = f * e.getRightLegRotation().getY();
            this.bipedRightLeg.rotateAngleZ = f * e.getRightLegRotation().getZ();
            this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
            copyModelAngles(this.bipedHead, this.bipedHeadwear);
        } else {
            super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        }
    }
    
    /*@Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.bipedHeadwear.render(f5);
        this.bipedHead.render(f5);
    }*/
}
