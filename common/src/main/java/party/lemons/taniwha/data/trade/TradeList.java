package party.lemons.taniwha.data.trade;

import com.google.common.collect.ArrayListMultimap;
import party.lemons.taniwha.data.trade.listing.TItemListing;

import java.util.List;

public class TradeList
{
	private final ArrayListMultimap<Integer, TItemListing> TRADES = ArrayListMultimap.create();

	public void addListing(int level, TItemListing listing)
	{
		TRADES.put(level, listing);
	}

	public List<TItemListing> getListingsForLevel(int level)
	{
		return TRADES.get(level);
	}
}
