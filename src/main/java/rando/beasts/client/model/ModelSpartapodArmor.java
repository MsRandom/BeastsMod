package rando.beasts.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSpartapodArmor extends ModelBiped {
    public ModelRenderer helmetBase;
    public ModelRenderer headFin;

    public ModelSpartapodArmor() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.headFin = new ModelRenderer(this, 65, 11);
        this.headFin.setRotationPoint(0.0F, -9.0F, 0.5F);
        this.headFin.addBox(0.0F, -6.0F, -6.0F, 0, 12, 13, 0.0F);
        this.helmetBase = new ModelRenderer(this, 66, 0);
        this.helmetBase.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.helmetBase.addBox(-4.5F, -8.5F, -4.5F, 9, 11, 9, 0.0F);
        this.helmetBase.addChild(this.headFin);
        this.bipedHead.addChild(this.helmetBase);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
