package rando.beasts.common.item.crafting;

import com.google.common.collect.ImmutableList;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import rando.beasts.common.init.BeastsItems;
import rando.beasts.common.init.BeastsRecipes;

public class RecipeCoconutJuice extends ShapelessRecipes {
    
    public RecipeCoconutJuice() {
        super("", new ItemStack(BeastsItems.COCONUT_JUICE), NonNullList.from(Ingredient.EMPTY, Ingredient.fromItems(ImmutableList.copyOf(Item.REGISTRY).stream().filter(item -> item instanceof ItemSword).toArray(Item[]::new)), Ingredient.fromItem(BeastsItems.COCONUT)));
        setRegistryName("coconut_juice");
        BeastsRecipes.LIST.add(this);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack.getItem() instanceof ItemSword) stack.setItemDamage(stack.getItemDamage() - 1);
        }
        return super.getCraftingResult(inv);
    }
}
