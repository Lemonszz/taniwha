package party.lemons.taniwha.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import party.lemons.taniwha.item.modifier.ItemWithModifiers;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ItemHelper {

    public static final Consumer<Item> MODIFIER_CONSUMER = (i)->{
        if(i instanceof ItemWithModifiers<?> modifierItem)
        {
            modifierItem.initModifiers();
        }
    };

    public static RegistrySupplier<Item> registerItem(DeferredRegister<Item> register, ResourceLocation location, Supplier<Item> itemSupplier)
    {
        RegistrySupplier<Item> registered = register.register(location, itemSupplier);
        registered.listen(MODIFIER_CONSUMER);

        return registered;
    }
}
