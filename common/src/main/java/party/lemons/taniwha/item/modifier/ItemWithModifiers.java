package party.lemons.taniwha.item.modifier;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.block.TBlocks;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.item.TItems;

import java.util.Arrays;
import java.util.Collection;

public interface ItemWithModifiers<T extends Item>
{
    static <A extends Item> void init(ItemWithModifiers<A> item, ItemModifier... modifiers)
    {
        item.registerModifiers((A)item, modifiers);
    }

    static void doModifiers(RegistrySupplier<Item> it)
    {
        it.listen((b)->{
            if(b instanceof ItemWithModifiers<?>)
            {
                Collection<ItemModifier> mods = TItems.MODIFIERS.get(b);
                mods.forEach(m->m.accept(b));
            }
        });
    }


    T modifiers(ItemModifier... modifiers);

    default void registerModifiers(T item, ItemModifier... modifiers)
    {
        TItems.MODIFIERS.putAll(item, Arrays.stream(modifiers).toList());
    }
}
