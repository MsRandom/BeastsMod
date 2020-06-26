package net.msrandom.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelIsopod extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer crest;
    public ModelRenderer legLeft1;
    public ModelRenderer legLeft2;
    public ModelRenderer legLeft3;
    public ModelRenderer legLeft4;
    public ModelRenderer legRight1;
    public ModelRenderer legRight2;
    public ModelRenderer legRight3;
    public ModelRenderer legRight4;

    public ModelIsopod() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.crest = new ModelRenderer(this, 37, 13);
        this.crest.setRotationPoint(0.0F, -1.0F, -2.0F);
        this.crest.addBox(-0.6F, -7.0F, -5.0F, 1, 7, 10, 0.0F);
        this.legLeft1 = new ModelRenderer(this, 0, 0);
        this.legLeft1.setRotationPoint(2.5F, 4.0F, -3.0F);
        this.legLeft1.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.legLeft4 = new ModelRenderer(this, 0, 0);
        this.legLeft4.setRotationPoint(2.5F, 4.0F, 6.0F);
        this.legLeft4.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.legRight3 = new ModelRenderer(this, 0, 0);
        this.legRight3.setRotationPoint(-2.5F, 4.0F, 3.0F);
        this.legRight3.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.head = new ModelRenderer(this, 0, 22);
        this.head.setRotationPoint(0.0F, 1.5F, -5.0F);
        this.head.addBox(-3.5F, -2.5F, -4.0F, 7, 5, 4, 0.0F);
        this.legRight1 = new ModelRenderer(this, 0, 0);
        this.legRight1.setRotationPoint(-2.5F, 4.0F, -3.0F);
        this.legRight1.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.legLeft3 = new ModelRenderer(this, 0, 0);
        this.legLeft3.setRotationPoint(2.5F, 4.0F, 3.0F);
        this.legLeft3.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.legLeft2 = new ModelRenderer(this, 0, 0);
        this.legLeft2.setRotationPoint(2.5F, 4.0F, 0.0F);
        this.legLeft2.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.legRight2 = new ModelRenderer(this, 0, 0);
        this.legRight2.setRotationPoint(-2.5F, 4.0F, 0.0F);
        this.legRight2.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.legRight4 = new ModelRenderer(this, 0, 0);
        this.legRight4.setRotationPoint(-2.5F, 4.0F, 6.0F);
        this.legRight4.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 16.9F, -2.0F);
        this.body.addBox(-4.5F, -4.0F, -5.0F, 9, 8, 14, 0.0F);
        this.body.addChild(this.crest);
        this.body.addChild(this.legLeft1);
        this.body.addChild(this.legLeft4);
        this.body.addChild(this.legRight3);
        this.body.addChild(this.head);
        this.body.addChild(this.legRight1);
        this.body.addChild(this.legLeft3);
        this.body.addChild(this.legLeft2);
        this.body.addChild(this.legRight2);
        this.body.addChild(this.legRight4);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
    }
    
    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float f, float f1, float partialTickTime) {
        float speed = 1.3f;
        float degree = 1.0f;
        this.legLeft1.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.7F) * f1 * 0.8F;
        this.legLeft2.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * -0.7F) * f1 * 0.8F;
        this.legLeft3.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.7F) * f1 * 0.8F;
        this.legLeft4.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * -0.7F) * f1 * 0.8F;
        this.legRight1.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * -0.7F) * f1 * 0.8F;
        this.legRight2.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.7F) * f1 * 0.8F;
        this.legRight3.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * -0.7F) * f1 * 0.8F;
        this.legRight4.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.7F) * f1 * 0.8F;
        this.head.offsetY = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.1F) * f1 * 0.5F;
        this.crest.offsetY = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.1F) * f1 * 0.5F;
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
