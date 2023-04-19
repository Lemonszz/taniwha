package party.lemons.taniwha.data.trade;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import party.lemons.taniwha.data.trade.listing.TItemListing;

import java.util.Collection;

public class TradeList
{
	private final Multimap<Integer, TItemListing> TRADES = ArrayListMultimap.create();

	public void addListing(int level, TItemListing listing)
	{
		TRADES.put(level, listing);
	}

	public Collection<TItemListing> getListingsForLevel(int level)
	{
		return TRADES.get(level);
	}
}
