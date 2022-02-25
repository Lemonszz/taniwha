package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.DoorBlock;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;

public class TDoorBlock extends DoorBlock implements BlockWithItem, BlockWithModifiers<TDoorBlock>
{
    public TDoorBlock(Properties settings)
    {
        super(settings);
    }

    @Override
    public TDoorBlock modifiers(BlockModifier... modifiers) {
        BlockWithModifiers.init(this, modifiers);

        return this;
    }
}