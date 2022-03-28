package party.lemons.taniwha.item;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import party.lemons.taniwha.TConstants;
import party.lemons.taniwha.item.modifier.ItemModifier;

public class TItems {
    public static final Multimap<Item, ItemModifier> MODIFIERS = ArrayListMultimap.create();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(TConstants.MOD_ID, Registry.ITEM_REGISTRY);

    public static void init()
    {
        ITEMS.register();
    }

    public static void runModifiers()
    {
    //    MODIFIERS.forEach((b, a)->a.accept(b));
    }
}
