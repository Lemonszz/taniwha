package party.lemons.taniwha.item.modifier;

import net.minecraft.world.item.Item;
import party.lemons.taniwha.item.TItems;

import java.util.Arrays;

public interface ItemWithModifiers<T extends Item>
{
    static <A extends Item> void init(ItemWithModifiers<A> item, ItemModifier... modifiers)
    {
        item.registerModifiers((A)item, modifiers);
    }

    T modifiers(ItemModifier... modifiers);

    default void registerModifiers(T item, ItemModifier... modifiers)
    {
        TItems.MODIFIERS.putAll(item, Arrays.stream(modifiers).toList());
    }
}
