package party.lemons.taniwha.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

public class TagUtil
{
	/**
	 *	Generic is tag method.
	 *	Pass in the registry key, tag and the object to check.
	 */
	public static <T> boolean is(ResourceKey<? extends Registry<?>> key, TagKey<T> tag, T obj){
		return BuiltInRegistries.REGISTRY.get((ResourceKey)key).wrapAsHolder(obj).is(tag);
	}
}
