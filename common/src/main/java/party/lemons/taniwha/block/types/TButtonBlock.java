package party.lemons.taniwha.block.types;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TButtonBlock extends ButtonBlock implements BlockWithModifiers<TButtonBlock>
{
    private ModifierContainer<Block> modifierContainer;

    public TButtonBlock(Properties properties, int ticks, boolean arrowsCanPress, SoundEvent offSound, SoundEvent onSound)
    {
        super(properties, ticks, arrowsCanPress, offSound, onSound);
    }

    public TButtonBlock(Properties properties, int ticks, boolean arrowsCanPress)
    {
        super(properties, ticks, arrowsCanPress, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON);
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