package random.beasts.common.item.crafting;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.oredict.OreDictionary;
import random.beasts.common.init.BeastsItems;
import random.beasts.common.init.BeastsRecipes;

public class RecipeCoconutJuice extends ShapelessRecipes {
    /**
     * if anyone can fix this, please do cause i literally can't figure it out without doing a lot of hacky shit
     *
     * @see random.beasts.common.event.CommonEvents#craftItem(PlayerEvent.ItemCraftedEvent) for the current logic
     */
    public RecipeCoconutJuice() {
        super("", new ItemStack(BeastsItems.COCONUT_JUICE), NonNullList.from(Ingredient.EMPTY, Ingredient.fromStacks(ImmutableList.copyOf(Item.REGISTRY).stream().filter(item -> item instanceof ItemSword).map(item -> new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE)).toArray(ItemStack[]::new)), Ingredient.fromItem(BeastsItems.COCONUT)));
        setRegistryName("coconut_juice");
        BeastsRecipes.LIST.add(this);
    }
}
