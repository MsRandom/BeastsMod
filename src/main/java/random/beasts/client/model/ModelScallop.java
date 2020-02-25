package random.beasts.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import random.beasts.common.entity.monster.EntityScallop;

public class ModelScallop extends EntityModel {
    public RendererModel base;
    public RendererModel mouth;
    public RendererModel jaw;
    public RendererModel tongue;

    public ModelScallop() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.jaw = new RendererModel(this, 0, 8);
        this.jaw.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.jaw.addBox(-1.5F, 0.0F, -4.0F, 3, 1, 4, 0.0F);
        this.mouth = new RendererModel(this, 0, 3);
        this.mouth.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.mouth.addBox(-1.5F, -1.0F, -4.0F, 3, 1, 4, 0.0F);
        this.tongue = new RendererModel(this, 0, 13);
        this.tongue.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.tongue.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.base = new RendererModel(this, 0, 0);
        this.base.setRotationPoint(0.0F, 23.0F, 1.5F);
        this.base.addBox(-1.0F, -0.5F, -1.0F, 2, 1, 1, 0.0F);
        this.base.addChild(this.jaw);
        this.base.addChild(this.mouth);
        this.base.addChild(this.tongue);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.base.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        LivingEntity target = ((EntityScallop) entityIn).getAttackTarget();
        if (target == null) this.base.rotateAngleX = 0;
        else this.base.rotateAngleX = 0.8f;
        this.jaw.rotateAngleX = MathHelper.cos(limbSwing * 0.95f) * limbSwingAmount * 0.8f + 0.3f;
    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
