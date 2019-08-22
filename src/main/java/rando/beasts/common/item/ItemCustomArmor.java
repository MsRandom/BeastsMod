package rando.beasts.common.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rando.beasts.common.init.BeastsItems;
import rando.beasts.common.main.BeastsMod;
import rando.beasts.common.utils.BeastsUtil;

public class ItemCustomArmor extends ItemArmor {

	public ItemCustomArmor(String unloc, ArmorMaterial material, int renderIndex, EntityEquipmentSlot armorType) {
		super(material, renderIndex, armorType);
		BeastsUtil.addToRegistry(this, unloc, true);
	}

	@Override 	
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
		if(armorSlot != EntityEquipmentSlot.HEAD)
			return super.getArmorModel(entityLiving, itemStack, armorSlot, defaultModel);
		if (itemStack != null) {
			if (itemStack.getItem() instanceof ItemArmor) {
				EntityEquipmentSlot type = ((ItemArmor) itemStack.getItem()).armorType;
				ModelBiped armorModel = null;
				switch (type) {
					case HEAD:
						armorModel = BeastsMod.proxy.getArmorModel(BeastsItems.SPARTAPOD_HELMET, 0);
						break;
					case LEGS:
						armorModel = BeastsMod.proxy.getArmorModel(BeastsItems.SPARTAPOD_HELMET, 0);
						break;
					case FEET:
						armorModel = BeastsMod.proxy.getArmorModel(BeastsItems.SPARTAPOD_HELMET, 0);
						break;
					case CHEST:
						armorModel = BeastsMod.proxy.getArmorModel(BeastsItems.SPARTAPOD_HELMET, 0);
						break;
					default:
						break;
				}
				
				armorModel.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
				armorModel.bipedHeadwear.showModel = armorSlot == EntityEquipmentSlot.HEAD;
				armorModel.bipedBody.showModel = (armorSlot == EntityEquipmentSlot.CHEST) || (armorSlot == EntityEquipmentSlot.CHEST);
				armorModel.bipedRightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				armorModel.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				armorModel.bipedRightLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS)|| (armorSlot == EntityEquipmentSlot.FEET);
				armorModel.bipedLeftLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS) || (armorSlot == EntityEquipmentSlot.FEET);
				
				armorModel.isSneak = defaultModel.isSneak;
				armorModel.isRiding = defaultModel.isRiding;
				armorModel.isChild = defaultModel.isChild;
				armorModel.rightArmPose = defaultModel.rightArmPose;
				armorModel.leftArmPose = defaultModel.leftArmPose;

				return armorModel;
			}
		}
		return null;
	}
}