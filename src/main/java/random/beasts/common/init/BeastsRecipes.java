package random.beasts.common.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.crafting.IRecipe;
import random.beasts.common.item.crafting.RecipeCoconutJuice;

public class BeastsRecipes {
    public static final List<IRecipe> LIST = new ArrayList<>();

    public static final IRecipe COCONUT_JUICE = new RecipeCoconutJuice();
}
