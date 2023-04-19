package party.lemons.taniwha.data.trade.listing;

import net.minecraft.world.entity.npc.VillagerTrades;

public abstract class TItemListing implements VillagerTrades.ItemListing
{
	public abstract TradeTypes.TradeType<?> type();
}
