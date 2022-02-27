package party.lemons.taniwha.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Ingredient;
import party.lemons.taniwha.hooks.block.BrewingStandHooks;

import java.util.Map;

public class BrewingFuelReloadListener extends SimpleJsonResourceReloadListener
{
    public static final String BREWING_FUEL = "brewing_fuel";
    private static final Gson GSON = new Gson();

    public BrewingFuelReloadListener() {
        super(GSON, BREWING_FUEL);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> elements, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        BrewingStandHooks.clearBrewingFuel();

        elements.forEach((l, json)->{
            JsonObject object = json.getAsJsonObject();

            //Empty objects ( {} ) are ignored. This is so you can override a fuel.
            if(!object.entrySet().isEmpty())
            {
                Ingredient ingredient = Ingredient.fromJson(object.getAsJsonObject("item"));
                int fuel = object.getAsJsonPrimitive("fuel").getAsInt();
                BrewingStandHooks.registerBrewingFuelItem(ingredient, fuel);
            }
        });
    }
}
