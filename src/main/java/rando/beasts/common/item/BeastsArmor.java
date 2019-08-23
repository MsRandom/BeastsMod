package rando.beasts.common.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rando.beasts.common.main.BeastsMod;
import rando.beasts.common.utils.BeastsUtil;

public class BeastsArmor extends ItemArmor {

	public BeastsArmor(String name, ArmorMaterial material, int renderIndex, EntityEquipmentSlot armorType) {
		super(material, renderIndex, armorType);
		BeastsUtil.addToRegistry(this, name, true);
	}

	@Override 	
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
		ModelBiped armorModel = BeastsMod.proxy.getArmorModel(this, armorSlot);
		if(armorModel != null) {
			armorModel.isSneak = defaultModel.isSneak;
			armorModel.isRiding = defaultModel.isRiding;
			armorModel.isChild = defaultModel.isChild;
			armorModel.rightArmPose = defaultModel.rightArmPose;
			armorModel.leftArmPose = defaultModel.leftArmPose;
		}
		return armorModel;
	}
}