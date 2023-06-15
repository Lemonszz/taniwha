package party.lemons.taniwha.compat.rei;

import com.google.common.collect.Lists;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.plugin.common.displays.anvil.DefaultAnvilDisplay;
import net.minecraft.world.item.ItemStack;
import party.lemons.taniwha.data.anvil.AnvilRecipe;
import party.lemons.taniwha.data.anvil.AnvilRecipes;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TaniwhaReiClient implements REIClientPlugin
{
    @Override
    public void registerDisplays(DisplayRegistry registry) {

        for(AnvilRecipe recipe : AnvilRecipes.getRecipes())
        {
            List<ItemStack> left = Arrays.asList(recipe.getIngredientA().getItems());
            List<ItemStack> right = Arrays.asList(recipe.getIngredientB().getItems());
            me.shedaniel.rei.plugin.common.displays.anvil.AnvilRecipe reiRecipe = new me.shedaniel.rei.plugin.common.displays.anvil.AnvilRecipe(AnvilRecipes.getID(recipe), left, right, Lists.newArrayList(recipe.getResult().copy()));
            registry.add(new DefaultAnvilDisplay(reiRecipe));
        }
    }
}
