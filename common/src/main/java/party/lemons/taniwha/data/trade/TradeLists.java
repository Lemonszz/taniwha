package party.lemons.taniwha.data.trade;

import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class TradeLists
{
	private static final Map<ResourceLocation, TradeList> TRADE_LISTS = Maps.newHashMap();

	public static void addTradeList(ResourceLocation id, TradeList tradeList)
	{
		TRADE_LISTS.put(id, tradeList);
	}

	public static void clear()
	{
		TRADE_LISTS.clear();
	}

	public static TradeList get(ResourceLocation id)
	{
		return TRADE_LISTS.get(id);
	}
}
