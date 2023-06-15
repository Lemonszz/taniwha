package party.lemons.taniwha.data.anvil;

import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

public class AnvilRecipes {

    //TODO: REI integration
    private static final Map<ResourceLocation, AnvilRecipe> RECIPES = Maps.newHashMap();

    public static void clear(){
        RECIPES.clear();
    }

    public static void add(ResourceLocation location, AnvilRecipe recipe)
    {
        RECIPES.put(location, recipe);
    }

    public void foreach(Consumer<AnvilRecipe> consumer)
    {
        RECIPES.values().forEach(consumer);
    }

    public static Collection<AnvilRecipe> getRecipes(){
        return RECIPES.values();
    }

    public static ResourceLocation getID(AnvilRecipe recipe)
    {
        for (Map.Entry<ResourceLocation, AnvilRecipe> entry : RECIPES.entrySet()) {
            if (entry.getValue().equals(recipe)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
