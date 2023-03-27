package party.lemons.taniwha.block.types;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TWoodenPressurePlateBlock extends PressurePlateBlock implements BlockWithModifiers<TWoodenPressurePlateBlock>
{
    private ModifierContainer<Block> modifierContainer;

    public TWoodenPressurePlateBlock(Sensitivity type, BlockSetType setType, Properties settings)
    {
        super(type, settings, setType);
    }

    @Override
    public TWoodenPressurePlateBlock modifiers(BlockModifier... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public @Nullable ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}