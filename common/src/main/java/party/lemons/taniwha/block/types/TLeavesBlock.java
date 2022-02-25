package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.LeavesBlock;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;

public class TLeavesBlock extends LeavesBlock implements BlockWithItem, BlockWithModifiers<TLeavesBlock>
{
    public TLeavesBlock(Properties settings)
    {
        super(settings);
    }

    @Override
    public TLeavesBlock modifiers(BlockModifier... modifiers) {
        BlockWithModifiers.init(this, modifiers);

        return this;
    }
}