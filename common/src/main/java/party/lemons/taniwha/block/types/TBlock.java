package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;

public class TBlock extends Block implements BlockWithItem, BlockWithModifiers<TBlock>
{
    public TBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public TBlock modifiers(BlockModifier... modifiers) {
        BlockWithModifiers.init(this, modifiers);
        return this;
    }
}