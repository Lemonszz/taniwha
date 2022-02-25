package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;

public class TSaplingBlock extends SaplingBlock implements BlockWithItem, BlockWithModifiers<TSaplingBlock> {
    public TSaplingBlock(AbstractTreeGrower abstractTreeGrower, Properties settings) {
        super(abstractTreeGrower, settings);
    }

    @Override
    public TSaplingBlock modifiers(BlockModifier... modifiers) {
        BlockWithModifiers.init(this, modifiers);

        return this;
    }
}