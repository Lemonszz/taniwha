package party.lemons.taniwha.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public final class RegistryHelper
{
    public static <T> void registerObject(Registry<T> registry, ResourceLocation id, RegistrySupplier<T> object)
    {
        DeferredRegister<T> r = DeferredRegister.create(id.getNamespace(), (ResourceKey)registry.key());
        r.register(id, object);
        r.register();
    }

    public static <T> void registerObject(Registry<T> registry, ResourceLocation id, Supplier<T> object)
    {
        DeferredRegister<T> r = DeferredRegister.create(id.getNamespace(), (ResourceKey)registry.key());
        r.register(id, object);
        r.register();
    }

    public static <T> void register(DeferredRegister<T> register, ResourceLocation location, Supplier<T> object)
    {
        register(register, location, object, new RegistryCallback[0]);
    }

    public static <T> void register(DeferredRegister<T> register, ResourceLocation location, Supplier<T> object, RegistryCallback<T>... callbacks)
    {
        register.register(location, object);

        for(RegistryCallback<T> callback : callbacks)
        {
            callback.callback(register, location, object);
        }
    }

    public interface RegistryCallback<T>
    {
        void callback(DeferredRegister<T> register, ResourceLocation id, Supplier<T> registryObject);
    }

    private RegistryHelper()
    {
    }
}