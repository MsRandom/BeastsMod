package random.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelButterflyfish - Tazz
 * Created using Tabula 7.1.0
 */
public class ModelButterflyfish extends ModelBase {
    public ModelRenderer Body;
    public ModelRenderer Tailbase;
    public ModelRenderer Dorsalfinright;
    public ModelRenderer Dorsalfinleft;
    public ModelRenderer Pectoralfinleft;
    public ModelRenderer Pectoralfinright;
    public ModelRenderer Antennae;
    public ModelRenderer Pelvicfinright;
    public ModelRenderer Pelvicfinleft;
    public ModelRenderer Tailfin;

    public ModelButterflyfish() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Pectoralfinleft = new ModelRenderer(this, 35, -2);
        this.Pectoralfinleft.mirror = true;
        this.Pectoralfinleft.setRotationPoint(1.0F, 1.5F, -1.5F);
        this.Pectoralfinleft.addBox(0.0F, 0.0F, -1.5F, 0, 5, 3, 0.0F);
        this.Pelvicfinright = new ModelRenderer(this, 29, 4);
        this.Pelvicfinright.setRotationPoint(-0.5F, 1.5F, 2.0F);
        this.Pelvicfinright.addBox(0.0F, 0.0F, -1.0F, 0, 2, 3, 0.0F);
        this.Tailfin = new ModelRenderer(this, 24, -3);
        this.Tailfin.setRotationPoint(0.0F, -0.5F, 3.5F);
        this.Tailfin.addBox(0.0F, -2.0F, -0.5F, 0, 4, 4, 0.0F);
        this.Pectoralfinright = new ModelRenderer(this, 42, -2);
        this.Pectoralfinright.setRotationPoint(-1.0F, 1.5F, -1.5F);
        this.Pectoralfinright.addBox(0.0F, 0.0F, -1.5F, 0, 5, 3, 0.0F);
        this.Pelvicfinleft = new ModelRenderer(this, 24, 4);
        this.Pelvicfinleft.mirror = true;
        this.Pelvicfinleft.setRotationPoint(0.5F, 1.5F, 2.0F);
        this.Pelvicfinleft.addBox(0.0F, 0.0F, -1.0F, 0, 2, 3, 0.0F);
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.setRotationPoint(0.0F, 22.5F, -1.5F);
        this.Body.addBox(-1.5F, -1.5F, -4.0F, 3, 3, 8, 0.0F);
        this.Dorsalfinleft = new ModelRenderer(this, 0, 5);
        this.Dorsalfinleft.mirror = true;
        this.Dorsalfinleft.setRotationPoint(1.0F, -1.5F, 0.0F);
        this.Dorsalfinleft.addBox(0.0F, -9.0F, -3.5F, 0, 9, 7, 0.0F);
        this.Tailbase = new ModelRenderer(this, 15, 0);
        this.Tailbase.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Tailbase.addBox(-0.5F, -1.0F, 0.0F, 1, 2, 3, 0.0F);
        this.Dorsalfinright = new ModelRenderer(this, 16, 5);
        this.Dorsalfinright.setRotationPoint(-1.0F, -1.5F, 0.0F);
        this.Dorsalfinright.addBox(0.0F, -9.0F, -3.5F, 0, 9, 7, 0.0F);
        this.Antennae = new ModelRenderer(this, 0, 0);
        this.Antennae.setRotationPoint(0.0F, -1.5F, -4.0F);
        this.Antennae.addBox(-0.5F, -3.0F, -2.0F, 1, 3, 2, 0.0F);
        this.Body.addChild(this.Pectoralfinleft);
        this.Body.addChild(this.Pelvicfinright);
        this.Tailbase.addChild(this.Tailfin);
        this.Body.addChild(this.Pectoralfinright);
        this.Body.addChild(this.Pelvicfinleft);
        this.Body.addChild(this.Dorsalfinleft);
        this.Body.addChild(this.Tailbase);
        this.Body.addChild(this.Dorsalfinright);
        this.Body.addChild(this.Antennae);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Body.render(f5);
    }

    float speed = 1.0f;
    float degree = 1.0f;

    @Override
    public void setRotationAngles(float f, float f1, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(f, f1, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.Pelvicfinright.rotateAngleZ = MathHelper.cos((f * speed * 1.0F) + (float) Math.PI) * (degree * 1.0F) * f1 * 0.5F + 1.0F;
        this.Pelvicfinleft.rotateAngleZ = MathHelper.cos((f * speed * 1.0F) + (float) Math.PI) * (degree * -1.0F) * f1 * 0.5F + -1.0F;
        this.Pectoralfinleft.rotateAngleZ = MathHelper.cos(3.0F + (f * speed * 1.0F) + (float) Math.PI) * (degree * -1.0F) * f1 * 0.5F + -1.0F;
        this.Pectoralfinright.rotateAngleZ = MathHelper.cos(3.0F + (f * speed * 1.0F) + (float) Math.PI) * (degree * 1.0F) * f1 * 0.5F + 1.0F;
        this.Dorsalfinright.rotateAngleZ = MathHelper.cos((f * speed * 1.0F) + (float) Math.PI) * (degree * 3.0F) * f1 * 0.5F + -1.0F;
        this.Dorsalfinleft.rotateAngleZ = MathHelper.cos((f * speed * 1.0F) + (float) Math.PI) * (degree * -3.0F) * f1 * 0.5F + 1.0F;
        this.Tailbase.rotateAngleY = MathHelper.cos((f * speed * 1.0F) + (float) Math.PI) * (degree * 0.5F) * f1 * 0.5F;
        this.Tailfin.rotateAngleY = MathHelper.cos((f * speed * 1.0F) + (float) Math.PI) * (degree * 1.0F) * f1 * 0.5F;

    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
