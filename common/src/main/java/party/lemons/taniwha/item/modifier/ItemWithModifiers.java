package party.lemons.taniwha.item.modifier;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.registry.ModifierContainer;

public interface ItemWithModifiers<T extends Item>
{
    T modifiers(ItemModifier... modifiers);

    @Nullable
    ModifierContainer<Item> getModifierContainer();

    default void initModifiers()
    {
        if(hasModifiers())
            getModifierContainer().initModifiers();
    }

    default boolean hasModifiers()
    {
        return getModifierContainer() != null;
    }
}
