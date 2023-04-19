package party.lemons.taniwha.data.trade;

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
import net.minecraft.world.item.crafting.Ingredient;
import party.lemons.taniwha.data.trade.listing.TItemListing;
import party.lemons.taniwha.data.trade.listing.TradeTypes;
import party.lemons.taniwha.hooks.block.BrewingStandHooks;

import java.util.Map;

public class TradeListReloadListener extends SimpleJsonResourceReloadListener
{
	public static final String PATH = "trade_lists";
	private static final Gson GSON = new Gson();

	public TradeListReloadListener() {
		super(GSON, PATH);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> elements, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
		TradeLists.clear();

		elements.forEach((l, json)->{
			JsonObject object = json.getAsJsonObject();

			//Empty objects ( {} ) are ignored. This is so you can override a fuel.
			if(!object.entrySet().isEmpty())
			{
				if(object.has("trades"))
				{
					TradeList list = new TradeList();
					JsonArray trades = object.getAsJsonArray("trades");
					for(int i = 0; i < trades.size(); i++)
					{
						JsonArray levelList = trades.get(i).getAsJsonArray();
						for(int k = 0; k < levelList.size(); k++) {

							JsonObject listingJSON = levelList.get(k).getAsJsonObject();
							DataResult<TItemListing> parsed = TradeTypes.CODEC.parse(JsonOps.INSTANCE, listingJSON);
							if (parsed.result().isPresent()) {
								list.addListing(i + 1, parsed.result().get());
							}
						}
					}

					TradeLists.addTradeList(l, list);
				}
			}
		});
	}
}
