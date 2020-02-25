package random.beasts.common.item;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.BeastsMod;

import javax.annotation.Nullable;

public class BeastsArmor extends ArmorItem {

    public BeastsArmor(String name, ArmorMaterial material, EquipmentSlotType armorType) {
        super(material, 0, armorType);
        BeastsUtils.addToRegistry(this, name);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        A armorModel = BeastsMod.proxy.getArmorModel(this, armorSlot);
        if (armorModel != null) {
            armorModel.isSneak = _default.isSneak;
            //armorModel.isRiding = _default.isRiding;
            armorModel.isChild = _default.isChild;
            armorModel.rightArmPose = _default.rightArmPose;
            armorModel.leftArmPose = _default.leftArmPose;
            if (entityLiving instanceof ArmorStandEntity) armorModel.boxList.forEach(a -> {
                a.rotateAngleX = 0;
                a.rotateAngleY = 0;
                a.rotateAngleZ = 0;
            });
        }

        return armorModel;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return BeastsMod.proxy.getArmorTexture(this, slot);
    }
}
