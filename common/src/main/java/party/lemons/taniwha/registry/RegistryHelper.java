package party.lemons.taniwha.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Locale;
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

    public static class BlockWithItemCallback implements RegistryCallback<Block>
    {
        private final CreativeModeTab group;

        public BlockWithItemCallback(CreativeModeTab group)
        {
            this.group = group;
        }

        @Override
        public void callback(DeferredRegister<Block> register, ResourceLocation id, Supplier<Block> registryObject) {
            Block bl = registryObject.get();

            if(bl instanceof BlockWithItem bi)
            {
                BlockWithItem info = (BlockWithItem) bl;
                if(!info.hasItem()) return;

                info.registerItem(id, group);
            }
        }
    }

    private RegistryHelper()
    {
    }
}