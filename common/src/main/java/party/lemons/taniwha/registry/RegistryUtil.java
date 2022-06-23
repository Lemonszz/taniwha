package party.lemons.taniwha.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class RegistryUtil {
	public static <T> Holder<T> findHolder(RegistryAccess access, ResourceKey<Registry<T>> registryKey, ResourceLocation location)
	{
		//TODO: how efficient is this?
		return access.registry(registryKey).get().getHolder(ResourceKey.create(registryKey, location)).get();
	}

	public static <T> ResourceLocation findID(RegistryAccess access, ResourceKey<Registry<T>> registryKey, T location)
	{
		return access.registry(registryKey).get().getKey(location);
	}

	public static <T> boolean resourceKeyEquals(ResourceKey<T> key1, ResourceKey<T> key2)
	{
		return key1.location().equals(key2.location());
	}
}
