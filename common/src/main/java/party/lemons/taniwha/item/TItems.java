package party.lemons.taniwha.item;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.item.Item;
import party.lemons.taniwha.item.modifier.ItemModifier;

public class TItems {
    public static final Multimap<Item, ItemModifier> MODIFIERS = ArrayListMultimap.create();

    public static void runModifiers()
    {
        MODIFIERS.forEach((b, a)->a.accept(b));
    }
}
