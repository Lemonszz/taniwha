package party.lemons.taniwha.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import party.lemons.taniwha.Taniwha;

import java.util.function.Consumer;

public class TLootContexts
{
	public static final LootContextParamSet GENERIC_ENTITY_LOOT_CONTEXT = register(
			Taniwha.id("generic_entity"),
			builder -> builder
					.required(LootContextParams.THIS_ENTITY)
					.required(LootContextParams.ORIGIN)
					.optional(LootContextParams.LAST_DAMAGE_PLAYER)
	);

	public static LootContextParamSet register(ResourceLocation id, Consumer<LootContextParamSet.Builder> consumer)
	{
		LootContextParamSet.Builder builder = new LootContextParamSet.Builder();
		consumer.accept(builder);
		LootContextParamSet lootContextParamSet = builder.build();
		LootContextParamSet lootContextParamSet2 = LootContextParamSets.REGISTRY.put(id, lootContextParamSet);
		if (lootContextParamSet2 != null) {
			throw new IllegalStateException("Loot table parameter set " + id + " is already registered");
		} else {
			return lootContextParamSet;
		}
	}
}
