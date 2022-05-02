package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;
import party.lemons.taniwha.registry.ModifierContainer;

public class TFenceGateBlock extends FenceGateBlock implements BlockWithItem, BlockWithModifiers<TFenceGateBlock>
{
    private ModifierContainer<Block> modifierContainer;

    public TFenceGateBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public TFenceGateBlock modifiers(BlockModifier... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public @Nullable ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}