package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.FenceGateBlock;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;

public class TFenceGateBlock extends FenceGateBlock implements BlockWithItem, BlockWithModifiers<TFenceGateBlock>
{
    public TFenceGateBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public TFenceGateBlock modifiers(BlockModifier... modifiers) {
        BlockWithModifiers.init(this, modifiers);
        return this;
    }
}