package party.lemons.taniwha.block.types;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;
import party.lemons.taniwha.registry.ModifierContainer;

public class TFlowerBlock extends FlowerBlock implements BlockWithItem, BlockWithModifiers<TFlowerBlock> {
    private ModifierContainer<Block> modifierContainer;

    public TFlowerBlock(MobEffect mobEffect, int i, Properties properties) {
        super(mobEffect, i, properties);
    }

    @Override
    public TFlowerBlock modifiers(BlockModifier... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}
