package party.lemons.taniwha.util;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import party.lemons.taniwha.registry.RegistryUtil;

public record RegistryAccessSupplier<T>(ResourceKey<Registry<T>> registryKey, ResourceLocation location)
{
	public Holder<T> get(RegistryAccess registryAccess)
	{
		return RegistryUtil.findHolder(registryAccess, registryKey, location);
	}
}