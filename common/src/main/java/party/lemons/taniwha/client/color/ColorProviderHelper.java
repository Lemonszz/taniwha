package party.lemons.taniwha.client.color;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public final class ColorProviderHelper
{
    public static void registerSimpleBlockWithItem(BlockColor colorProvider, Supplier<Block>... blocks)
    {
        ColorHandlerRegistry.registerBlockColors(colorProvider, blocks);
        ColorHandlerRegistry.registerItemColors(new BlockItemColorProvider(), blocks);
    }

    private ColorProviderHelper()
    {
    }
}
