package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;
import party.lemons.taniwha.registry.ModifierContainer;

public class TSaplingBlock extends SaplingBlock implements BlockWithItem, BlockWithModifiers<TSaplingBlock> {
    private ModifierContainer<Block> modifierContainer;

    public TSaplingBlock(AbstractTreeGrower abstractTreeGrower, Properties settings) {
        super(abstractTreeGrower, settings);
    }

    @Override
    public TSaplingBlock modifiers(BlockModifier... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public @Nullable ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}