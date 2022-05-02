package party.lemons.taniwha.block.modifier;

import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.registry.ModifierContainer;

public interface BlockWithModifiers<T extends Block>
{
    T modifiers(BlockModifier... modifiers);

    @Nullable
    ModifierContainer<Block> getModifierContainer();

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
