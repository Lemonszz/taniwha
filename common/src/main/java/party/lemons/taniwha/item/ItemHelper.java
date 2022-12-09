package party.lemons.taniwha.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import party.lemons.taniwha.item.modifier.ItemWithModifiers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ItemHelper {

    private static final Map<String, List<RegistrySupplier<Item>>> modItems = Maps.newHashMap();

    public static final Consumer<Item> MODIFIER_CONSUMER = (i)->{
        if(i instanceof ItemWithModifiers<?> modifierItem)
        {
            modifierItem.initModifiers();
        }
    };

    public static List<RegistrySupplier<Item>> getItems(String modid)
    {
        return modItems.getOrDefault(modid, Lists.newArrayList());
    }

    public static RegistrySupplier<Item> registerItem(DeferredRegister<Item> register, ResourceLocation location, Supplier<Item> itemSupplier)
    {
        RegistrySupplier<Item> registered = register.register(location, itemSupplier);
        registered.listen(MODIFIER_CONSUMER);

        cacheItem(location, registered);

        return registered;
    }

    private static void cacheItem(ResourceLocation location, RegistrySupplier<Item> registered)
    {
        List<RegistrySupplier<Item>> items;
        if(modItems.containsKey(location.getNamespace()))
            items = modItems.get(location.getNamespace());
        else {
            items = new ArrayList<>();
            modItems.put(location.getNamespace(), items);
        }
        items.add(registered);
    }
}
