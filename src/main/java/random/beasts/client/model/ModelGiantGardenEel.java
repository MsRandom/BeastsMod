package random.beasts.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import random.beasts.common.entity.monster.EntityGiantGardenEel;

public class ModelGiantGardenEel extends EntityModel<EntityGiantGardenEel> {
    public RendererModel body;
    public RendererModel body1;
    public RendererModel body2;
    public RendererModel body3;
    public RendererModel head;
    private RendererModel[] parts;

    public ModelGiantGardenEel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body2 = new RendererModel(this, 0, 18);
        this.body2.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.body2.addBox(-3.0F, -12.0F, -3.0F, 6, 12, 6, 0.0F);
        this.body3 = new RendererModel(this, 24, 18);
        this.body3.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.body3.addBox(-3.0F, -12.0F, -3.0F, 6, 12, 6, 0.0F);
        this.head = new RendererModel(this, 0, 36);
        this.head.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.head.addBox(-3.5F, -3.5F, -12.0F, 7, 7, 12, 0.0F);
        this.body1 = new RendererModel(this, 24, 0);
        this.body1.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.body1.addBox(-3.0F, -12.0F, -3.0F, 6, 12, 6, 0.0F);
        this.body = new RendererModel(this, 0, 0);
        this.body.setRotationPoint(0.0F, 26.0F, 0.0F);
        this.body.addBox(-3.0F, -12.0F, -3.0F, 6, 12, 6, 0.0F);
        this.body1.addChild(this.body2);
        this.body2.addChild(this.body3);
        this.body3.addChild(this.head);
        this.body.addChild(this.body1);

        parts = new RendererModel[]{head, body3, body2, body1};
    }

    @Override
    public void render(EntityGiantGardenEel entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.render(f5);
    }

    @Override
    public void setLivingAnimations(EntityGiantGardenEel entityIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        float slam = entityIn.getSlamTimer();
        this.body.rotateAngleX = (250 - slam) / 180f;
        if (slam == 250) for (int i = 0; i < parts.length; i++) {
            float degree = 0.25f * (i + 1);
            parts[i].rotateAngleZ = MathHelper.cos(entityIn.ticksExisted * 0.05f) * degree * 0.1f;
        }
    }
}
