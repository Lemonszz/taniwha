package party.lemons.taniwha.block.types;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TFenceGateBlock extends FenceGateBlock implements BlockWithModifiers<TFenceGateBlock>
{
    private ModifierContainer<Block> modifierContainer;

    public TFenceGateBlock(Properties properties)
    {
        super(properties, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN);
    }

    public TFenceGateBlock(Properties properties, SoundEvent closeSound, SoundEvent openSound)
    {
        super(properties, closeSound, openSound);
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