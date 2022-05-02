package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;
import party.lemons.taniwha.registry.ModifierContainer;

public class TTrapdoorBlock extends TrapDoorBlock implements BlockWithItem, BlockWithModifiers<TTrapdoorBlock>
{
    private ModifierContainer<Block> modifierContainer;

    public TTrapdoorBlock(Properties settings)
    {
        super(settings);
    }

    @Override
    public TTrapdoorBlock modifiers(BlockModifier... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public @Nullable ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}