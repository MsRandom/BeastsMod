package rando.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelBranchie extends ModelBase {
	public ModelRenderer body;
	public ModelRenderer legLeft;
	public ModelRenderer legRight;
	public ModelRenderer armRight;
	public ModelRenderer armLeft;
	public ModelRenderer top;
	public ModelRenderer handRight;
	public ModelRenderer handLeft;

	public ModelBranchie() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.armLeft = new ModelRenderer(this, 25, 0);
		this.armLeft.setRotationPoint(0.5F, -0.5F, 0.0F);
		this.armLeft.addBox(0.0F, -0.5F, -0.5F, 5, 1, 1, 0.0F);
		this.setRotateAngle(armLeft, 0.0F, -0.7853981633974483F, -0.3490658503988659F);
		this.legRight = new ModelRenderer(this, 26, 4);
		this.legRight.setRotationPoint(-0.2F, 5.0F, 0.0F);
		this.legRight.addBox(-1.0F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
		this.legLeft = new ModelRenderer(this, 26, 4);
		this.legLeft.setRotationPoint(0.2F, 5.0F, 0.0F);
		this.legLeft.addBox(0.0F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
		this.handLeft = new ModelRenderer(this, 7, 7);
		this.handLeft.setRotationPoint(5.0F, 0.0F, 0.0F);
		this.handLeft.addBox(-0.5F, 0.0F, -1.5F, 3, 0, 3, 0.0F);
		this.setRotateAngle(handLeft, 0.0F, 0.0F, 0.4326671215693943F);
		this.armRight = new ModelRenderer(this, 25, 0);
		this.armRight.setRotationPoint(-0.5F, -0.5F, 0.0F);
		this.armRight.addBox(-5.0F, -0.5F, -0.5F, 5, 1, 1, 0.0F);
		this.setRotateAngle(armRight, 0.0F, 0.7853981633974483F, 0.3490658503988659F);
		this.top = new ModelRenderer(this, 10, 0);
		this.top.setRotationPoint(0.0F, -4.5F, 0.0F);
		this.top.addBox(-3.0F, -3.0F, 0.0F, 6, 5, 0, 0.0F);
		this.handRight = new ModelRenderer(this, 15, 7);
		this.handRight.setRotationPoint(-5.0F, 0.0F, 0.0F);
		this.handRight.addBox(-2.5F, 0.0F, -1.5F, 3, 0, 3, 0.0F);
		this.setRotateAngle(handRight, 0.0F, 0.0F, -0.48624872960562016F);
		this.body = new ModelRenderer(this, 0, 0);
		this.body.setRotationPoint(0.0F, 14.0F, 0.0F);
		this.body.addBox(-1.0F, -4.5F, -1.0F, 2, 10, 2, 0.0F);
		this.body.addChild(this.armLeft);
		this.body.addChild(this.legRight);
		this.body.addChild(this.legLeft);
		this.armLeft.addChild(this.handLeft);
		this.body.addChild(this.armRight);
		this.body.addChild(this.top);
		this.armRight.addChild(this.handRight);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        float speed = 0.55f, degree = 0.75f;
        //limbSwing = entityIn.ticksExisted;
        //limbSwingAmount = 0.6F;
        this.armLeft.rotateAngleZ = MathHelper.cos(limbSwing * speed) * degree * limbSwingAmount;
        this.handLeft.rotateAngleX = MathHelper.cos(limbSwing * speed + (float) Math.PI) * degree * limbSwingAmount;
        this.armRight.rotateAngleZ = MathHelper.cos(limbSwing * speed) * degree * limbSwingAmount;
        this.handRight.rotateAngleX = MathHelper.cos(limbSwing * speed + (float) Math.PI)* degree * limbSwingAmount;
        this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * speed + (float) Math.PI) * degree * limbSwingAmount;
        this.legRight.rotateAngleX = MathHelper.cos(limbSwing * speed) * degree * limbSwingAmount;   
    }
    
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
