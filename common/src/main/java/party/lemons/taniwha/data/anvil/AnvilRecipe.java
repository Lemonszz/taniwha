package party.lemons.taniwha.data.anvil;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface AnvilRecipe
{
    Ingredient getIngredientA();
    Ingredient getIngredientB();
    ItemStack getResult();
    int getCost();

    AnvilRecipeType<?> type();
}
