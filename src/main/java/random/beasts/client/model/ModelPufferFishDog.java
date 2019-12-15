package random.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import random.beasts.common.entity.passive.EntityPufferfishDog;

public class ModelPufferFishDog extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer body_1;
    public ModelRenderer leftArm;
    public ModelRenderer tail;
    public ModelRenderer teeth;
    public ModelRenderer spikes;
    public ModelRenderer spikes_1;
    public ModelRenderer spikes_2;
    public ModelRenderer spikes_3;
    public ModelRenderer rightArm;
    public ModelRenderer leftLeg;
    public ModelRenderer rightLeg;
    public ModelRenderer teeth_1;
    public ModelRenderer collarInflated;
    public ModelRenderer glasses;
    public ModelRenderer leftArm_1;
    public ModelRenderer rightArm_1;
    public ModelRenderer leftLeg_1;
    public ModelRenderer rightLeg_1;
    public ModelRenderer tail_1;
    public ModelRenderer teeth_2;
    public ModelRenderer spikes_4;
    public ModelRenderer spikes_5;
    public ModelRenderer spikes_6;
    public ModelRenderer spikes_7;
    public ModelRenderer collarNormal;
    public ModelRenderer glasses_1;
    
    private State state = State.STANDING;

    public ModelPufferFishDog() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.leftLeg = new ModelRenderer(this, 33, 0);
        this.leftLeg.setRotationPoint(4.0F, 1.0F, 3.0F);
        this.leftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(leftLeg, 0.17453292519943295F, 0.0F, -1.0471975511965976F);
        this.tail_1 = new ModelRenderer(this, 0, 12);
        this.tail_1.setRotationPoint(0.0F, 0.0F, 3.1F);
        this.tail_1.addBox(0.0F, -2.0F, 0.0F, 0, 4, 4, 0.0F);
        this.setRotateAngle(tail_1, 0.08726646259971647F, 0.0F, 0.0F);
        this.glasses_1 = new ModelRenderer(this, 46, 3);
        this.glasses_1.setRotationPoint(0.0F, -0.5F, -3.0F);
        this.glasses_1.addBox(-3.0F, -1.0F, -1.0F, 6, 2, 3, 0.0F);
        this.collarInflated = new ModelRenderer(this, 29, 39);
        this.collarInflated.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.collarInflated.addBox(-5.5F, -5.5F, -0.5F, 11, 11, 1, 0.0F);
        this.teeth = new ModelRenderer(this, 0, 13);
        this.teeth.setRotationPoint(0.0F, 1.5F, -3.4F);
        this.teeth.addBox(-1.5F, -2.0F, 0.0F, 3, 2, 0, 0.0F);
        this.setRotateAngle(teeth, 0.2617993877991494F, 0.0F, 0.0F);
        this.leftArm_1 = new ModelRenderer(this, 17, 0);
        this.leftArm_1.setRotationPoint(2.0F, 1.0F, -1.0F);
        this.leftArm_1.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(leftArm_1, -0.2617993877991494F, 0.0F, -0.2617993877991494F);
        this.spikes = new ModelRenderer(this, 0, 50);
        this.spikes.setRotationPoint(0.0F, -4.9F, -6.9F);
        this.spikes.addBox(-5.0F, -1.0F, 0.0F, 10, 1, 0, 0.0F);
        this.setRotateAngle(spikes, 0.5235987755982988F, 0.0F, 0.0F);
        this.leftLeg_1 = new ModelRenderer(this, 33, 0);
        this.leftLeg_1.setRotationPoint(1.8F, 2.0F, 2.5F);
        this.leftLeg_1.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(leftLeg_1, 0.3490658503988659F, 0.0F, -0.2617993877991494F);
        this.body = new ModelRenderer(this, 16, 8);
        this.body.setRotationPoint(0.0F, 19.5F, 0.0F);
        this.body.addBox(-5.0F, -5.0F, -7.0F, 10, 10, 14, 0.0F);
        this.setRotateAngle(body, -0.08726646259971647F, 0.0F, 0.0F);
        this.spikes_2 = new ModelRenderer(this, 3, 28);
        this.spikes_2.setRotationPoint(4.9F, 0.0F, -6.9F);
        this.spikes_2.addBox(0.0F, -5.0F, 0.0F, 1, 10, 0, 0.0F);
        this.setRotateAngle(spikes_2, 0.0F, 0.5235987755982988F, 0.0F);
        this.body_1 = new ModelRenderer(this, 0, 0);
        this.body_1.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.body_1.addBox(-2.5F, -2.5F, -3.5F, 5, 5, 7, 0.0F);
        this.setRotateAngle(body_1, -0.08726646259971647F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 17, 0);
        this.rightArm.mirror = true;
        this.rightArm.setRotationPoint(-4.0F, 1.0F, -3.0F);
        this.rightArm.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(rightArm, -0.17453292519943295F, 0.0F, 1.0471975511965976F);
        this.rightLeg_1 = new ModelRenderer(this, 41, 0);
        this.rightLeg_1.mirror = true;
        this.rightLeg_1.setRotationPoint(-1.8F, 2.0F, 2.5F);
        this.rightLeg_1.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(rightLeg_1, 0.3490658503988659F, 0.0F, 0.2617993877991494F);
        this.spikes_3 = new ModelRenderer(this, 0, 52);
        this.spikes_3.setRotationPoint(0.0F, 4.9F, -6.9F);
        this.spikes_3.addBox(-5.0F, 0.0F, 0.0F, 10, 1, 0, 0.0F);
        this.setRotateAngle(spikes_3, -0.5235987755982988F, 0.0F, 0.0F);
        this.teeth_1 = new ModelRenderer(this, 0, 13);
        this.teeth_1.setRotationPoint(0.0F, 2.0F, -6.9F);
        this.teeth_1.addBox(-1.5F, -2.0F, 0.0F, 3, 2, 0, 0.0F);
        this.setRotateAngle(teeth_1, 0.2617993877991494F, 0.0F, 0.0F);
        this.glasses = new ModelRenderer(this, 34, 33);
        this.glasses.setRotationPoint(0.0F, 0.0F, -7.0F);
        this.glasses.addBox(-5.5F, -1.0F, -1.0F, 11, 2, 3, 0.0F);
        this.spikes_6 = new ModelRenderer(this, 3, 0);
        this.spikes_6.setRotationPoint(2.4F, 0.0F, -3.4F);
        this.spikes_6.addBox(0.0F, -2.5F, 0.0F, 1, 5, 0, 0.0F);
        this.setRotateAngle(spikes_6, 0.0F, 0.5235987755982988F, 0.0F);
        this.leftArm = new ModelRenderer(this, 25, 0);
        this.leftArm.setRotationPoint(4.0F, 1.0F, -3.0F);
        this.leftArm.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(leftArm, -0.17453292519943295F, 0.0F, -1.0471975511965976F);
        this.teeth_2 = new ModelRenderer(this, 0, 13);
        this.teeth_2.setRotationPoint(0.0F, 1.5F, -3.4F);
        this.teeth_2.addBox(-1.5F, -2.0F, 0.0F, 3, 2, 0, 0.0F);
        this.setRotateAngle(teeth_2, 0.2617993877991494F, 0.0F, 0.0F);
        this.rightArm_1 = new ModelRenderer(this, 25, 0);
        this.rightArm_1.mirror = true;
        this.rightArm_1.setRotationPoint(-2.0F, 1.0F, -1.0F);
        this.rightArm_1.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(rightArm_1, -0.2617993877991494F, 0.0F, 0.2617993877991494F);
        this.spikes_1 = new ModelRenderer(this, 0, 28);
        this.spikes_1.setRotationPoint(-4.9F, 0.0F, -6.9F);
        this.spikes_1.addBox(-1.0F, -5.0F, 0.0F, 1, 10, 0, 0.0F);
        this.setRotateAngle(spikes_1, 0.0F, -0.5235987755982988F, 0.0F);
        this.spikes_7 = new ModelRenderer(this, 0, 24);
        this.spikes_7.setRotationPoint(0.0F, 2.4F, -3.4F);
        this.spikes_7.addBox(-2.5F, 0.0F, 0.0F, 5, 1, 0, 0.0F);
        this.setRotateAngle(spikes_7, -0.5235987755982988F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 4, 37);
        this.tail.setRotationPoint(0.0F, 0.0F, 6.9F);
        this.tail.addBox(0.0F, -2.0F, 0.0F, 0, 4, 4, 0.0F);
        this.setRotateAngle(tail, 0.04363323129985824F, 0.0F, 0.0F);
        this.spikes_4 = new ModelRenderer(this, 0, 21);
        this.spikes_4.setRotationPoint(0.0F, -2.4F, -3.4F);
        this.spikes_4.addBox(-2.5F, -1.0F, 0.0F, 5, 1, 0, 0.0F);
        this.setRotateAngle(spikes_4, 0.5235987755982988F, 0.0F, 0.0F);
        this.collarNormal = new ModelRenderer(this, 14, 14);
        this.collarNormal.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.collarNormal.addBox(-3.0F, -3.0F, -0.5F, 6, 6, 1, 0.0F);
        this.rightLeg = new ModelRenderer(this, 41, 0);
        this.rightLeg.mirror = true;
        this.rightLeg.setRotationPoint(-4.0F, 1.0F, 3.0F);
        this.rightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(rightLeg, 0.17453292519943295F, 0.0F, 1.0471975511965976F);
        this.spikes_5 = new ModelRenderer(this, 0, 0);
        this.spikes_5.setRotationPoint(-2.4F, 0.0F, -3.4F);
        this.spikes_5.addBox(-1.0F, -2.5F, 0.0F, 1, 5, 0, 0.0F);
        this.setRotateAngle(spikes_5, 0.0F, -0.5235987755982988F, 0.0F);
        this.body.addChild(this.leftLeg);
        this.body_1.addChild(this.tail_1);
        this.body_1.addChild(this.glasses_1);
        this.body.addChild(this.collarInflated);
        this.body.addChild(this.teeth);
        this.body_1.addChild(this.leftArm_1);
        this.body.addChild(this.spikes);
        this.body_1.addChild(this.leftLeg_1);
        this.body.addChild(this.spikes_2);
        this.body.addChild(this.rightArm);
        this.body_1.addChild(this.rightLeg_1);
        this.body.addChild(this.spikes_3);
        this.body.addChild(this.teeth_1);
        this.body.addChild(this.glasses);
        this.body_1.addChild(this.spikes_6);
        this.body.addChild(this.leftArm);
        this.body_1.addChild(this.teeth_2);
        this.body_1.addChild(this.rightArm_1);
        this.body.addChild(this.spikes_1);
        this.body_1.addChild(this.spikes_7);
        this.body.addChild(this.tail);
        this.body_1.addChild(this.spikes_4);
        this.body_1.addChild(this.collarNormal);
        this.body.addChild(this.rightLeg);
        this.body_1.addChild(this.spikes_5);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (this.isChild) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * f5, 1.65F * f5);
        }
        if (entity instanceof EntityPufferfishDog && ((EntityPufferfishDog) entity).isInflated()) this.body.render(f5);
        else this.body_1.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        float speed = 0.75f, degree = 1.45f;
          
        if (this.state == State.PARTY)
        {
            float f1 = MathHelper.cos((float)entityIn.ticksExisted);
            float f2 = MathHelper.sin((float)entityIn.ticksExisted);
            this.body_1.rotationPointX = f1;
            this.body_1.rotationPointY = 20.0F + f2;
            
            this.leftLeg_1.rotationPointX = 1.8F - f1;
            this.leftLeg_1.rotationPointY = 1.0F - f2;

            this.leftArm_1.rotationPointX = 2.0F - f1;
            this.leftArm_1.rotationPointY = 1.0F - f2;
            
            this.rightLeg_1.rotationPointX = -1.8F - f1;
            this.rightLeg_1.rotationPointY = 1.0F - f2;

            this.rightArm_1.rotationPointX = -2.0F - f1;
            this.rightArm_1.rotationPointY = 1.0F - f2;
            
            this.tail_1.rotationPointX = f1;
            this.tail_1.rotationPointY = f2;
        }
        else if(this.state == State.SITTING){
            this.rightArm_1.rotateAngleZ = 0.2617993877991494F;
            this.leftLeg_1.rotateAngleX = -1.0821041362364843F;
            this.leftLeg_1.rotateAngleZ = -0.2617993877991494F;
            this.rightLeg_1.rotateAngleX = -1.0821041362364843F;
            this.rightLeg_1.rotateAngleZ = 0.2617993877991494F;
            this.leftArm_1.rotateAngleZ = -0.2617993877991494F;
        } 
        
        else
        {
            this.leftArm_1.rotateAngleX = MathHelper.cos(limbSwing * degree) * speed * limbSwingAmount;
            this.rightArm_1.rotateAngleX = MathHelper.cos(limbSwing * degree + (float) Math.PI) * speed * limbSwingAmount;
            this.leftLeg_1.rotateAngleX = MathHelper.cos(limbSwing * degree + (float) Math.PI) * speed * limbSwingAmount;
            this.rightLeg_1.rotateAngleX = MathHelper.cos(limbSwing * degree) * speed * limbSwingAmount;
        }
    }
    
    
    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
    		
            
    	if (((EntityPufferfishDog)entitylivingbaseIn).isPartying())
        {
            this.state = State.PARTY;
        }
    	else if (((EntityPufferfishDog)entitylivingbaseIn).isSitting())
        {
        	resetRotationPoints();
            this.state = State.SITTING;
        }
        else if (entitylivingbaseIn.motionX != 0 || entitylivingbaseIn.motionY != 0 || entitylivingbaseIn.motionZ != 0)
        {
        	resetRotationPoints();
            this.state = State.WALKING;
        }
        
        else
        {
        	resetRotationPoints();
            this.state = State.STANDING;
        }
    }

    private void resetRotationPoints() {
    	this.tail_1.setRotationPoint(0.0F, 0.0F, 3.1F);
    	this.leftArm_1.setRotationPoint(2.0F, 1.0F, -1.0F);
    	this.leftLeg_1.setRotationPoint(1.8F, 2.0F, 2.5F);
    	this.body_1.setRotationPoint(0.0F, 20.0F, 0.0F);
    	this.rightLeg_1.setRotationPoint(-1.8F, 2.0F, 2.5F);
    	this.rightArm_1.setRotationPoint(-2.0F, 1.0F, -1.0F);
		
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
	
	@SideOnly(Side.CLIENT)
    enum State
    {
        WALKING,
        STANDING,
        SITTING,
        PARTY
    }
}
