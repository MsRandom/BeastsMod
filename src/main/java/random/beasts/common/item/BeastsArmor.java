package random.beasts.common.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import random.beasts.api.main.BeastsUtils;
import random.beasts.common.BeastsMod;

import javax.annotation.Nullable;

public class BeastsArmor extends ItemArmor {

    public BeastsArmor(String name, ArmorMaterial material, EntityEquipmentSlot armorType) {
        super(material, 0, armorType);
        BeastsUtils.addToRegistry(this, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
        ModelBiped armorModel = BeastsMod.proxy.getArmorModel(this, armorSlot);
        if (armorModel != null) {
            armorModel.isSneak = defaultModel.isSneak;
            armorModel.isRiding = defaultModel.isRiding;
            armorModel.isChild = defaultModel.isChild;
            armorModel.rightArmPose = defaultModel.rightArmPose;
            armorModel.leftArmPose = defaultModel.leftArmPose;
        }

/*		if(entityLiving instanceof EntityArmorStand){
			armorModel.boxList.forEach(e -> {e.rotateAngleY = entityLiving.rotationYaw;});
		}*/

        return armorModel;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return BeastsMod.proxy.getArmorTexture(this, slot);
    }
}
