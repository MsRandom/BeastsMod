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
        //if this is planned to be used as more than just a helmet then this will need to be changed
        this.bipedHead.showModel = true;
        this.bipedHeadwear.showModel = true;
        this.bipedBody.showModel = false;
        this.bipedRightArm.showModel = false;
        this.bipedLeftArm.showModel = false;
        this.bipedRightLeg.showModel = false;
        this.bipedLeftLeg.showModel = false;
    }
}
