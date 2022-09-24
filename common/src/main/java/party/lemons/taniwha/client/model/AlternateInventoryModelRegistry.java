package party.lemons.taniwha.client.model;

import com.google.common.collect.Maps;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Map;

public class AlternateInventoryModelRegistry
{
	private static final Map<Item, ModelResourceLocation> ALTERNATE_LOCATIONS = Maps.newHashMap();

	public static void register(Item item, ModelResourceLocation location)
	{
		ALTERNATE_LOCATIONS.put(item, location);
	}

	public static boolean hasAlternateModel(Item item)
	{
		return ALTERNATE_LOCATIONS.containsKey(item);
	}

	public static ModelResourceLocation getAlternateModel(Item item)
	{
		return ALTERNATE_LOCATIONS.get(item);
	}
}
