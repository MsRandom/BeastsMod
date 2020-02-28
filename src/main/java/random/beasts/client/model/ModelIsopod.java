package random.beasts.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import random.beasts.common.entity.monster.EntityIsopod;

public class ModelIsopod extends EntityModel<EntityIsopod> {
    public RendererModel body;
    public RendererModel head;
    public RendererModel crest;
    public RendererModel legLeft1;
    public RendererModel legLeft2;
    public RendererModel legLeft3;
    public RendererModel legLeft4;
    public RendererModel legRight1;
    public RendererModel legRight2;
    public RendererModel legRight3;
    public RendererModel legRight4;

    public ModelIsopod() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.crest = new RendererModel(this, 37, 13);
        this.crest.setRotationPoint(0.0F, -1.0F, -2.0F);
        this.crest.addBox(-0.6F, -7.0F, -5.0F, 1, 7, 10, 0.0F);
        this.legLeft1 = new RendererModel(this, 0, 0);
        this.legLeft1.setRotationPoint(2.5F, 4.0F, -3.0F);
        this.legLeft1.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.legLeft4 = new RendererModel(this, 0, 0);
        this.legLeft4.setRotationPoint(2.5F, 4.0F, 6.0F);
        this.legLeft4.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.legRight3 = new RendererModel(this, 0, 0);
        this.legRight3.setRotationPoint(-2.5F, 4.0F, 3.0F);
        this.legRight3.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.head = new RendererModel(this, 0, 22);
        this.head.setRotationPoint(0.0F, 1.5F, -5.0F);
        this.head.addBox(-3.5F, -2.5F, -4.0F, 7, 5, 4, 0.0F);
        this.legRight1 = new RendererModel(this, 0, 0);
        this.legRight1.setRotationPoint(-2.5F, 4.0F, -3.0F);
        this.legRight1.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.legLeft3 = new RendererModel(this, 0, 0);
        this.legLeft3.setRotationPoint(2.5F, 4.0F, 3.0F);
        this.legLeft3.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.legLeft2 = new RendererModel(this, 0, 0);
        this.legLeft2.setRotationPoint(2.5F, 4.0F, 0.0F);
        this.legLeft2.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.legRight2 = new RendererModel(this, 0, 0);
        this.legRight2.setRotationPoint(-2.5F, 4.0F, 0.0F);
        this.legRight2.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.legRight4 = new RendererModel(this, 0, 0);
        this.legRight4.setRotationPoint(-2.5F, 4.0F, 6.0F);
        this.legRight4.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 2, 0.0F);
        this.body = new RendererModel(this, 0, 0);
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
    public void render(EntityIsopod entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.render(f5);
    }

    @Override
    public void setLivingAnimations(EntityIsopod entitylivingbaseIn, float f, float f1, float partialTickTime) {
        float speed = 1.0f;
        float degree = 1.0f;
        this.legLeft1.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.7F) * f1 * 0.5F;
        this.legLeft2.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * -0.7F) * f1 * 0.5F;
        this.legLeft3.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.7F) * f1 * 0.5F;
        this.legLeft4.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * -0.7F) * f1 * 0.5F;
        this.legRight1.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * -0.7F) * f1 * 0.5F;
        this.legRight2.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.7F) * f1 * 0.5F;
        this.legRight3.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * -0.7F) * f1 * 0.5F;
        this.legRight4.rotateAngleX = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.7F) * f1 * 0.5F;
    	this.head.offsetY = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.1F) * f1 * 0.5F;
    	this.crest.offsetY = MathHelper.cos((f * speed * 0.4F) + (float) Math.PI) * (degree * 0.1F) * f1 * 0.5F;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
