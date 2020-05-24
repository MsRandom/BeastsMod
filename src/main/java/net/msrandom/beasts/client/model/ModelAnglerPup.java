package net.msrandom.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.msrandom.beasts.common.entity.passive.EntityAnglerPup;

/**
 * ModelAnglerPup - Coda
 * Created using Tabula 7.1.0
 */
public class ModelAnglerPup extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer leftArm;
    public ModelRenderer rightArm;
    public ModelRenderer leftLeg;
    public ModelRenderer rightLeg;
    public ModelRenderer tail;
    public ModelRenderer teeth;
    public ModelRenderer collar;
    public ModelRenderer light;
    public ModelRenderer fin;

    private State state = State.STANDING;
    
    public ModelAnglerPup() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.rightLeg = new ModelRenderer(this, 41, 0);
        this.rightLeg.mirror = true;
        this.rightLeg.setRotationPoint(-1.8F, 2.0F, 2.5F);
        this.rightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(rightLeg, 0.3490658503988659F, 0.0F, 0.2617993877991494F);
        this.collar = new ModelRenderer(this, 26, 14);
        this.collar.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.collar.addBox(-3.0F, -3.0F, -0.5F, 6, 6, 1, 0.0F);
        this.rightArm = new ModelRenderer(this, 25, 0);
        this.rightArm.mirror = true;
        this.rightArm.setRotationPoint(-2.0F, 1.0F, -1.0F);
        this.rightArm.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(rightArm, -0.2617993877991494F, 0.0F, 0.2617993877991494F);
        this.light = new ModelRenderer(this, 0, 21);
        this.light.setRotationPoint(0.0F, -2.0F, -3.5F);
        this.light.addBox(0.0F, -4.0F, -2.0F, 0, 4, 4, 0.0F);
        this.setRotateAngle(light, 0.2617993877991494F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.body.addBox(-2.5F, -2.5F, -3.5F, 5, 5, 7, 0.0F);
        this.setRotateAngle(body, -0.08726646259971647F, 0.0F, 0.0F);
        this.leftArm = new ModelRenderer(this, 17, 0);
        this.leftArm.setRotationPoint(2.0F, 1.0F, -1.0F);
        this.leftArm.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(leftArm, -0.2617993877991494F, 0.0F, -0.2617993877991494F);
        this.leftLeg = new ModelRenderer(this, 33, 0);
        this.leftLeg.setRotationPoint(1.8F, 2.0F, 2.5F);
        this.leftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(leftLeg, 0.3490658503988659F, 0.0F, -0.2617993877991494F);
        this.tail = new ModelRenderer(this, 0, 12);
        this.tail.setRotationPoint(0.0F, 0.0F, 3.1F);
        this.tail.addBox(0.0F, -2.0F, 0.0F, 0, 4, 4, 0.0F);
        this.setRotateAngle(tail, 0.08726646259971647F, 0.0F, 0.0F);
        this.teeth = new ModelRenderer(this, 13, 17);
        this.teeth.setRotationPoint(0.0F, 1.5F, -3.4F);
        this.teeth.addBox(-1.5F, -2.0F, 0.0F, 3, 2, 0, 0.0F);
        this.setRotateAngle(teeth, 0.2617993877991494F, 0.0F, 0.0F);
        this.fin = new ModelRenderer(this, 10, 18);
        this.fin.setRotationPoint(0.0F, -2.5F, 1.0F);
        this.fin.addBox(0.0F, -2.0F, -2.0F, 0, 2, 4, 0.0F);
        this.body.addChild(this.rightLeg);
        this.body.addChild(this.collar);
        this.body.addChild(this.rightArm);
        this.body.addChild(this.light);
        this.body.addChild(this.leftArm);
        this.body.addChild(this.leftLeg);
        this.body.addChild(this.tail);
        this.body.addChild(this.teeth);
        this.body.addChild(this.fin);
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        float speed = 0.75f, degree = 1.45f;

        if (this.state == State.PARTY) {
            float f1 = MathHelper.cos((float) entityIn.ticksExisted);
            float f2 = MathHelper.sin((float) entityIn.ticksExisted);
            this.body.rotationPointX = f1;
            this.body.rotationPointY = 20.0F + f2;

            this.leftLeg.rotationPointX = 1.8F - f1;
            this.leftLeg.rotationPointY = 1.0F - f2;

            this.leftArm.rotationPointX = 2.0F - f1;
            this.leftArm.rotationPointY = 1.0F - f2;

            this.rightLeg.rotationPointX = -1.8F - f1;
            this.rightLeg.rotationPointY = 1.0F - f2;

            this.rightArm.rotationPointX = -2.0F - f1;
            this.rightArm.rotationPointY = 1.0F - f2;

            this.tail.rotationPointX = f1;
            this.tail.rotationPointY = f2;
        } else if (this.state == State.SITTING) {
            this.rightArm.rotateAngleZ = 0.2617993877991494F;
            this.leftLeg.rotateAngleX = -1.0821041362364843F;
            this.leftLeg.rotateAngleZ = -0.2617993877991494F;
            this.rightLeg.rotateAngleX = -1.0821041362364843F;
            this.rightLeg.rotateAngleZ = 0.2617993877991494F;
            this.leftArm.rotateAngleZ = -0.2617993877991494F;
        } else {
            this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * degree) * speed * limbSwingAmount;
            this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * degree + (float) Math.PI) * speed * limbSwingAmount;
            this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * degree + (float) Math.PI) * speed * limbSwingAmount;
            this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * degree) * speed * limbSwingAmount;
        }
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
    }


    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {

        if (((EntityAnglerPup) entitylivingbaseIn).isPartying()) {
            this.state = State.PARTY;
        } else if (((EntityAnglerPup) entitylivingbaseIn).isSitting()) {
            resetRotationPoints();
            this.state = State.SITTING;
        } else if (entitylivingbaseIn.motionX != 0 || entitylivingbaseIn.motionY != 0 || entitylivingbaseIn.motionZ != 0) {
            resetRotationPoints();
            this.state = State.WALKING;
        } else {
            resetRotationPoints();
            this.state = State.STANDING;
        }
    }

    private void resetRotationPoints() {
        this.setRotateAngle(tail, 0.08726646259971647F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm, -0.2617993877991494F, 0.0F, -0.2617993877991494F);
        this.setRotateAngle(leftLeg, 0.3490658503988659F, 0.0F, -0.2617993877991494F);
        this.setRotateAngle(body, -0.08726646259971647F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg, 0.3490658503988659F, 0.0F, 0.2617993877991494F);
        this.setRotateAngle(rightArm, -0.2617993877991494F, 0.0F, 0.2617993877991494F);

    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @SideOnly(Side.CLIENT)
    enum State {
        WALKING,
        STANDING,
        SITTING,
        PARTY
    }
}
