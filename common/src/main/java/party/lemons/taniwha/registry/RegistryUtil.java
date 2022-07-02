package party.lemons.taniwha.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class RegistryUtil {

	public static <T> Holder<T> findHolder(RegistryAccess access, ResourceKey<Registry<T>> registryKey, ResourceLocation location)
	{
		return access.registry(registryKey).get().getHolder(ResourceKey.create(registryKey, location)).get();
	}

	public static <T> Optional<Holder<T>> findOptionalHolder(RegistryAccess access, ResourceKey<Registry<T>> registryKey, ResourceLocation location)
	{
		return access.registry(registryKey).get().getHolder(ResourceKey.create(registryKey, location));
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
