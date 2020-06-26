package net.msrandom.beasts.common.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.msrandom.beasts.api.main.BeastsReference;
import net.msrandom.beasts.api.main.BeastsUtils;
import net.msrandom.beasts.proxy.ArmorData;

import javax.annotation.Nullable;

public class BeastsArmor extends ItemArmor {

    public BeastsArmor(String name, ArmorMaterial material, EntityEquipmentSlot armorType) {
        super(material, 0, armorType);
        BeastsUtils.addToRegistry(this, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
        ModelBiped armorModel = ArmorData.MODELS.getFrom(this, armorSlot);
        if (armorModel != null) {
            armorModel.isSneak = defaultModel.isSneak;
            armorModel.isRiding = defaultModel.isRiding;
            armorModel.isChild = defaultModel.isChild;
            armorModel.rightArmPose = defaultModel.rightArmPose;
            armorModel.leftArmPose = defaultModel.leftArmPose;
            if (entityLiving instanceof EntityArmorStand) armorModel.boxList.forEach(a -> {
                a.rotateAngleX = 0;
                a.rotateAngleY = 0;
                a.rotateAngleZ = 0;
            });
        }

        return armorModel;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        String texture = ArmorData.TEXTURES.getFrom(this, slot);
        return texture == null ? null : BeastsReference.ID + ":textures/models/armor/" + texture + ".png";
    }
}
