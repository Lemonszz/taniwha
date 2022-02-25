package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.TallFlowerBlock;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;

public class TTallFlowerBlock extends TallFlowerBlock implements BlockWithItem, BlockWithModifiers<TTallFlowerBlock>
{
    public TTallFlowerBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public TTallFlowerBlock modifiers(BlockModifier... modifiers) {
        BlockWithModifiers.init(this, modifiers);

        return this;
    }
}