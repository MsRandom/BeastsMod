package rando.beasts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import rando.beasts.common.entity.monster.EntityGiantGardenEel;

public class ModelGiantGardenEel extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer body1;
    public ModelRenderer body2;
    public ModelRenderer body3;
    public ModelRenderer head;
    private ModelRenderer[] parts;

    public ModelGiantGardenEel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body2 = new ModelRenderer(this, 0, 18);
        this.body2.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.body2.addBox(-3.0F, -12.0F, -3.0F, 6, 12, 6, 0.0F);
        this.body3 = new ModelRenderer(this, 24, 18);
        this.body3.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.body3.addBox(-3.0F, -12.0F, -3.0F, 6, 12, 6, 0.0F);
        this.head = new ModelRenderer(this, 0, 36);
        this.head.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.head.addBox(-3.5F, -3.5F, -12.0F, 7, 7, 12, 0.0F);
        this.body1 = new ModelRenderer(this, 24, 0);
        this.body1.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.body1.addBox(-3.0F, -12.0F, -3.0F, 6, 12, 6, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 26.0F, 0.0F);
        this.body.addBox(-3.0F, -12.0F, -3.0F, 6, 12, 6, 0.0F);
        this.body1.addChild(this.body2);
        this.body2.addChild(this.body3);
        this.body3.addChild(this.head);
        this.body.addChild(this.body1);

        parts = new ModelRenderer[]{head, body3, body2, body1};
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entityIn,float limbSwing, float limbSwingAmount, float partialTickTime) {
        EntityGiantGardenEel gardenEel = (EntityGiantGardenEel)entityIn;
        float slam = gardenEel.getSlamTimer();
        
         this.body.rotateAngleX = (250 - slam)/180f;
         if(slam == 250) for(int i = 0; i < parts.length; i++) {
            float degree = 0.25f * (i + 1);
            parts[i].rotateAngleZ = MathHelper.cos(gardenEel.ticksExisted * 0.05f) * degree * 0.1f;
        }
        
        if(body.rotateAngleX > 0) System.out.println(gardenEel.getUniqueID());
    }
}
