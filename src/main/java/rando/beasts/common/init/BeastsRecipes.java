package rando.beasts.common.init;

import net.minecraft.item.crafting.IRecipe;
import rando.beasts.common.item.crafting.RecipeCoconutJuice;

import java.util.ArrayList;
import java.util.List;

public class BeastsRecipes {
    public static final List<IRecipe> LIST = new ArrayList<>();

    public static final IRecipe COCONUT_JUICE = new RecipeCoconutJuice();
}
