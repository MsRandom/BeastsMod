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
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            if(itemstack.getItem() instanceof ItemSword) {
            	ItemStack item = new ItemStack(itemstack.getItem());
            	item.setItemDamage(itemstack.getItemDamage() + 1);
            	nonnulllist.set(i, item);
            }
            else nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
        }

        return nonnulllist;
    }
}
