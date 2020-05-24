package net.msrandom.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelAnemoneCrawler extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer finDorsal;
    public ModelRenderer head;
    public ModelRenderer legRight;
    public ModelRenderer armRight;
    public ModelRenderer legLeft;
    public ModelRenderer armLeft;
    public ModelRenderer tail;

    public ModelAnemoneCrawler() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.armRight = new ModelRenderer(this, 0, 0);
        this.armRight.setRotationPoint(-0.5F, 2.5F, -3.5F);
        this.armRight.addBox(-1.0F, 0.0F, -1.0F, 1, 3, 2, 0.0F);
        this.tail = new ModelRenderer(this, 0, 13);
        this.tail.setRotationPoint(0.0F, 0.0F, 4.5F);
        this.tail.addBox(0.0F, -2.5F, 0.0F, 0, 5, 5, 0.0F);
        this.finDorsal = new ModelRenderer(this, 0, 8);
        this.finDorsal.setRotationPoint(0.0F, -2.5F, 1.0F);
        this.finDorsal.addBox(0.0F, -2.0F, -3.5F, 0, 2, 7, 0.0F);
        this.head = new ModelRenderer(this, 18, 0);
        this.head.setRotationPoint(0.0F, 0.5F, -4.5F);
        this.head.addBox(-1.5F, -2.0F, -3.0F, 3, 4, 3, 0.0F);
        this.legLeft = new ModelRenderer(this, 0, 0);
        this.legLeft.setRotationPoint(0.5F, 2.5F, 3.5F);
        this.legLeft.addBox(0.0F, 0.0F, -1.0F, 1, 3, 2, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 17.5F, 0.0F);
        this.body.addBox(-1.5F, -2.5F, -4.5F, 3, 5, 9, 0.0F);
        this.legRight = new ModelRenderer(this, 0, 0);
        this.legRight.setRotationPoint(-0.5F, 2.5F, 3.5F);
        this.legRight.addBox(-1.0F, 0.0F, -1.0F, 1, 3, 2, 0.0F);
        this.armLeft = new ModelRenderer(this, 0, 0);
        this.armLeft.setRotationPoint(0.5F, 2.5F, -3.5F);
        this.armLeft.addBox(0.0F, 0.0F, -1.0F, 1, 3, 2, 0.0F);
        this.body.addChild(this.armRight);
        this.body.addChild(this.tail);
        this.body.addChild(this.finDorsal);
        this.body.addChild(this.head);
        this.body.addChild(this.legLeft);
        this.body.addChild(this.legRight);
        this.body.addChild(this.armLeft);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (((EntityLivingBase) entity).isChild()) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
        }
        if (entity.isSneaking()) GlStateManager.translate(0.0F, 0.2F, 0.0F);
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        float speed = 1.5f, degree = 0.9f;
        this.armLeft.rotateAngleX = MathHelper.cos(limbSwing * speed) * degree * limbSwingAmount;
        this.armRight.rotateAngleX = MathHelper.cos(limbSwing * speed + (float) Math.PI) * degree * limbSwingAmount;
        this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * speed + (float) Math.PI) * degree * limbSwingAmount;
        this.legRight.rotateAngleX = MathHelper.cos(limbSwing * speed) * degree * limbSwingAmount;
        this.tail.rotateAngleY = MathHelper.cos(20 + limbSwing * speed) * degree * limbSwingAmount;
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
