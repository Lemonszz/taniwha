package party.lemons.taniwha.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.Taniwha;

public final class TBlockTags
{
    public static TagKey<Block> GOLEM_HEADS = TagKey.create(Registries.BLOCK, Taniwha.id("golem_heads"));

    public static void init()
    {
        //NOFU
    }
}
