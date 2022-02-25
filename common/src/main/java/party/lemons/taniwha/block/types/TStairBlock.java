package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;

public class TStairBlock extends StairBlock implements BlockWithItem, BlockWithModifiers<TStairBlock> {
    public TStairBlock(BlockState baseBlockState, Properties settings) {
        super(baseBlockState, settings);
    }

    @Override
    public TStairBlock modifiers(BlockModifier... modifiers) {
        BlockWithModifiers.init(this, modifiers);
        return this;
    }
}