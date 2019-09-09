package rando.beasts.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHermitHelm extends ModelBiped {
    public ModelRenderer field_178723_h;
    public ModelRenderer field_178721_j;
    public ModelRenderer field_78116_c;
    public ModelRenderer field_78115_e;
    public ModelRenderer field_178724_i;
    public ModelRenderer field_178722_k;
    public ModelRenderer shell1;
    public ModelRenderer shell2;
    public ModelRenderer shell3;

    public ModelHermitHelm() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.shell3 = new ModelRenderer(this, 70, 40);
        this.shell3.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.shell3.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(shell3, 0.0F, 0.0F, -0.2617993877991494F);
        this.field_178724_i = new ModelRenderer(this, 32, 48);
        this.field_178724_i.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.field_178724_i.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.shell1 = new ModelRenderer(this, 70, 0);
        this.shell1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shell1.addBox(-5.0F, -8.5F, -5.0F, 10, 7, 10, 0.0F);
        this.field_78116_c = new ModelRenderer(this, 0, 0);
        this.field_78116_c.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.field_78116_c.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.field_178722_k = new ModelRenderer(this, 16, 48);
        this.field_178722_k.setRotationPoint(1.899999976158142F, 12.0F, 0.0F);
        this.field_178722_k.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.field_178721_j = new ModelRenderer(this, 0, 16);
        this.field_178721_j.setRotationPoint(-1.899999976158142F, 12.0F, 0.0F);
        this.field_178721_j.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.shell2 = new ModelRenderer(this, 70, 22);
        this.shell2.setRotationPoint(2.0F, -8.0F, 0.0F);
        this.shell2.addBox(-4.0F, -3.0F, -4.0F, 8, 6, 8, 0.0F);
        this.setRotateAngle(shell2, 0.0F, 0.0F, 0.5235987755982988F);
        this.field_178723_h = new ModelRenderer(this, 40, 16);
        this.field_178723_h.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.field_178723_h.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.field_78115_e = new ModelRenderer(this, 16, 16);
        this.field_78115_e.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.field_78115_e.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.shell2.addChild(this.shell3);
        this.field_78116_c.addChild(this.shell1);
        this.shell1.addChild(this.shell2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.field_178724_i.render(f5);
        this.field_78116_c.render(f5);
        this.field_178722_k.render(f5);
        this.field_178721_j.render(f5);
        this.field_178723_h.render(f5);
        this.field_78115_e.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
