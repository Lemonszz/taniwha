package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.HugeMushroomBlock;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;

public class TMushroomBlock extends HugeMushroomBlock implements BlockWithItem, BlockWithModifiers<TMushroomBlock>
{
    public TMushroomBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public TMushroomBlock modifiers(BlockModifier... modifiers) {
        BlockWithModifiers.init(this, modifiers);
        return this;
    }
}