package rando.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelLandwhale extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;
    public ModelRenderer legLeft;
    public ModelRenderer coral1;
    public ModelRenderer coral2;
    public ModelRenderer coral3;
    public ModelRenderer coral4;
    public ModelRenderer coral5;
    public ModelRenderer coral6;
    public ModelRenderer coral7;
    public ModelRenderer coral8;
    public ModelRenderer legRight;
    public ModelRenderer mouth;
    public ModelRenderer tailBase;
    public ModelRenderer jaw;
    public ModelRenderer tail;
    public ModelRenderer flukeLeft;
    public ModelRenderer flukeRight;

    public ModelLandwhale() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.coral6 = new ModelRenderer(this, 0, 12);
        this.coral6.setRotationPoint(4.0F, -8.0F, 11.0F);
        this.coral6.addBox(-3.0F, -6.0F, 0.0F, 6, 6, 0, 0.0F);
        this.setRotateAngle(coral6, 0.0F, 0.7853981633974483F, 0.0F);
        this.tail = new ModelRenderer(this, 18, 64);
        this.tail.setRotationPoint(0.0F, 0.0F, 11.0F);
        this.tail.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 12, 0.0F);
        this.setRotateAngle(tail, 0.2617993877991494F, 0.0F, 0.0F);
        this.coral1 = new ModelRenderer(this, 80, 86);
        this.coral1.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.coral1.addBox(-8.0F, -16.0F, 0.0F, 16, 16, 0, 0.0F);
        this.setRotateAngle(coral1, 0.0F, 0.7853981633974483F, 0.0F);
        this.flukeRight = new ModelRenderer(this, 58, 73);
        this.flukeRight.setRotationPoint(-1.0F, 0.0F, 8.0F);
        this.flukeRight.addBox(-12.0F, -1.5F, -4.0F, 12, 3, 8, 0.0F);
        this.setRotateAngle(flukeRight, 0.0F, 0.4363323129985824F, 0.0F);
        this.flukeLeft = new ModelRenderer(this, 58, 73);
        this.flukeLeft.mirror = true;
        this.flukeLeft.setRotationPoint(1.0F, 0.0F, 8.0F);
        this.flukeLeft.addBox(0.0F, -1.5F, -4.0F, 12, 3, 8, 0.0F);
        this.setRotateAngle(flukeLeft, 0.0F, -0.4363323129985824F, 0.0F);
        this.armLeft = new ModelRenderer(this, 60, 0);
        this.armLeft.setRotationPoint(4.0F, 6.0F, -5.0F);
        this.armLeft.addBox(-2.0F, -4.0F, -4.0F, 7, 14, 8, 0.0F);
        this.armRight = new ModelRenderer(this, 60, 0);
        this.armRight.mirror = true;
        this.armRight.setRotationPoint(-4.0F, 6.0F, -5.0F);
        this.armRight.addBox(-5.0F, -4.0F, -4.0F, 7, 14, 8, 0.0F);
        this.coral8 = new ModelRenderer(this, 0, 20);
        this.coral8.setRotationPoint(0.0F, -8.0F, -10.0F);
        this.coral8.addBox(-3.0F, -6.0F, 0.0F, 6, 6, 0, 0.0F);
        this.setRotateAngle(coral8, 0.0F, 0.7853981633974483F, 0.0F);
        this.mouth = new ModelRenderer(this, 62, 48);
        this.mouth.setRotationPoint(0.0F, -2.0F, -10.0F);
        this.mouth.addBox(-7.0F, -5.0F, -17.0F, 14, 9, 16, 0.0F);
        this.coral5 = new ModelRenderer(this, 0, 12);
        this.coral5.setRotationPoint(4.0F, -8.0F, 11.0F);
        this.coral5.addBox(-3.0F, -6.0F, 0.0F, 6, 6, 0, 0.0F);
        this.setRotateAngle(coral5, 0.0F, -0.7853981633974483F, 0.0F);
        this.coral2 = new ModelRenderer(this, 80, 86);
        this.coral2.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.coral2.addBox(-8.0F, -16.0F, 0.0F, 16, 16, 0, 0.0F);
        this.setRotateAngle(coral2, 0.0F, -0.7853981633974483F, 0.0F);
        this.tailBase = new ModelRenderer(this, 30, 42);
        this.tailBase.setRotationPoint(0.0F, -0.5F, 14.0F);
        this.tailBase.addBox(-6.0F, -5.0F, 0.0F, 12, 10, 12, 0.0F);
        this.setRotateAngle(tailBase, -0.2617993877991494F, 0.0F, 0.0F);
        this.coral3 = new ModelRenderer(this, 0, 0);
        this.coral3.setRotationPoint(-4.0F, -8.0F, 11.0F);
        this.coral3.addBox(-5.0F, -10.0F, 0.0F, 10, 10, 0, 0.0F);
        this.setRotateAngle(coral3, 0.0F, 0.7853981633974483F, 0.0F);
        this.coral4 = new ModelRenderer(this, 0, 0);
        this.coral4.setRotationPoint(-4.0F, -8.0F, 11.0F);
        this.coral4.addBox(-5.0F, -10.0F, 0.0F, 10, 10, 0, 0.0F);
        this.setRotateAngle(coral4, 0.0F, -0.7853981633974483F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 8.0F, -3.0F);
        this.body.addBox(-8.0F, -8.0F, -12.0F, 16, 14, 28, 0.0F);
        this.legRight = new ModelRenderer(this, 92, 0);
        this.legRight.mirror = true;
        this.legRight.setRotationPoint(-4.0F, 6.0F, 10.0F);
        this.legRight.addBox(-5.0F, -6.0F, -4.0F, 7, 16, 8, 0.0F);
        this.jaw = new ModelRenderer(this, 20, 84);
        this.jaw.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.jaw.addBox(-8.0F, -3.0F, -18.0F, 16, 10, 18, 0.0F);
        this.legLeft = new ModelRenderer(this, 92, 0);
        this.legLeft.setRotationPoint(4.0F, 6.0F, 10.0F);
        this.legLeft.addBox(-2.0F, -6.0F, -4.0F, 7, 16, 8, 0.0F);
        this.coral7 = new ModelRenderer(this, 0, 20);
        this.coral7.setRotationPoint(0.0F, -8.0F, -10.0F);
        this.coral7.addBox(-3.0F, -6.0F, 0.0F, 6, 6, 0, 0.0F);
        this.setRotateAngle(coral7, 0.0F, -0.7853981633974483F, 0.0F);
        this.body.addChild(this.coral6);
        this.tailBase.addChild(this.tail);
        this.body.addChild(this.coral1);
        this.tail.addChild(this.flukeRight);
        this.tail.addChild(this.flukeLeft);
        this.body.addChild(this.armLeft);
        this.body.addChild(this.armRight);
        this.body.addChild(this.coral8);
        this.body.addChild(this.mouth);
        this.body.addChild(this.coral5);
        this.body.addChild(this.coral2);
        this.body.addChild(this.tailBase);
        this.body.addChild(this.coral3);
        this.body.addChild(this.coral4);
        this.body.addChild(this.legRight);
        this.mouth.addChild(this.jaw);
        this.body.addChild(this.legLeft);
        this.body.addChild(this.coral7);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if(((EntityLivingBase)entity).isChild()) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
        }
        if (entity.isSneaking()) GlStateManager.translate(0.0F, 0.2F, 0.0F);
        this.body.render(f5);
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        float speed = 0.55f, degree = 0.75f;
        this.armLeft.rotateAngleX = MathHelper.cos(limbSwing * speed * 2.0F) * degree * limbSwingAmount;
        this.armRight.rotateAngleX = MathHelper.cos(limbSwing * speed + (float) Math.PI) * degree * limbSwingAmount;
        this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * speed + (float) Math.PI) * degree * limbSwingAmount;
        this.legRight.rotateAngleX = MathHelper.cos(limbSwing * speed) * degree * limbSwingAmount;   
        this.tailBase.rotateAngleY = MathHelper.cos(20 + limbSwing * speed) * degree * limbSwingAmount;   
    }
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
