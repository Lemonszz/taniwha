package party.lemons.taniwha.block.modifier;

import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.block.TBlocks;

import java.util.Arrays;

public interface BlockWithModifiers<T extends Block>
{
    static <A extends Block> void init(BlockWithModifiers<A> block, BlockModifier... modifiers)
    {
        block.registerModifiers((A)block, modifiers);
    }

    T modifiers(BlockModifier... modifiers);

    default void registerModifiers(T block, BlockModifier... modifiers)
    {
        TBlocks.MODIFIERS.putAll(block, Arrays.stream(modifiers).toList());
    }
}
