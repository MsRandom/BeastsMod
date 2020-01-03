package random.beasts.common.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.List;

public class ItemPufferArmor extends BeastsArmor {
    public ItemPufferArmor(String type, EntityEquipmentSlot armorType) {
        super("puffer_" + type, ArmorMaterial.LEATHER, armorType);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        //todo change this to use EnchantmentHelper and ItemEnchantedBook
        if(((NonNullList<ItemStack>)entityIn.getArmorInventoryList()).stream().allMatch(s -> s.getItem() instanceof ItemPufferArmor)) for (ItemStack s : entityIn.getArmorInventoryList()) {
            boolean flag = false;
            if (s.getTagCompound() != null && s.getTagCompound().hasKey("ench", 9)) {
                NBTTagList list = s.getTagCompound().getTagList("ench", 10);
                int index = getEnchantmentIndex(list);
                if (index != -1) flag = true;
            }
            if(!flag) s.addEnchantment(Enchantments.THORNS, 2);
        } else for (ItemStack s : entityIn.getArmorInventoryList()) {
            if (s.getTagCompound() != null && s.getTagCompound().hasKey("ench", 9)) {
                NBTTagList list = s.getTagCompound().getTagList("ench", 10);
                int index = getEnchantmentIndex(list);
                if (index != -1) list.removeTag(index);
            }
        }
    }

    private int getEnchantmentIndex(NBTTagList list) {
        List<NBTBase> tagList = ReflectionHelper.getPrivateValue(NBTTagList.class, list, "tagList", "field_74747_a", "a");
        int index = -1;
        for (int i = 0; i < tagList.size(); i++) {
            NBTBase tag = tagList.get(i);
            if (tag.getId() == 10 && tag instanceof NBTTagCompound) {
                NBTTagCompound compound = (NBTTagCompound) tag;
                if (compound.hasKey("id") && compound.hasKey("lvl") && compound.getShort("id") == (short) Enchantment.getEnchantmentID(Enchantments.THORNS) && compound.getShort("lvl") == (short) ((byte) 2)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}
