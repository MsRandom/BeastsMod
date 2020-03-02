package random.beasts.common.item;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class ItemPufferArmor extends BeastsArmor {
    public ItemPufferArmor(String type, EquipmentSlotType armorType) {
        super("puffer_" + type, ArmorMaterial.LEATHER, armorType);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        //todo change this to use EnchantmentHelper and ItemEnchantedBook
        if (((NonNullList<ItemStack>) entityIn.getArmorInventoryList()).stream().allMatch(s -> s.getItem() instanceof ItemPufferArmor))
            for (ItemStack s : entityIn.getArmorInventoryList()) {
                boolean flag = false;
                if (s.hasTag() && Objects.requireNonNull(s.getTag()).contains("ench", 9)) {
                    ListNBT list = s.getTag().getList("ench", 10);
                    int index = getEnchantmentIndex(list);
                    if (index != -1) flag = true;
                }
                if (!flag) s.addEnchantment(Enchantments.THORNS, 2);
            }
        else for (ItemStack s : entityIn.getArmorInventoryList()) {
            if (s.getTag() != null && s.getTag().contains("ench", 9)) {
                ListNBT list = s.getTag().getList("ench", 10);
                int index = getEnchantmentIndex(list);
                if (index != -1) list.remove(index);
            }
        }
    }

    private int getEnchantmentIndex(ListNBT list) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            INBT tag = list.get(i);
            if (tag.getId() == 10 && tag instanceof CompoundNBT) {
                CompoundNBT compound = (CompoundNBT) tag;
                if (compound.contains("id") && compound.contains("lvl") && compound.getString("id").equals(ForgeRegistries.ENCHANTMENTS.getKey(Enchantments.THORNS).toString()) && compound.getShort("lvl") == (short) ((byte) 2)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}
