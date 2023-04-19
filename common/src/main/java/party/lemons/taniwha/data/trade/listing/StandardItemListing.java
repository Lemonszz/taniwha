package party.lemons.taniwha.data.trade.listing;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;

public class StandardItemListing extends TItemListing
{
	public static final Codec<StandardItemListing> CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
						ItemStack.CODEC.fieldOf("item1").forGetter(i->i.item1),
						ItemStack.CODEC.optionalFieldOf("item2", ItemStack.EMPTY).forGetter(i->i.item2),
						ItemStack.CODEC.fieldOf("result").forGetter(i->i.result),
						Codec.INT.optionalFieldOf("uses", 0).forGetter(i->i.uses),
						Codec.INT.fieldOf("max_uses").forGetter(i->i.maxUses),
						Codec.INT.optionalFieldOf("xp", 1).forGetter(i->i.xp),
						Codec.FLOAT.optionalFieldOf("price_multiplier", 0.05F).forGetter(i->i.priceMultiplier),
						Codec.INT.optionalFieldOf("demand", 0).forGetter(i->i.demand)
					)
					.apply(instance, StandardItemListing::new));

	private final ItemStack item1;
	private final ItemStack item2;
	private final ItemStack result;
	private final int uses;
	private final int maxUses;
	private final int xp;
	private final float priceMultiplier;
	private final int demand;

	public StandardItemListing(ItemStack item1, ItemStack item2, ItemStack result, int uses, int maxUses, int xp, float priceMultiplier, int demand)
	{
		this.item1 = item1;
		this.item2 = item2;
		this.result = result;
		this.uses = uses;
		this.maxUses = maxUses;
		this.xp = xp;
		this.priceMultiplier = priceMultiplier;
		this.demand = demand;
	}

	@Override
	public TradeTypes.TradeType<?> type()
	{
		return TradeTypes.STANDARD.get();
	}

	@Nullable
	@Override
	public MerchantOffer getOffer(Entity entity, RandomSource randomSource)
	{
		return new MerchantOffer(item1, item2, result, uses, maxUses, xp, priceMultiplier, demand);
	}
}
