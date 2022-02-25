package party.lemons.taniwha.hooks.block.entity;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface BlockEntityHooks
{
    static void addAdditionalBlock(BlockEntityType<?> type, Block... blocks)
    {
        ((BlockEntityHooks)type).addAdditionalBlockTypes(blocks);
    }

    void addAdditionalBlockTypes(Block... blocks);
}
