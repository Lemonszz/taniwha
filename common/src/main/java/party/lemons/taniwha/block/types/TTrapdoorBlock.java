package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.TrapDoorBlock;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;

public class TTrapdoorBlock extends TrapDoorBlock implements BlockWithItem, BlockWithModifiers<TTrapdoorBlock>
{
    public TTrapdoorBlock(Properties settings)
    {
        super(settings);
    }

    @Override
    public TTrapdoorBlock modifiers(BlockModifier... modifiers) {
        BlockWithModifiers.init(this, modifiers);

        return this;
    }
}