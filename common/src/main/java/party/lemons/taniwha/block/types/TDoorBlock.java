package party.lemons.taniwha.block.types;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TDoorBlock extends DoorBlock implements BlockWithModifiers<TDoorBlock>
{
    private ModifierContainer<Block> modifierContainer;

    public TDoorBlock(Properties settings)
    {
        super(settings, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN);
    }

    public TDoorBlock(Properties settings, SoundEvent closeSound, SoundEvent openSound)
    {
        super(settings, closeSound, openSound);
    }

    @Override
    public TDoorBlock modifiers(BlockModifier... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public @Nullable ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}