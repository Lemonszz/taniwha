package party.lemons.taniwha.block.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.block.modifier.RTypeModifier;
import party.lemons.taniwha.registry.ModifierContainer;

public class TFlowerPotBlock extends FlowerPotBlock implements BlockWithModifiers<TFlowerPotBlock>
{
    private ModifierContainer<Block> modifierContainer;

    public TFlowerPotBlock(Block block, Properties properties) {
        super(block, properties);

        modifiers(RTypeModifier.CUTOUT);
    }

    @Override
    public TFlowerPotBlock modifiers(BlockModifier... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public @Nullable ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}
