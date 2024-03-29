package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import party.lemons.taniwha.block.TBlockExtension;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TBlock extends Block implements BlockWithModifiers<TBlock>, TBlockExtension
{
    private ModifierContainer<Block> modifierContainer;

    public TBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public TBlock modifiers(BlockModifier... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}