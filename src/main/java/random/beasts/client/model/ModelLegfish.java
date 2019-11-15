package random.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * ModelLegfish - Coda
 * Created using Tabula 7.1.0
 */
public class ModelLegfish extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer fins;
    public ModelRenderer head;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;
    public ModelRenderer legLeft;
    public ModelRenderer legRight;
    public ModelRenderer tail;

    public ModelLegfish() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.legLeft = new ModelRenderer(this, 0, 0);
        this.legLeft.setRotationPoint(1.0F, 1.0F, 1.0F);
        this.legLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.fins = new ModelRenderer(this, 0, 3);
        this.fins.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.fins.addBox(0.0F, -4.0F, 0.0F, 0, 6, 7, 0.0F);
        this.legRight = new ModelRenderer(this, 0, 0);
        this.legRight.setRotationPoint(-1.0F, 1.0F, 1.0F);
        this.legRight.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.tail = new ModelRenderer(this, 0, 16);
        this.tail.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.tail.addBox(0.0F, -1.0F, 0.0F, 0, 2, 2, 0.0F);
        this.armLeft = new ModelRenderer(this, 11, 0);
        this.armLeft.setRotationPoint(1.0F, 1.0F, -1.5F);
        this.armLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.head = new ModelRenderer(this, 15, 4);
        this.head.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.head.addBox(-0.5F, 0.0F, -3.0F, 1, 2, 3, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.body.addBox(-1.0F, -2.0F, -3.0F, 2, 4, 5, 0.0F);
        this.armRight = new ModelRenderer(this, 11, 0);
        this.armRight.setRotationPoint(-1.0F, 1.0F, -1.5F);
        this.armRight.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.body.addChild(this.legLeft);
        this.body.addChild(this.fins);
        this.body.addChild(this.legRight);
        this.body.addChild(this.tail);
        this.body.addChild(this.armLeft);
        this.body.addChild(this.head);
        this.body.addChild(this.armRight);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    	if(((EntityLivingBase)entity).isChild()) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
        }
        if (entity.isSneaking()) GlStateManager.translate(0.0F, 0.2F, 0.0F);
        this.body.render(f5);
    }
    
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
