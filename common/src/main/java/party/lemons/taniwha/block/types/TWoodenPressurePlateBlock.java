package party.lemons.taniwha.block.types;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TWoodenPressurePlateBlock extends PressurePlateBlock implements BlockWithModifiers<TWoodenPressurePlateBlock>
{
    private ModifierContainer<Block> modifierContainer;

    public TWoodenPressurePlateBlock(Sensitivity type, Properties settings)
    {
        super(type, settings, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON);
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