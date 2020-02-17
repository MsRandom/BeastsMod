package random.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * ModelAnglerQueen - Coda
 * Created using Tabula 7.1.0
 */
public class ModelAnglerQueen extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer hips;
    public ModelRenderer armRight;
    public ModelRenderer legLeft;
    public ModelRenderer armLeft;
    public ModelRenderer legRight;
    public ModelRenderer neckJoint;
    public ModelRenderer tail;
    public ModelRenderer jaw;
    public ModelRenderer mouth;
    public ModelRenderer teeth;
    public ModelRenderer lure;
    public ModelRenderer light;

    public ModelAnglerQueen() {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.neckJoint = new ModelRenderer(this, 0, 0);
        this.neckJoint.setRotationPoint(0.0F, 0.0F, -16.0F);
        this.neckJoint.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.legLeft = new ModelRenderer(this, 0, 0);
        this.legLeft.setRotationPoint(9.0F, 14.5F, 11.0F);
        this.legLeft.addBox(-4.0F, 0.0F, -4.0F, 8, 16, 8, 0.0F);
        this.armRight = new ModelRenderer(this, 0, 0);
        this.armRight.setRotationPoint(-9.0F, 14.5F, -9.0F);
        this.armRight.addBox(-4.0F, 0.0F, -4.0F, 8, 16, 8, 0.0F);
        this.light = new ModelRenderer(this, 120, 0);
        this.light.setRotationPoint(0.0F, 0.0F, -33.0F);
        this.light.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0.0F);
        this.jaw = new ModelRenderer(this, 0, 62);
        this.jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jaw.addBox(-15.0F, 0.0F, -30.0F, 30, 20, 30, 0.0F);
        this.legRight = new ModelRenderer(this, 0, 0);
        this.legRight.setRotationPoint(-9.0F, 14.5F, 11.0F);
        this.legRight.addBox(-4.0F, 0.0F, -4.0F, 8, 16, 8, 0.0F);
        this.tail = new ModelRenderer(this, 192, 30);
        this.tail.setRotationPoint(0.0F, 0.0F, 24.0F);
        this.tail.addBox(0.0F, -12.0F, 0.0F, 1, 24, 20, 0.0F);
        this.teeth = new ModelRenderer(this, 0, 112);
        this.teeth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth.addBox(-14.0F, -5.0F, -29.0F, 28, 5, 29, 0.0F);
        this.mouth = new ModelRenderer(this, 96, 44);
        this.mouth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.mouth.addBox(-12.0F, -14.0F, -24.0F, 24, 14, 24, 0.0F);
        this.armLeft = new ModelRenderer(this, 0, 0);
        this.armLeft.setRotationPoint(9.0F, 14.5F, -9.0F);
        this.armLeft.addBox(-4.0F, 0.0F, -4.0F, 8, 16, 8, 0.0F);
        this.hips = new ModelRenderer(this, 120, 0);
        this.hips.setRotationPoint(0.0F, -4.0F, 15.5F);
        this.hips.addBox(-8.0F, -10.0F, 0.0F, 16, 20, 24, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, -6.5F, 0.0F);
        this.body.addBox(-14.0F, -15.0F, -16.0F, 28, 30, 32, 0.0F);
        this.lure = new ModelRenderer(this, 158, 74);
        this.lure.setRotationPoint(0.0F, -14.0F, -6.0F);
        this.lure.addBox(0.0F, -14.0F, -34.0F, 1, 14, 34, 0.0F);
        this.body.addChild(this.neckJoint);
        this.body.addChild(this.legLeft);
        this.body.addChild(this.armRight);
        this.lure.addChild(this.light);
        this.neckJoint.addChild(this.jaw);
        this.body.addChild(this.legRight);
        this.hips.addChild(this.tail);
        this.jaw.addChild(this.teeth);
        this.neckJoint.addChild(this.mouth);
        this.body.addChild(this.armLeft);
        this.body.addChild(this.hips);
        this.mouth.addChild(this.lure);
    }
    
    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float f, float f1, float partialTickTime) {
    	float speed = 1.0f;
    	float degree = 1.0f;
    	this.hips.rotateAngleY = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.4F) * f1 * 0.5F;
    	this.tail.rotateAngleY = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.6F) * f1 * 0.5F;
    	this.armRight.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.8F) * f1 * 0.5F;
    	this.legLeft.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.8F) * f1 * 0.5F;
    	this.legRight.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * -0.8F) * f1 * 0.5F;
    	this.armLeft.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * -0.8F) * f1 * 0.5F;
    	this.neckJoint.offsetY = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.2F) * f1 * 0.5F;
    	this.lure.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.2F) * f1 * 0.5F;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
