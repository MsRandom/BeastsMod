package rando.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelRabbitman extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer coatOverlay;
    public ModelRenderer armLeft;
    public ModelRenderer legLeft;
    public ModelRenderer armLeft_1;
    public ModelRenderer legRight;
    public ModelRenderer tail;
    public ModelRenderer earRight;
    public ModelRenderer earLeft;
    public ModelRenderer nose;
    public ModelRenderer armMiddle;

    public ModelRabbitman() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body = new ModelRenderer(this, 0, 36);
        this.body.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.body.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 4, 0.0F);
        this.earRight = new ModelRenderer(this, 56, 0);
        this.earRight.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.earRight.addBox(-2.5F, -9.0F, -1.0F, 2, 5, 1, 0.0F);
        this.setRotateAngle(earRight, 0.0F, -0.2617993877991494F, 0.0F);
        this.armMiddle = new ModelRenderer(this, 40, 21);
        this.armMiddle.setRotationPoint(-3.0F, 1.0F, 0.0F);
        this.armMiddle.addBox(-3.0F, 0.0F, -1.5F, 6, 3, 3, 0.0F);
        this.head = new ModelRenderer(this, 22, 0);
        this.head.setRotationPoint(0.0F, 0.0F, -1.5F);
        this.head.addBox(-2.5F, -4.0F, -2.5F, 5, 4, 5, 0.0F);
        this.earLeft = new ModelRenderer(this, 56, 0);
        this.earLeft.mirror = true;
        this.earLeft.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.earLeft.addBox(0.5F, -9.0F, -1.0F, 2, 5, 1, 0.0F);
        this.setRotateAngle(earLeft, 0.0F, 0.2617993877991494F, 0.0F);
        this.armLeft_1 = new ModelRenderer(this, 12, 15);
        this.armLeft_1.setRotationPoint(-3.0F, 4.0F, -2.0F);
        this.armLeft_1.addBox(-2.0F, -2.0F, -1.5F, 2, 6, 3, 0.0F);
        this.setRotateAngle(armLeft_1, -0.8726646259971648F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 0, 0);
        this.nose.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.nose.addBox(-0.5F, -2.5F, -5.5F, 1, 1, 1, 0.0F);
        this.armLeft = new ModelRenderer(this, 22, 9);
        this.armLeft.setRotationPoint(3.0F, 4.0F, -2.0F);
        this.armLeft.addBox(0.0F, -2.0F, -1.5F, 2, 6, 3, 0.0F);
        this.setRotateAngle(armLeft, -0.8726646259971648F, 0.0F, 0.0F);
        this.coatOverlay = new ModelRenderer(this, 37, 4);
        this.coatOverlay.setRotationPoint(0.0F, -0.1F, -0.5F);
        this.coatOverlay.addBox(-3.5F, 0.0F, -3.0F, 7, 12, 5, 0.0F);
        this.legRight = new ModelRenderer(this, 22, 18);
        this.legRight.setRotationPoint(-1.5F, 12.0F, -1.0F);
        this.legRight.addBox(-1.5F, -2.0F, -1.5F, 3, 9, 3, 0.0F);
        this.tail = new ModelRenderer(this, 17, 0);
        this.tail.setRotationPoint(0.0F, 8.5F, 0.5F);
        this.tail.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2, 0.0F);
        this.legLeft = new ModelRenderer(this, 0, 15);
        this.legLeft.mirror = true;
        this.legLeft.setRotationPoint(1.5F, 12.0F, -1.0F);
        this.legLeft.addBox(-1.5F, -2.0F, -1.5F, 3, 9, 3, 0.0F);
        this.head.addChild(this.earRight);
        this.armLeft.addChild(this.armMiddle);
        this.body.addChild(this.head);
        this.head.addChild(this.earLeft);
        this.body.addChild(this.armLeft_1);
        this.head.addChild(this.nose);
        this.body.addChild(this.armLeft);
        this.body.addChild(this.coatOverlay);
        this.body.addChild(this.legRight);
        this.body.addChild(this.tail);
        this.body.addChild(this.legLeft);
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
        this.head.rotateAngleY = (float)Math.toRadians(netHeadYaw);
        this.head.rotateAngleX = (float)Math.toRadians(headPitch);
        this.legRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
    }
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
