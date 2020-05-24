package net.msrandom.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.msrandom.beasts.common.entity.projectile.EntityGlowShrimpShot;

public class ModelGlowShrimpProjectile extends ModelBase {
    public ModelRenderer shape1;
    public ModelRenderer shape1_1;
    public ModelRenderer shape1_2;
    public ModelRenderer shape1_3;
    public ModelRenderer shape1_4;

    public ModelGlowShrimpProjectile() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.shape1_3 = new ModelRenderer(this, -5, 6);
        this.shape1_3.setRotationPoint(0.0F, 0.0F, 1.5F);
        this.shape1_3.addBox(-2.0F, 0.0F, 0.0F, 4, 0, 5, 0.0F);
        this.setRotateAngle(shape1_3, 0.0F, 0.0F, -0.7853981633974483F);
        this.shape1_1 = new ModelRenderer(this, 0, 7);
        this.shape1_1.setRotationPoint(0.0F, 0.0F, 1.5F);
        this.shape1_1.addBox(0.0F, -2.0F, 0.0F, 0, 4, 5, 0.0F);
        this.shape1_2 = new ModelRenderer(this, -5, 6);
        this.shape1_2.mirror = true;
        this.shape1_2.setRotationPoint(0.0F, 0.0F, 1.5F);
        this.shape1_2.addBox(-2.0F, 0.0F, 0.0F, 4, 0, 5, 0.0F);
        this.setRotateAngle(shape1_2, 0.0F, 0.0F, 0.7853981633974483F);
        this.shape1_4 = new ModelRenderer(this, -5, 6);
        this.shape1_4.setRotationPoint(0.0F, 0.0F, 1.5F);
        this.shape1_4.addBox(-2.0F, 0.0F, 0.0F, 4, 0, 5, 0.0F);
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1.addBox(-1.0F, -1.0F, -1.5F, 2, 2, 3, 0.0F);
        this.shape1.addChild(this.shape1_3);
        this.shape1.addChild(this.shape1_1);
        this.shape1.addChild(this.shape1_2);
        this.shape1.addChild(this.shape1_4);
    }

    public void render(EntityGlowShrimpShot entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.shape1.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
