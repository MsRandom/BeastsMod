package rando.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * Undermole - Codabear & Bolcko
 * Created using Tabula 7.0.0
 */
public class ModelUndermole extends ModelBase {
    public ModelRenderer Body;
    public ModelRenderer Stomach;
    public ModelRenderer Head;
    public ModelRenderer LeftArmBase;
    public ModelRenderer RightArmBase;
    public ModelRenderer Hips;
    public ModelRenderer LeftLegBase;
    public ModelRenderer RightLegBase;
    public ModelRenderer LeftLeg;
    public ModelRenderer LeftFoot;
    public ModelRenderer LeftToe1;
    public ModelRenderer LeftToe2;
    public ModelRenderer RightLeg;
    public ModelRenderer RightFoot;
    public ModelRenderer RightToe1;
    public ModelRenderer RightToe2;
    public ModelRenderer SnoutBase;
    public ModelRenderer HelmetMiddle;
    public ModelRenderer HelmetTop;
    public ModelRenderer HelmetLight;
    public ModelRenderer SnoutTip;
    public ModelRenderer Jaw;
    public ModelRenderer Nose;
    public ModelRenderer HelmetLeft;
    public ModelRenderer HelmetRight;
    public ModelRenderer LeftArm;
    public ModelRenderer LeftFinger1;
    public ModelRenderer LeftFinger2;
    public ModelRenderer LeftFinger3;
    public ModelRenderer LeftArmBrace;
    public ModelRenderer RightArm;
    public ModelRenderer RightFinger1;
    public ModelRenderer RightFinger2;
    public ModelRenderer LeftFinger3_1;
    public ModelRenderer RightArmBrace;

    public ModelUndermole() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.RightArmBrace = new ModelRenderer(this, 0, 52);
        this.RightArmBrace.setRotationPoint(1.0F, 3.0F, -2.0F);
        this.RightArmBrace.addBox(-2.0F, -1.5F, -0.5F, 4, 3, 4, 0.0F);
        this.LeftFoot = new ModelRenderer(this, 24, 14);
        this.LeftFoot.setRotationPoint(0.0F, 2.5F, 1.0F);
        this.LeftFoot.addBox(-1.5F, 0.0F, -2.5F, 3, 2, 4, 0.0F);
        this.setRotateAngle(LeftFoot, -0.2617993877991494F, 0.0F, 0.17453292519943295F);
        this.RightFinger1 = new ModelRenderer(this, 0, 2);
        this.RightFinger1.setRotationPoint(-0.2F, 5.0F, -1.2F);
        this.RightFinger1.addBox(0.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(RightFinger1, 0.0F, 0.0F, -0.3490658503988659F);
        this.RightToe2 = new ModelRenderer(this, 0, 14);
        this.RightToe2.setRotationPoint(0.5F, 0.1F, -2.5F);
        this.RightToe2.addBox(-0.7F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(RightToe2, 0.0F, -0.2617993877991494F, 0.03490658503988659F);
        this.LeftFinger1 = new ModelRenderer(this, 0, 2);
        this.LeftFinger1.setRotationPoint(0.2F, 5.0F, -1.2F);
        this.LeftFinger1.addBox(-1.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(LeftFinger1, 0.0F, 0.0F, 0.3490658503988659F);
        this.RightArm = new ModelRenderer(this, 45, 33);
        this.RightArm.setRotationPoint(0.0F, 5.0F, 1.0F);
        this.RightArm.addBox(-0.5F, 0.0F, -2.0F, 3, 5, 3, 0.0F);
        this.setRotateAngle(RightArm, -0.7853981633974483F, 0.0F, -0.2617993877991494F);
        this.RightFoot = new ModelRenderer(this, 24, 14);
        this.RightFoot.setRotationPoint(0.0F, 2.5F, 1.0F);
        this.RightFoot.addBox(-1.5F, 0.0F, -2.5F, 3, 2, 4, 0.0F);
        this.setRotateAngle(RightFoot, -0.2617993877991494F, 0.0F, -0.17453292519943295F);
        this.Head = new ModelRenderer(this, 0, 14);
        this.Head.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.Head.addBox(-4.0F, -6.0F, -4.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Head, -0.17453292519943295F, 0.0F, 0.0F);
        this.Hips = new ModelRenderer(this, 32, 14);
        this.Hips.setRotationPoint(0.0F, 4.0F, -4.0F);
        this.Hips.addBox(-3.5F, 0.0F, 0.0F, 7, 2, 7, 0.0F);
        this.setRotateAngle(Hips, 0.08726646259971647F, 0.0F, 0.0F);
        this.RightFinger2 = new ModelRenderer(this, 0, 2);
        this.RightFinger2.setRotationPoint(-0.2F, 5.0F, 0.2F);
        this.RightFinger2.addBox(0.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(RightFinger2, 0.0F, 0.0F, -0.3490658503988659F);
        this.LeftFinger2 = new ModelRenderer(this, 0, 2);
        this.LeftFinger2.setRotationPoint(0.2F, 5.0F, 0.2F);
        this.LeftFinger2.addBox(-1.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(LeftFinger2, 0.0F, 0.0F, 0.3490658503988659F);
        this.LeftToe1 = new ModelRenderer(this, 0, 14);
        this.LeftToe1.setRotationPoint(-0.5F, 0.1F, -2.5F);
        this.LeftToe1.addBox(-1.3F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(LeftToe1, 0.0F, 0.2617993877991494F, -0.03490658503988659F);
        this.HelmetTop = new ModelRenderer(this, 28, 33);
        this.HelmetTop.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.HelmetTop.addBox(-2.0F, -1.0F, -4.5F, 4, 2, 9, 0.0F);
        this.setRotateAngle(HelmetTop, 0.08726646259971647F, 0.0F, 0.0F);
        this.LeftFinger3 = new ModelRenderer(this, 0, 2);
        this.LeftFinger3.setRotationPoint(-2.2F, 5.0F, -0.5F);
        this.LeftFinger3.addBox(0.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(LeftFinger3, 0.0F, 0.0F, -0.3490658503988659F);
        this.LeftFinger3_1 = new ModelRenderer(this, 0, 2);
        this.LeftFinger3_1.setRotationPoint(2.2F, 5.0F, -0.5F);
        this.LeftFinger3_1.addBox(-1.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(LeftFinger3_1, 0.0F, 0.0F, 0.3490658503988659F);
        this.RightArmBase = new ModelRenderer(this, 26, 0);
        this.RightArmBase.setRotationPoint(-3.5F, -2.0F, 0.0F);
        this.RightArmBase.addBox(0.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(RightArmBase, 0.0F, 0.0F, 0.6108652381980153F);
        this.SnoutBase = new ModelRenderer(this, 0, 29);
        this.SnoutBase.setRotationPoint(0.0F, 0.5F, -4.0F);
        this.SnoutBase.addBox(-3.0F, -3.5F, -1.0F, 6, 4, 3, 0.0F);
        this.setRotateAngle(SnoutBase, -0.22689280275926282F, 0.0F, 0.0F);
        this.LeftArm = new ModelRenderer(this, 45, 33);
        this.LeftArm.setRotationPoint(0.0F, 5.0F, 1.0F);
        this.LeftArm.addBox(-2.5F, 0.0F, -2.0F, 3, 5, 3, 0.0F);
        this.setRotateAngle(LeftArm, -0.7853981633974483F, 0.0F, 0.2617993877991494F);
        this.HelmetLeft = new ModelRenderer(this, 0, 41);
        this.HelmetLeft.setRotationPoint(4.0F, 1.0F, 0.0F);
        this.HelmetLeft.addBox(-2.0F, 0.0F, -5.0F, 3, 1, 10, 0.0F);
        this.LeftArmBase = new ModelRenderer(this, 26, 0);
        this.LeftArmBase.setRotationPoint(3.5F, -2.0F, 0.0F);
        this.LeftArmBase.addBox(-2.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(LeftArmBase, 0.0F, 0.0F, -0.6108652381980153F);
        this.RightToe1 = new ModelRenderer(this, 0, 14);
        this.RightToe1.setRotationPoint(-0.5F, 0.1F, -2.5F);
        this.RightToe1.addBox(-1.3F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(RightToe1, 0.0F, 0.2617993877991494F, -0.03490658503988659F);
        this.Stomach = new ModelRenderer(this, 26, 0);
        this.Stomach.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.Stomach.addBox(-4.0F, -2.0F, -4.5F, 8, 6, 8, 0.0F);
        this.setRotateAngle(Stomach, -0.2617993877991494F, 0.0F, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 50, 0);
        this.LeftLeg.setRotationPoint(-2.0F, 5.0F, -1.0F);
        this.LeftLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LeftLeg, 0.6981317007977318F, 0.0F, 0.0F);
        this.HelmetRight = new ModelRenderer(this, 0, 41);
        this.HelmetRight.setRotationPoint(-4.0F, 1.0F, 0.0F);
        this.HelmetRight.addBox(-1.0F, 0.0F, -5.0F, 3, 1, 10, 0.0F);
        this.HelmetLight = new ModelRenderer(this, 48, 23);
        this.HelmetLight.setRotationPoint(0.0F, -6.0F, -1.0F);
        this.HelmetLight.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F);
        this.HelmetMiddle = new ModelRenderer(this, 7, 29);
        this.HelmetMiddle.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.HelmetMiddle.addBox(-2.0F, 0.0F, -6.0F, 4, 1, 11, 0.0F);
        this.setRotateAngle(HelmetMiddle, 0.08726646259971647F, 0.0F, 0.0F);
        this.SnoutTip = new ModelRenderer(this, 48, 29);
        this.SnoutTip.setRotationPoint(0.0F, -3.5F, -1.0F);
        this.SnoutTip.addBox(-2.0F, 0.0F, -1.5F, 4, 2, 2, 0.0F);
        this.setRotateAngle(SnoutTip, 0.4363323129985824F, 0.0F, 0.0F);
        this.Nose = new ModelRenderer(this, 20, 0);
        this.Nose.setRotationPoint(0.0F, -0.2F, -0.7F);
        this.Nose.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 1, 0.0F);
        this.Jaw = new ModelRenderer(this, 0, 0);
        this.Jaw.setRotationPoint(0.0F, 2.0F, -0.5F);
        this.Jaw.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(Jaw, -0.2617993877991494F, 0.0F, 0.0F);
        this.RightLegBase = new ModelRenderer(this, 32, 23);
        this.RightLegBase.setRotationPoint(-4.0F, 2.0F, 0.0F);
        this.RightLegBase.addBox(0.0F, -1.0F, -2.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(RightLegBase, -0.3490658503988659F, 0.0F, 0.17453292519943295F);
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.setRotationPoint(0.0F, 10.6F, 0.0F);
        this.Body.addBox(-3.5F, -4.0F, -3.0F, 7, 8, 6, 0.0F);
        this.setRotateAngle(Body, 0.17453292519943295F, 0.0F, 0.0F);
        this.LeftToe2 = new ModelRenderer(this, 0, 14);
        this.LeftToe2.setRotationPoint(0.5F, 0.1F, -2.5F);
        this.LeftToe2.addBox(-0.7F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(LeftToe2, 0.0F, -0.2617993877991494F, 0.03490658503988659F);
        this.LeftLegBase = new ModelRenderer(this, 32, 23);
        this.LeftLegBase.setRotationPoint(4.0F, 2.0F, 0.0F);
        this.LeftLegBase.addBox(-4.0F, -1.0F, -2.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(LeftLegBase, -0.3490658503988659F, 0.0F, -0.17453292519943295F);
        this.RightLeg = new ModelRenderer(this, 50, 0);
        this.RightLeg.setRotationPoint(2.0F, 5.0F, -1.0F);
        this.RightLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(RightLeg, 0.6981317007977318F, 0.0F, 0.0F);
        this.LeftArmBrace = new ModelRenderer(this, 0, 52);
        this.LeftArmBrace.setRotationPoint(-1.0F, 3.0F, -2.0F);
        this.LeftArmBrace.addBox(-2.0F, -1.5F, -0.5F, 4, 3, 4, 0.0F);
        this.RightArm.addChild(this.RightArmBrace);
        this.LeftLeg.addChild(this.LeftFoot);
        this.RightArm.addChild(this.RightFinger1);
        this.RightFoot.addChild(this.RightToe2);
        this.LeftArm.addChild(this.LeftFinger1);
        this.RightArmBase.addChild(this.RightArm);
        this.RightLeg.addChild(this.RightFoot);
        this.Body.addChild(this.Head);
        this.Stomach.addChild(this.Hips);
        this.RightArm.addChild(this.RightFinger2);
        this.LeftArm.addChild(this.LeftFinger2);
        this.LeftFoot.addChild(this.LeftToe1);
        this.Head.addChild(this.HelmetTop);
        this.LeftArm.addChild(this.LeftFinger3);
        this.RightArm.addChild(this.LeftFinger3_1);
        this.Body.addChild(this.RightArmBase);
        this.Head.addChild(this.SnoutBase);
        this.LeftArmBase.addChild(this.LeftArm);
        this.HelmetMiddle.addChild(this.HelmetLeft);
        this.Body.addChild(this.LeftArmBase);
        this.RightFoot.addChild(this.RightToe1);
        this.Body.addChild(this.Stomach);
        this.LeftLegBase.addChild(this.LeftLeg);
        this.HelmetMiddle.addChild(this.HelmetRight);
        this.Head.addChild(this.HelmetLight);
        this.Head.addChild(this.HelmetMiddle);
        this.SnoutBase.addChild(this.SnoutTip);
        this.SnoutTip.addChild(this.Nose);
        this.SnoutTip.addChild(this.Jaw);
        this.Stomach.addChild(this.RightLegBase);
        this.LeftFoot.addChild(this.LeftToe2);
        this.Stomach.addChild(this.LeftLegBase);
        this.RightLegBase.addChild(this.RightLeg);
        this.LeftArm.addChild(this.LeftArmBrace);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.Body.render(f5);
    }

    @Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
		float speed = 2.1F, degree = 1.3F;
		
        if(((EntityLivingBase)entity).limbSwingAmount < 0.2) {
			limbSwing = entity.ticksExisted;
			limbSwingAmount = 0.3F;
	    	
			this.Head.rotateAngleX = MathHelper.cos(20 - limbSwing * (0.0562F * speed) + (float)Math.PI) * (degree * 0.05F) * limbSwingAmount * 0.5F - 0.2F;
			this.SnoutBase.rotateAngleX = MathHelper.cos(limbSwing * (0.0562F * speed) + (float)Math.PI) * (degree * 0.1F) * limbSwingAmount * 0.5F - 0.2F;
	    	
			this.LeftArmBase.rotateAngleX = MathHelper.cos(40 + limbSwing * (0.0562F * speed) + (float)Math.PI) * (-degree * 0.3F) * limbSwingAmount * 0.5F - 0.0F;
			this.RightArmBase.rotateAngleX = MathHelper.cos(40 + limbSwing * (0.0562F * speed) + (float)Math.PI) * (degree * 0.3F) * limbSwingAmount * 0.5F - 0.0F;
	    	
			this.LeftArm.rotateAngleX = MathHelper.cos(40 + limbSwing * (0.0562F * speed) + (float)Math.PI) * (-degree * 0.3F) * limbSwingAmount * 0.5F - 0.9F;
			this.RightArm.rotateAngleX = MathHelper.cos(40 + limbSwing * (0.0562F * speed) + (float)Math.PI) * (degree * 0.3F) * limbSwingAmount * 0.5F - 0.9F;
	    	
		}
		else {
	    	speed = 4.4F;
	    	this.Body.offsetY += 0.02F;
			this.Head.rotateAngleX = MathHelper.cos(20 + limbSwing * (0.1962F * speed) + (float)Math.PI) * (degree * 0.2F) * limbSwingAmount * 0.5F - 0.2F;
	    	
			this.Body.offsetY = MathHelper.sin(limbSwing * (0.1862F * speed)) * -limbSwingAmount * 0.02F + 0.02F;
			
			this.LeftArmBase.rotateAngleX = MathHelper.cos(40 + limbSwing * (0.1562F * speed) + (float)Math.PI) * (-degree * 1.9F) * limbSwingAmount * 0.5F - 0.0F;
			this.RightArmBase.rotateAngleX = MathHelper.cos(40 + limbSwing * (0.1562F * speed) + (float)Math.PI) * (degree * 1.9F) * limbSwingAmount * 0.5F - 0.0F;
	    	
			this.LeftLegBase.rotateAngleX = MathHelper.cos(40 + limbSwing * (0.1562F * speed) + (float)Math.PI) * (degree * 1.2F) * limbSwingAmount * 0.5F - 0.2F;
            this.LeftLegBase.offsetY = MathHelper.sin(limbSwing * (0.1462F * speed)) * limbSwingAmount * 0.05F - 0.05F;
            this.LeftLeg.rotateAngleX = MathHelper.cos(20 + limbSwing * (0.1562F * speed) + (float)Math.PI) * (degree * 1.1F) * limbSwingAmount * 0.5F + 0.8F;
            this.LeftFoot.rotateAngleX = MathHelper.cos(20 + limbSwing * (0.1562F * speed) + (float)Math.PI) * (-degree * 0.3F) * limbSwingAmount * 0.5F - 0.4F;

            this.RightLegBase.rotateAngleX = MathHelper.cos(40 + limbSwing * (0.1562F * speed) + (float)Math.PI) * (-degree * 1.2F) * limbSwingAmount * 0.5F - 0.2F;
            this.RightLegBase.offsetY = MathHelper.sin(limbSwing * (0.1462F * speed)) * limbSwingAmount * 0.05F - 0.05F;
            this.RightLeg.rotateAngleX = MathHelper.cos(20 + limbSwing * (0.1562F * speed) + (float)Math.PI) * (-degree * 1.1F) * limbSwingAmount * 0.5F + 0.8F;
            this.RightFoot.rotateAngleX = MathHelper.cos(20 + limbSwing * (0.1562F * speed) + (float)Math.PI) * (degree * 0.3F) * limbSwingAmount * 0.5F - 0.4F;
		}
	}
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
