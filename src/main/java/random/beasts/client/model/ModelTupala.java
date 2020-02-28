package random.beasts.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import random.beasts.common.entity.passive.EntityTupala;

public class ModelTupala extends EntityModel<EntityTupala> {
    public RendererModel body;
    public RendererModel base1;
    public RendererModel base2;
    public RendererModel base3;
    public RendererModel legFrontLeft;
    public RendererModel legMiddleLeft;
    public RendererModel legBackLeft;
    public RendererModel legFrontRight;
    public RendererModel legMiddleRight;
    public RendererModel legBackRight;
    public RendererModel head1;
    public RendererModel head2;
    public RendererModel head3;

    public ModelTupala() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.base3 = new RendererModel(this, 0, 0);
        this.base3.setRotationPoint(0.0F, -3.0F, -2.5F);
        this.base3.addBox(-2.0F, -10.0F, -2.0F, 4, 12, 4, 0.0F);
        this.base1 = new RendererModel(this, 0, 0);
        this.base1.setRotationPoint(-2.5F, -3.0F, 2.5F);
        this.base1.addBox(-2.0F, -10.0F, -2.0F, 4, 12, 4, 0.0F);
        this.legFrontRight = new RendererModel(this, 0, 20);
        this.legFrontRight.mirror = true;
        this.legFrontRight.setRotationPoint(-3.5F, 1.0F, -1.5F);
        this.legFrontRight.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(legFrontRight, -0.4363323129985824F, 0.0F, 0.6981317007977318F);
        this.legBackLeft = new RendererModel(this, 0, 20);
        this.legBackLeft.setRotationPoint(3.5F, 1.0F, 1.5F);
        this.legBackLeft.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(legBackLeft, 0.4363323129985824F, 0.0F, -0.6981317007977318F);
        this.head3 = new RendererModel(this, 17, 0);
        this.head3.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.head3.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
        this.legFrontLeft = new RendererModel(this, 0, 20);
        this.legFrontLeft.setRotationPoint(3.5F, 1.0F, -1.5F);
        this.legFrontLeft.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(legFrontLeft, -0.4363323129985824F, 0.0F, -0.6981317007977318F);
        this.head2 = new RendererModel(this, 17, 0);
        this.head2.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.head2.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
        this.head1 = new RendererModel(this, 17, 0);
        this.head1.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.head1.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
        this.legMiddleLeft = new RendererModel(this, 0, 20);
        this.legMiddleLeft.setRotationPoint(3.5F, 1.0F, 0.0F);
        this.legMiddleLeft.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(legMiddleLeft, 0.0F, 0.0F, -0.7853981633974483F);
        this.legBackRight = new RendererModel(this, 0, 20);
        this.legBackRight.mirror = true;
        this.legBackRight.setRotationPoint(-3.5F, 1.0F, 1.5F);
        this.legBackRight.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(legBackRight, 0.4363323129985824F, 0.0F, 0.6981317007977318F);
        this.base2 = new RendererModel(this, 0, 0);
        this.base2.setRotationPoint(2.5F, -3.0F, 2.5F);
        this.base2.addBox(-2.0F, -10.0F, -2.0F, 4, 12, 4, 0.0F);
        this.body = new RendererModel(this, 30, 0);
        this.body.setRotationPoint(0.0F, 19.0F, 0.0F);
        this.body.addBox(-4.0F, -3.0F, -4.0F, 8, 5, 8, 0.0F);
        this.legMiddleRight = new RendererModel(this, 0, 20);
        this.legMiddleRight.mirror = true;
        this.legMiddleRight.setRotationPoint(-3.5F, 1.0F, 0.0F);
        this.legMiddleRight.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(legMiddleRight, 0.0F, 0.0F, 0.7853981633974483F);
        this.body.addChild(this.base3);
        this.body.addChild(this.base1);
        this.body.addChild(this.legFrontRight);
        this.body.addChild(this.legBackLeft);
        this.base3.addChild(this.head3);
        this.body.addChild(this.legFrontLeft);
        this.base2.addChild(this.head2);
        this.base1.addChild(this.head1);
        this.body.addChild(this.legMiddleLeft);
        this.body.addChild(this.legBackRight);
        this.body.addChild(this.base2);
        this.body.addChild(this.legMiddleRight);
    }

    @Override
    public void render(EntityTupala entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.render(f5);
    }

    @Override
    public void setLivingAnimations(EntityTupala entitylivingbaseIn, float f, float f1, float partialTickTime) {
        float speed = 1.0f;
        float degree = 1.0f;
        this.legMiddleLeft.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.8F) * f1 * 0.5F;
        this.legFrontLeft.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * -0.8F) * f1 * 0.5F + -0.45F;
        this.legBackLeft.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * -0.8F) * f1 * 0.5F + 0.45F;
        this.legMiddleRight.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * -0.8F) * f1 * 0.5F;
        this.legFrontRight.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.8F) * f1 * 0.5F + -0.45F;
        this.legBackRight.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.8F) * f1 * 0.5F + 0.45F;
        this.base1.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.2F) * f1 * 0.5F;
        this.head1.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.4F) * f1 * 0.5F;
        this.base2.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.2F) * f1 * 0.5F;
        this.head2.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.4F) * f1 * 0.5F;
        this.base3.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.2F) * f1 * 0.5F;
        this.head3.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.4F) * f1 * 0.5F;
    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
