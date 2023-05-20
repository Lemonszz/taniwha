package party.lemons.taniwha.hooks;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;

import java.util.HashMap;

public class PotteryPatternHooks
{
	public static void addPotteryPatternItem(Item item, ResourceLocation pattern)
	{
		DecoratedPotPatterns.ITEM_TO_POT_TEXTURE = new HashMap<>(DecoratedPotPatterns.ITEM_TO_POT_TEXTURE);
		DecoratedPotPatterns.ITEM_TO_POT_TEXTURE.put(item, ResourceKey.create(Registries.DECORATED_POT_PATTERNS, pattern));
	}
}
