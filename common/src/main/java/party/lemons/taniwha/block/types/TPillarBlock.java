package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.RotatedPillarBlock;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;

public class TPillarBlock extends RotatedPillarBlock implements BlockWithItem, BlockWithModifiers<TPillarBlock>
{
    public TPillarBlock(Properties settings)
    {
        super(settings);
    }

    @Override
    public TPillarBlock modifiers(BlockModifier... modifiers) {
        BlockWithModifiers.init(this, modifiers);
        return this;
    }
}