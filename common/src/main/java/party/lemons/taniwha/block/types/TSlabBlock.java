package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.SlabBlock;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;

public class TSlabBlock extends SlabBlock implements BlockWithItem, BlockWithModifiers<TSlabBlock> {
    public TSlabBlock(Properties settings) {
        super(settings);
    }

    @Override
    public TSlabBlock modifiers(BlockModifier... modifiers) {
        BlockWithModifiers.init(this, modifiers);
        return this;
    }
}
