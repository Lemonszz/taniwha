package party.lemons.taniwha.hooks.block;

import com.google.common.collect.Maps;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.function.Supplier;

public class StrippableHooks {

    private static Map<Block, Supplier<Block>> STRIPPABLES = Maps.newHashMap();

    public static void addStrippable(Block block, Supplier<Block> stripped)
    {
        STRIPPABLES.put(block, stripped);
    }

    public static void insertTo(Map<Block, Block> map)
    {
        for(Map.Entry<Block, Supplier<Block>> strips : STRIPPABLES.entrySet())
        {
            map.put(strips.getKey(), strips.getValue().get());
        }
    }
}
