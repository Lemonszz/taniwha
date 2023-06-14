package party.lemons.taniwha.data.anvil.impl;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import party.lemons.taniwha.data.TCodecs;
import party.lemons.taniwha.data.anvil.AnvilRecipe;
import party.lemons.taniwha.data.anvil.AnvilRecipeType;
import party.lemons.taniwha.data.anvil.AnvilRecipeTypes;

public record SimpleAnvilRecipe(Ingredient ingredientA, Ingredient ingredientB, ItemStack result, int xpCost) implements AnvilRecipe {

    public static final Codec<SimpleAnvilRecipe> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                            TCodecs.INGREDIENT.fieldOf("ingredient_a").forGetter(SimpleAnvilRecipe::ingredientA),
                            TCodecs.INGREDIENT.fieldOf("ingredient_b").forGetter(SimpleAnvilRecipe::ingredientB),
                            ItemStack.CODEC.fieldOf("result").forGetter(SimpleAnvilRecipe::result),
                            Codec.INT.fieldOf("xp_cost").forGetter(SimpleAnvilRecipe::xpCost)
                    )
                    .apply(instance, SimpleAnvilRecipe::new));

    @Override
    public Ingredient getIngredientA() {
        return ingredientA();
    }

    @Override
    public Ingredient getIngredientB() {
        return ingredientB();
    }

    @Override
    public ItemStack getResult() {
        return result();
    }

    @Override
    public int getCost() {
        return xpCost();
    }

    @Override
    public AnvilRecipeType<?> type() {
        return AnvilRecipeTypes.SIMPLE.get();
    }
}
