package party.lemons.taniwha.block;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.Taniwha;

public final class TBlockTags
{
    public static TagKey<Block> GOLEM_HEADS = TagKey.create(Registry.BLOCK_REGISTRY, Taniwha.id("golem_heads"));

    public static void init()
    {
        //NOFU
    }
}
