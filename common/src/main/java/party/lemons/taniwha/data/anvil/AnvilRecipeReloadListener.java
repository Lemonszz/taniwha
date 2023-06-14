package party.lemons.taniwha.data.anvil;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import party.lemons.taniwha.data.trade.TradeList;
import party.lemons.taniwha.data.trade.TradeLists;
import party.lemons.taniwha.data.trade.listing.TItemListing;
import party.lemons.taniwha.data.trade.listing.TradeTypes;

import java.util.Map;

public class AnvilRecipeReloadListener extends SimpleJsonResourceReloadListener
{
	public static final String PATH = "anvil_recipes";
	private static final Gson GSON = new Gson();

	public AnvilRecipeReloadListener() {
		super(GSON, PATH);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> elements, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
		AnvilRecipes.clear();

		elements.forEach((l, json)->{
			JsonObject object = json.getAsJsonObject();

			//Empty objects ( {} ) are ignored. This is so you can override a fuel.
			if(!object.entrySet().isEmpty())
			{
				DataResult<AnvilRecipe> parsedRecipe = AnvilRecipeTypes.CODEC.parse(JsonOps.INSTANCE, object);
				if(parsedRecipe.error().isPresent())
				{
					System.out.println(parsedRecipe.error().get().message());
				}else if(parsedRecipe.result().isPresent())
				{
					AnvilRecipes.add(l, parsedRecipe.result().get());
				}
			}
		});
	}
}
