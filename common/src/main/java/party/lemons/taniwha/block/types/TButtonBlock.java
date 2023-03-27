package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TButtonBlock extends ButtonBlock implements BlockWithModifiers<TButtonBlock>
{
    private ModifierContainer<Block> modifierContainer;

    public TButtonBlock(Properties properties, BlockSetType type, int ticks, boolean arrowsCanPress)
    {
        super(properties, type, ticks, arrowsCanPress);
    }

    @Override
    public TButtonBlock modifiers(BlockModifier... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public @Nullable ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}