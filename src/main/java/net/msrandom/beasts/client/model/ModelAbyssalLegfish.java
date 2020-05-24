package net.msrandom.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelAbyssalLegfish extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer legRight;
    public ModelRenderer legLeft;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;
    public ModelRenderer fin;
    public ModelRenderer tail;

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

    public static class Type1 extends ModelAbyssalLegfish {
        public Type1() {
            this.textureWidth = 32;
            this.textureHeight = 22;
            this.legRight = new ModelRenderer(this, 0, 0);
            this.legRight.setRotationPoint(-0.5F, 0.5F, 2.5F);
            this.legRight.addBox(-1.0F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.fin = new ModelRenderer(this, 0, 6);
            this.fin.setRotationPoint(0.0F, -1.5F, 1.0F);
            this.fin.addBox(0.0F, -2.0F, -2.5F, 0, 2, 5, 0.0F);
            this.body = new ModelRenderer(this, 0, 0);
            this.body.setRotationPoint(0.0F, 20.5F, 0.0F);
            this.body.addBox(-1.0F, -1.5F, -3.5F, 2, 3, 7, 0.0F);
            this.armRight = new ModelRenderer(this, 0, 0);
            this.armRight.setRotationPoint(-0.5F, 0.5F, -1.0F);
            this.armRight.addBox(-1.0F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.legLeft = new ModelRenderer(this, 0, 0);
            this.legLeft.mirror = true;
            this.legLeft.setRotationPoint(0.5F, 0.5F, 2.5F);
            this.legLeft.addBox(0.0F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.tail = new ModelRenderer(this, 0, 12);
            this.tail.setRotationPoint(0.0F, 0.0F, 3.5F);
            this.tail.addBox(0.0F, -1.5F, 0.0F, 0, 3, 3, 0.0F);
            this.armLeft = new ModelRenderer(this, 0, 0);
            this.armLeft.mirror = true;
            this.armLeft.setRotationPoint(0.5F, 0.5F, -1.0F);
            this.armLeft.addBox(0.0F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.body.addChild(this.legRight);
            this.body.addChild(this.fin);
            this.body.addChild(this.armRight);
            this.body.addChild(this.legLeft);
            this.body.addChild(this.tail);
            this.body.addChild(this.armLeft);
        }
    }

    public static class Type2 extends ModelAbyssalLegfish {
        public Type2() {
            this.textureWidth = 24;
            this.textureHeight = 22;
            this.tail = new ModelRenderer(this, 0, 0);
            this.tail.setRotationPoint(0.0F, 0.0F, 2.5F);
            this.tail.addBox(0.0F, -1.5F, 0.0F, 0, 3, 9, 0.0F);
            this.body = new ModelRenderer(this, 0, 0);
            this.body.setRotationPoint(0.0F, 20.5F, 0.0F);
            this.body.addBox(-1.0F, -1.5F, -2.5F, 2, 3, 5, 0.0F);
            this.armLeft = new ModelRenderer(this, 0, 0);
            this.armLeft.mirror = true;
            this.armLeft.setRotationPoint(0.5F, 0.5F, -1.0F);
            this.armLeft.addBox(0.0F, -0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.fin = new ModelRenderer(this, 0, 9);
            this.fin.setRotationPoint(0.0F, -1.5F, 0.5F);
            this.fin.addBox(0.0F, -3.0F, -2.0F, 0, 3, 4, 0.0F);
            this.armRight = new ModelRenderer(this, 0, 0);
            this.armRight.setRotationPoint(-0.5F, 0.5F, -1.0F);
            this.armRight.addBox(-1.0F, -0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.legLeft = new ModelRenderer(this, 0, 0);
            this.legLeft.mirror = true;
            this.legLeft.setRotationPoint(0.5F, 0.5F, 1.5F);
            this.legLeft.addBox(0.0F, -0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.legRight = new ModelRenderer(this, 0, 0);
            this.legRight.setRotationPoint(-0.5F, 0.5F, 1.5F);
            this.legRight.addBox(-1.0F, -0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.body.addChild(this.tail);
            this.body.addChild(this.armLeft);
            this.body.addChild(this.fin);
            this.body.addChild(this.armRight);
            this.body.addChild(this.legLeft);
            this.body.addChild(this.legRight);
        }
    }

    public static class Type3 extends ModelAbyssalLegfish {
        public ModelRenderer snout;
        public ModelRenderer snout_extra;

        public Type3() {
            this.textureWidth = 34;
            this.textureHeight = 22;
            this.legLeft = new ModelRenderer(this, 0, 0);
            this.legLeft.setRotationPoint(-0.5F, 1.5F, 3.5F);
            this.legLeft.addBox(-1.0F, 0.0F, -1.0F, 1, 3, 2, 0.0F);
            this.snout = new ModelRenderer(this, 0, 5);
            this.snout.setRotationPoint(0.0F, 0.5F, -4.5F);
            this.snout.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 1, 0.0F);
            this.legRight = new ModelRenderer(this, 0, 0);
            this.legRight.setRotationPoint(0.5F, 1.5F, 3.5F);
            this.legRight.addBox(0.0F, 0.0F, -1.0F, 1, 3, 2, 0.0F);
            this.armLeft = new ModelRenderer(this, 1, 1);
            this.armLeft.mirror = true;
            this.armLeft.setRotationPoint(1.0F, 1.5F, -2.0F);
            this.armLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.snout_extra = new ModelRenderer(this, 16, -6);
            this.snout_extra.setRotationPoint(0.0F, 0.0F, -1.0F);
            this.snout_extra.addBox(0.0F, -1.0F, -6.0F, 0, 2, 6, 0.0F);
            this.tail = new ModelRenderer(this, 0, 6);
            this.tail.setRotationPoint(0.0F, 0.0F, 4.5F);
            this.tail.addBox(0.0F, -1.5F, 0.0F, 0, 3, 7, 0.0F);
            this.body = new ModelRenderer(this, 0, 0);
            this.body.setRotationPoint(0.0F, 19.5F, 0.0F);
            this.body.addBox(-1.5F, -1.5F, -4.5F, 3, 3, 9, 0.0F);
            this.fin = new ModelRenderer(this, 0, 9);
            this.fin.setRotationPoint(0.0F, -1.5F, 0.5F);
            this.fin.addBox(0.0F, -3.0F, -4.0F, 0, 3, 8, 0.0F);
            this.armRight = new ModelRenderer(this, 1, 1);
            this.armRight.setRotationPoint(-1.0F, 1.5F, -2.0F);
            this.armRight.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.body.addChild(this.legLeft);
            this.body.addChild(this.snout);
            this.body.addChild(this.legRight);
            this.body.addChild(this.armLeft);
            this.snout.addChild(this.snout_extra);
            this.body.addChild(this.tail);
            this.body.addChild(this.fin);
            this.body.addChild(this.armRight);
        }
    }
}
