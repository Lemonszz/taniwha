package party.lemons.taniwha.item;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import party.lemons.taniwha.TConstants;
import party.lemons.taniwha.item.modifier.ItemModifier;
import party.lemons.taniwha.item.modifier.ItemWithModifiers;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TItems {
    public static final Multimap<Item, ItemModifier> MODIFIERS = ArrayListMultimap.create();

    public static final DeferredRegister<Item> TITEM_REGISTER = DeferredRegister.create(TConstants.MOD_ID, Registry.ITEM_REGISTRY);

    public static final Consumer<Item> MODIFIER_CONSUMER = (i)->{
        if(i instanceof ItemWithModifiers<?>)
        {
            Collection<ItemModifier> mods = TItems.MODIFIERS.get(i);
            mods.forEach(m->m.accept(i));
        }
    };

    public static RegistrySupplier<Item> registerItem(DeferredRegister<Item> register, ResourceLocation location, Supplier<Item> itemSupplier)
    {
        RegistrySupplier<Item> registered = register.register(location, itemSupplier);
        registered.listen(MODIFIER_CONSUMER);

        return registered;
    }

    public static void init()
    {
        TITEM_REGISTER.register();
    }
}
