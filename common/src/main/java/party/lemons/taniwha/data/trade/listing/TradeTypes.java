package party.lemons.taniwha.data.trade.listing;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import party.lemons.taniwha.TConstants;
import party.lemons.taniwha.Taniwha;
import party.lemons.taniwha.util.TagUtil;

import java.util.Optional;

public class TradeTypes
{
	public static final ResourceKey<Registry<TradeType<?>>> KEY = ResourceKey.createRegistryKey(Taniwha.id("trade"));
	public static final Registrar<TradeType<?>> REGISTRY = RegistrarManager.get(TConstants.MOD_ID).builder(KEY.location(), new TradeType<?>[0]).build();
	public static final DeferredRegister<TradeType<?>> TRADE_TYPES = DeferredRegister.create(TConstants.MOD_ID, KEY);

	public static final RegistrySupplier<TradeType<?>> STANDARD = TRADE_TYPES.register(Taniwha.id("standard"), ()->new TradeType<>(StandardItemListing.CODEC));


	public static Codec<TradeType<?>> byNameCodec() {
		Codec<TradeType<?>> codec = ResourceLocation.CODEC
				.flatXmap(
						resourceLocation -> Optional.ofNullable(REGISTRY.get(resourceLocation))
								.map(DataResult::success)
								.orElseGet(() -> DataResult.error(() -> "Unknown registry key in " + REGISTRY.key() + ": " + resourceLocation)),
						object -> REGISTRY.getKey(object)
								.map(ResourceKey::location)
								.map(DataResult::success)
								.orElseGet(() -> DataResult.error(() -> "Unknown registry element in " + REGISTRY.key() + ":" + object))
				);
		Codec<TradeType<?>> codec2 = ExtraCodecs.idResolverCodec(object -> REGISTRY.getKey(object).isPresent() ? REGISTRY.getRawId(object) : -1, REGISTRY::byRawId, -1);
		return ExtraCodecs.overrideLifecycle(ExtraCodecs.orCompressed(codec, codec2), (e)-> Lifecycle.stable(),  (e)->Lifecycle.stable());
	}
	public static final Codec<TItemListing> CODEC = byNameCodec().dispatch(TItemListing::type, TradeType::codec);

	public static void init()
	{
		TRADE_TYPES.register();
	}

	public record TradeType<T extends TItemListing>(Codec<T> codec)
	{
	}
}
