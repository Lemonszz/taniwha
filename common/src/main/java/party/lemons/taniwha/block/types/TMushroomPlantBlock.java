package party.lemons.taniwha.block.types;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TMushroomPlantBlock extends MushroomBlock implements BonemealableBlock, BlockWithModifiers<TMushroomPlantBlock> {

    private ModifierContainer<Block> modifierContainer;

    public TMushroomPlantBlock(ResourceKey<ConfiguredFeature<?, ?>> feature, Properties properties) {
        super(properties, feature);
    }

    @Override
    public TMushroomPlantBlock modifiers(BlockModifier... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public @Nullable ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}
