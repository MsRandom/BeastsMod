package random.beasts.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import random.beasts.common.entity.monster.EntityWhippingBarnacle;

public class ModelWhippingBarnacle extends EntityModel {
    public RendererModel body;
    public RendererModel toung1;
    public RendererModel toung2;
    public RendererModel toung3;
    public RendererModel toung4;
    public RendererModel spike1;
    public RendererModel spike2;
    public RendererModel spike3;
    public RendererModel spike4;
    private RendererModel[] tongue;
    private RendererModel[] spikes;

    public ModelWhippingBarnacle() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.toung4 = new RendererModel(this, 28, 33);
        this.toung4.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.toung4.addBox(-1.5F, -8.0F, -1.5F, 3, 9, 3, 0.0F);
        this.spike2 = new RendererModel(this, 35, 31);
        this.spike2.setRotationPoint(-0.5F, -5.0F, 0.0F);
        this.spike2.addBox(-6.0F, 0.0F, -0.4F, 6, 1, 1, 0.0F);
        this.setRotateAngle(spike2, 0.0F, 0.0F, 0.9599310885968813F);
        this.spike1 = new RendererModel(this, 21, 31);
        this.spike1.setRotationPoint(0.5F, -5.0F, 0.0F);
        this.spike1.addBox(0.0F, 0.0F, -0.5F, 6, 1, 1, 0.0F);
        this.setRotateAngle(spike1, 0.0F, 0.0F, -0.9599310885968813F);
        this.toung2 = new RendererModel(this, 44, 50);
        this.toung2.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.toung2.addBox(-2.5F, -8.0F, -2.5F, 5, 9, 5, 0.0F);
        this.body = new RendererModel(this, 0, 45);
        this.body.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.body.addBox(-5.5F, -8.0F, -5.5F, 11, 8, 11, 0.0F);
        this.toung1 = new RendererModel(this, 0, 29);
        this.toung1.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.toung1.addBox(-3.5F, -7.0F, -3.5F, 7, 9, 7, 0.0F);
        this.toung3 = new RendererModel(this, 44, 36);
        this.toung3.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.toung3.addBox(-2.0F, -8.0F, -2.0F, 4, 9, 4, 0.0F);
        this.spike4 = new RendererModel(this, 36, 23);
        this.spike4.setRotationPoint(0.0F, -2.5F, -0.5F);
        this.spike4.addBox(-0.5F, 0.0F, -6.0F, 1, 1, 6, 0.0F);
        this.setRotateAngle(spike4, -0.8726646259971648F, 0.0F, 0.0F);
        this.spike3 = new RendererModel(this, 21, 23);
        this.spike3.setRotationPoint(0.0F, -2.5F, 0.5F);
        this.spike3.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.setRotateAngle(spike3, 0.8726646259971648F, 0.0F, 0.0F);
        this.toung3.addChild(this.toung4);
        this.toung4.addChild(this.spike2);
        this.toung4.addChild(this.spike1);
        this.toung1.addChild(this.toung2);
        this.body.addChild(this.toung1);
        this.toung2.addChild(this.toung3);
        this.toung4.addChild(this.spike4);
        this.toung4.addChild(this.spike3);
        tongue = new RendererModel[]{toung1, toung2, toung3, toung4};
        spikes = new RendererModel[]{spike1, spike2, spike3, spike4};
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        EntityWhippingBarnacle barnacle = (EntityWhippingBarnacle) entityIn;
        if (!barnacle.ready) {
            toung1.offsetY = 1.2F;
            for (RendererModel spike : spikes) spike.rotateAngleX = 0;
            for (RendererModel part : tongue) part.rotateAngleZ = 0;
        } else {
            toung1.offsetY = 0;
            for (RendererModel spike : spikes) spike.rotateAngleX = MathHelper.cos(barnacle.ticksExisted) * 0.1f;
            if (barnacle.impaling) for (int i = 0; i < tongue.length; i++)
                tongue[i].rotateAngleZ = Math.min(Math.min(barnacle.impalingTicks, 90) * (i + 1) * 0.0125f, 0.65f);
            else for (int i = 0; i < tongue.length; i++)
                tongue[i].rotateAngleZ = MathHelper.cos(barnacle.ticksExisted * 0.1f) * (i + 1) * 0.0625f;
        }
    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
