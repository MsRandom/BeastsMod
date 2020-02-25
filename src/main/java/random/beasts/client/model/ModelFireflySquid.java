package random.beasts.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelFireflySquid - Undefined
 * Created using Tabula 7.1.0
 */
public class ModelFireflySquid extends EntityModel {
    public RendererModel body;
    public RendererModel finLeft;
    public RendererModel finRight;
    public RendererModel tentacleMiddle;
    public RendererModel tentacleRight;
    public RendererModel tentacleLeft;

    public ModelFireflySquid() {
        this.textureWidth = 35;
        this.textureHeight = 20;
        this.tentacleLeft = new RendererModel(this, 15, 13);
        this.tentacleLeft.mirror = true;
        this.tentacleLeft.setRotationPoint(1.0F, 1.0F, -3.0F);
        this.tentacleLeft.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(tentacleLeft, 0.6108652381980153F, -0.5235987755982988F, 0.0F);
        this.tentacleMiddle = new RendererModel(this, 0, 12);
        this.tentacleMiddle.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.tentacleMiddle.addBox(-1.5F, -1.0F, -4.0F, 3, 2, 4, 0.0F);
        this.setRotateAngle(tentacleMiddle, 0.3490658503988659F, 0.0F, 0.0F);
        this.tentacleRight = new RendererModel(this, 15, 13);
        this.tentacleRight.setRotationPoint(-1.0F, 1.0F, -3.0F);
        this.tentacleRight.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(tentacleRight, 0.6108652381980153F, 0.5235987755982988F, 0.0F);
        this.body = new RendererModel(this, 0, 0);
        this.body.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.body.addBox(-2.0F, -2.0F, -3.5F, 4, 4, 7, 0.0F);
        this.setRotateAngle(body, 0.0F, 3.141592653589793F, 0.0F);
        this.finLeft = new RendererModel(this, 12, 0);
        this.finLeft.mirror = true;
        this.finLeft.setRotationPoint(2.0F, 0.0F, 2.5F);
        this.finLeft.addBox(-1.0F, 0.0F, -3.0F, 5, 0, 6, 0.0F);
        this.finRight = new RendererModel(this, 12, 0);
        this.finRight.setRotationPoint(-2.0F, 0.0F, 2.5F);
        this.finRight.addBox(-4.0F, 0.0F, -3.0F, 5, 0, 6, 0.0F);
        this.body.addChild(this.tentacleLeft);
        this.body.addChild(this.tentacleMiddle);
        this.body.addChild(this.tentacleRight);
        this.body.addChild(this.finLeft);
        this.body.addChild(this.finRight);
    }

    @Override
    public void setLivingAnimations(LivingEntity entitylivingbaseIn, float f, float f1, float partialTickTime) {
        float speed = 1.0f;
        float degree = 1.0f;
        this.tentacleRight.rotateAngleX = MathHelper.cos((f * speed * 0.3F) + (float) Math.PI) * (degree * 0.8F) * f1 * 0.5F + 0.4F;
        this.tentacleLeft.rotateAngleX = MathHelper.cos((f * speed * 0.3F) + (float) Math.PI) * (degree * -0.8F) * f1 * 0.5F + 0.4F;
        this.tentacleMiddle.rotateAngleX = MathHelper.cos((f * speed * 0.3F) + (float) Math.PI) * (degree * 0.2F) * f1 * 0.5F + 0.3F;
        this.finRight.rotateAngleZ = MathHelper.cos((f * speed * 0.3F) + (float) Math.PI) * (degree * 0.7F) * f1 * 0.5F;
        this.finLeft.rotateAngleZ = MathHelper.cos((f * speed * 0.3F) + (float) Math.PI) * (degree * -0.7F) * f1 * 0.5F;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
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
