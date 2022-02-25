package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.FenceBlock;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;

public class TFenceBlock extends FenceBlock implements BlockWithItem, BlockWithModifiers<TFenceBlock> {
    public TFenceBlock(Properties settings) {
        super(settings);
    }

    @Override
    public TFenceBlock modifiers(BlockModifier... modifiers) {
        BlockWithModifiers.init(this, modifiers);
        return this;
    }
}