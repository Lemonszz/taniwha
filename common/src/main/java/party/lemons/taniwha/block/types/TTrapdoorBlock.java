package party.lemons.taniwha.block.types;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TTrapdoorBlock extends TrapDoorBlock implements BlockWithModifiers<TTrapdoorBlock>
{
    private ModifierContainer<Block> modifierContainer;

    public TTrapdoorBlock(Properties settings, BlockSetType setType)
    {
        super(settings, setType);
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