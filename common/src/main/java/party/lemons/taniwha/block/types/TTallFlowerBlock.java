package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TTallFlowerBlock extends TallFlowerBlock implements BlockWithModifiers<TTallFlowerBlock>
{
    private ModifierContainer<Block> modifierContainer;

    public TTallFlowerBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public TTallFlowerBlock modifiers(BlockModifier... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public @Nullable ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}