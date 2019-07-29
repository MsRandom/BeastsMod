package rando.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import rando.beasts.common.entity.EntityMonkeyGremlin;

public class ModelMonkeyGremlin extends ModelBase {
    public ModelRenderer Body;
    public ModelRenderer LeftLeg;
    public ModelRenderer RightLeg;
    public ModelRenderer Tail1;
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;
    public ModelRenderer Head;
    public ModelRenderer Tail2;
    public ModelRenderer RightEar;
    public ModelRenderer LeftEar;

    public ModelMonkeyGremlin() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Head = new ModelRenderer(this, 12, 21);
        this.Head.setRotationPoint(0.0F, -3.0F, 1.0F);
        this.Head.addBox(-3.0F, -2.5F, -3.0F, 6, 5, 6, 0.0F);
        this.setRotateAngle(Head, -1.5707963267948966F, 0.0F, 0.0F);
        this.RightArm = new ModelRenderer(this, 0, 15);
        this.RightArm.setRotationPoint(-3.0F, 2.0F, 0.0F);
        this.RightArm.addBox(-2.0F, -1.0F, -1.5F, 3, 10, 3, 0.0F);
        this.setRotateAngle(RightArm, -1.5707963267948966F, 0.0F, 0.0F);
        this.Tail1 = new ModelRenderer(this, 56, 18);
        this.Tail1.setRotationPoint(0.0F, 8.5F, 0.4F);
        this.Tail1.addBox(-1.0F, -12.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(Tail1, -2.5132741228718345F, 0.0F, 0.0F);
        this.LeftEar = new ModelRenderer(this, 0, 28);
        this.LeftEar.mirror = true;
        this.LeftEar.setRotationPoint(3.0F, 0.0F, 1.0F);
        this.LeftEar.addBox(0.0F, -1.5F, 0.0F, 5, 3, 1, 0.0F);
        this.setRotateAngle(LeftEar, 0.0F, -0.4363323129985824F, 0.0F);
        this.RightEar = new ModelRenderer(this, 0, 28);
        this.RightEar.setRotationPoint(-3.0F, 0.0F, 1.0F);
        this.RightEar.addBox(-5.0F, -1.5F, 0.0F, 5, 3, 1, 0.0F);
        this.setRotateAngle(RightEar, 0.0F, 0.4363323129985824F, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 0, 4);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setRotationPoint(2.0F, 8.5F, -1.0F);
        this.LeftLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
        this.setRotateAngle(LeftLeg, -1.5707963267948966F, 0.0F, 0.0F);
        this.RightLeg = new ModelRenderer(this, 0, 4);
        this.RightLeg.setRotationPoint(-2.0F, 8.5F, -1.0F);
        this.RightLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
        this.setRotateAngle(RightLeg, -1.5707963267948966F, 0.0F, 0.0F);
        this.Tail2 = new ModelRenderer(this, 56, 4);
        this.Tail2.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.Tail2.addBox(-1.0F, -12.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(Tail2, 0.7853981633974483F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 36, 18);
        this.Body.setRotationPoint(0.0F, 15.0F, -5.0F);
        this.Body.addBox(-2.5F, 0.0F, -2.5F, 5, 9, 5, 0.0F);
        this.setRotateAngle(Body, 1.5707963267948966F, 0.0F, 0.0F);
        this.LeftArm = new ModelRenderer(this, 0, 15);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(3.0F, 2.0F, 0.0F);
        this.LeftArm.addBox(-1.0F, -1.0F, -1.5F, 3, 10, 3, 0.0F);
        this.setRotateAngle(LeftArm, -1.5707963267948966F, 0.0F, 0.0F);
        this.Body.addChild(this.Head);
        this.Body.addChild(this.RightArm);
        this.Body.addChild(this.Tail1);
        this.Head.addChild(this.LeftEar);
        this.Head.addChild(this.RightEar);
        this.Body.addChild(this.LeftLeg);
        this.Body.addChild(this.RightLeg);
        this.Tail1.addChild(this.Tail2);
        this.Body.addChild(this.LeftArm);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Body.render(f5);
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
		
        if(entity == null) 
			return;

		float speed = 2.4F, degree = 1.3F;
        
        if(entity instanceof EntityMonkeyGremlin) {
            EntityMonkeyGremlin e = (EntityMonkeyGremlin) entity;
            
            if(e.limbSwingAmount < 0.2) {
            	limbSwing = entity.ticksExisted;
	            limbSwingAmount = 0.3F;
	            speed = 1.1F;
				this.Tail1.rotateAngleX = MathHelper.cos(0 + limbSwing * (0.0962F * speed) + (float)Math.PI) * (-degree * 0.4F) * limbSwingAmount * 0.5F - 2.5F;
				this.Tail2.rotateAngleX = MathHelper.cos(20 + limbSwing * (0.0962F * speed) + (float)Math.PI) * (-degree * 0.4F) * limbSwingAmount * 0.5F - 5.5F;
				this.Head.rotateAngleX = MathHelper.cos(20 + limbSwing * (0.1662F * speed) + (float)Math.PI) * (degree * 0.1F) * limbSwingAmount * 0.5F - 1.5F;
				this.LeftEar.rotateAngleY = MathHelper.cos(20 + limbSwing * (0.1662F * speed) + (float)Math.PI) * (degree * 0.1F) * limbSwingAmount * 0.5F - 0.5F;
				this.RightEar.rotateAngleY = MathHelper.cos(20 + limbSwing * (0.1662F * speed) + (float)Math.PI) * (-degree * 0.1F) * limbSwingAmount * 0.5F + 0.5F;
            }
            else {
	            this.Head.rotateAngleX = MathHelper.cos(20 + limbSwing * (0.1362F * speed) + (float)Math.PI) * (degree * 0.1F) * limbSwingAmount * 0.5F - 1.5F;
				this.Body.offsetY = MathHelper.sin(limbSwing * (0.2162F * speed)) * -limbSwingAmount * 0.01F - 0.0F;
				this.LeftArm.rotateAngleX = MathHelper.cos(40 + limbSwing * (0.1562F * speed) + (float)Math.PI) * (-degree * 1.9F) * limbSwingAmount * 0.5F - 1.5F;
				this.RightArm.rotateAngleX = MathHelper.cos(40 + limbSwing * (0.1562F * speed) + (float)Math.PI) * (degree * 1.9F) * limbSwingAmount * 0.5F - 1.5F;
				this.LeftLeg.rotateAngleX = MathHelper.cos(40 + limbSwing * (0.1562F * speed) + (float)Math.PI) * (degree * 1.9F) * limbSwingAmount * 0.5F - 1.5F;
				this.RightLeg.rotateAngleX = MathHelper.cos(40 + limbSwing * (0.1562F * speed) + (float)Math.PI) * (-degree * 1.9F) * limbSwingAmount * 0.5F - 1.5F;
				this.Tail1.rotateAngleX = MathHelper.cos(0 + limbSwing * (0.1062F * speed) + (float)Math.PI) * (-degree * 0.6F) * limbSwingAmount * 0.5F - 2.5F;
				this.Tail2.rotateAngleX = MathHelper.cos(20 + limbSwing * (0.1062F * speed) + (float)Math.PI) * (-degree * 0.6F) * limbSwingAmount * 0.5F - 5.5F;
            }
        }
        
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
