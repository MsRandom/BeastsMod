package random.beasts.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import random.beasts.common.entity.passive.EntitySlimeSlug;

public class ModelSlimeSlug extends EntityModel<EntitySlimeSlug> {
    public RendererModel body;
    public RendererModel neck;
    public RendererModel hump;
    public RendererModel head;
    public RendererModel mouth;
    public RendererModel antennaRight;
    public RendererModel antennaLeft;
    public RendererModel tail;

    public ModelSlimeSlug() {
        this.textureWidth = 80;
        this.textureHeight = 80;
        this.mouth = new RendererModel(this, 0, 0);
        this.mouth.setRotationPoint(0.0F, 5.0F, 5.5F);
        this.mouth.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 2, 0.0F);
        this.setRotateAngle(mouth, -0.17453292519943295F, 0.0F, 0.0F);
        this.antennaRight = new RendererModel(this, 0, 5);
        this.antennaRight.setRotationPoint(2.5F, 4.5F, 0.5F);
        this.antennaRight.addBox(0.0F, -1.0F, -5.0F, 0, 2, 5, 0.0F);
        this.setRotateAngle(antennaRight, 0.0F, -0.6108652381980153F, 0.5235987755982988F);
        this.antennaLeft = new RendererModel(this, 0, 5);
        this.antennaLeft.setRotationPoint(-2.5F, 4.5F, 0.5F);
        this.antennaLeft.addBox(0.0F, -1.0F, -5.0F, 0, 2, 5, 0.0F);
        this.setRotateAngle(antennaLeft, 0.0F, 0.4363323129985824F, -0.5235987755982988F);
        this.body = new RendererModel(this, 0, 0);
        this.body.setRotationPoint(0.0F, 18.5F, 0.0F);
        this.body.addBox(-4.5F, -3.5F, -10.0F, 9, 9, 20, 0.0F);
        this.hump = new RendererModel(this, 51, 60);
        this.hump.setRotationPoint(0.0F, -2.5F, 5.5F);
        this.hump.addBox(-3.5F, -3.0F, -3.5F, 7, 3, 7, 0.0F);
        this.setRotateAngle(hump, -0.17453292519943295F, 0.0F, 0.0F);
        this.head = new RendererModel(this, 44, 30);
        this.head.setRotationPoint(0.0F, -6.5F, -8.0F);
        this.head.addBox(-3.0F, 0.0F, 0.0F, 6, 7, 6, 0.0F);
        this.setRotateAngle(head, -0.6981317007977318F, 0.0F, 0.0F);
        this.neck = new RendererModel(this, 38, 0);
        this.neck.setRotationPoint(0.0F, 2.0F, -9.0F);
        this.neck.addBox(-2.5F, -6.0F, -8.0F, 5, 6, 8, 0.0F);
        this.setRotateAngle(neck, -0.8726646259971648F, 0.0F, 0.0F);
        this.tail = new RendererModel(this, 0, 29);
        this.tail.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.tail.addBox(-2.0F, -8.0F, -3.5F, 4, 8, 7, 0.0F);
        this.setRotateAngle(tail, -0.3490658503988659F, 0.0F, 0.0F);
        this.head.addChild(this.mouth);
        this.head.addChild(this.antennaRight);
        this.head.addChild(this.antennaLeft);
        this.body.addChild(this.hump);
        this.neck.addChild(this.head);
        this.body.addChild(this.neck);
        this.hump.addChild(this.tail);
    }

    @Override
    public void render(EntitySlimeSlug entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(EntitySlimeSlug entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        float speed = 8.0f;
        float degree = 1.0f;
        this.neck.rotateAngleX = MathHelper.cos((f * speed * 0.3F) + (float) Math.PI) * (degree * 0.5F) * f1 * 0.5F + -0.5F;
        this.head.rotateAngleX = MathHelper.cos((f * speed * 0.3F) + (float) Math.PI) * (degree * 0.3F) * f1 * 0.5F + -0.7F;
        this.hump.rotateAngleX = MathHelper.cos((f * speed * 0.3F) + (float) Math.PI) * (degree * 0.5F) * f1 * 0.5F + -0.3F;
        super.setRotationAngles(entityIn, f, f1, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
