package party.lemons.taniwha.block.modifier;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.block.TBlocks;

import java.util.Arrays;
import java.util.Collection;

public interface BlockWithModifiers<T extends Block>
{
    static <A extends Block> void init(BlockWithModifiers<A> block, BlockModifier... modifiers)
    {
        block.registerModifiers((A)block, modifiers);
    }

    static void doModifiers(RegistrySupplier<Block> bl)
    {
        bl.listen((b)->{
            if(b instanceof BlockWithModifiers<?>)
            {
                Collection<BlockModifier> mods = TBlocks.MODIFIERS.get(b);
                mods.forEach(m->m.accept(b));
            }
        });
    }

    T modifiers(BlockModifier... modifiers);

    default void registerModifiers(T block, BlockModifier... modifiers)
    {
        TBlocks.MODIFIERS.putAll(block, Arrays.stream(modifiers).toList());
    }
}
