package party.lemons.taniwha.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;
import party.lemons.taniwha.registry.ModifierContainer;

import java.util.Random;
import java.util.function.Supplier;

public class TMushroomPlantBlock extends MushroomBlock implements BonemealableBlock, BlockWithItem, BlockWithModifiers<TMushroomPlantBlock> {

    private ModifierContainer<Block> modifierContainer;

    private final Supplier<Holder<? extends ConfiguredFeature<?, ?>>> giantShroomFeature;

    public TMushroomPlantBlock(Supplier<Holder<? extends ConfiguredFeature<?, ?>>> giantShroomFeature, Properties properties) {
        super(properties, giantShroomFeature);

        this.giantShroomFeature = giantShroomFeature;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, boolean bl) {
        return giantShroomFeature != null;
    }

    @Override
    public boolean isBonemealSuccess(Level level, Random random, BlockPos blockPos, BlockState blockState) {
        return (double)random.nextFloat() < 0.4;
    }

    @Override
    public boolean growMushroom(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, Random random) {
        if(giantShroomFeature == null)
            return false;

        serverLevel.removeBlock(blockPos, false);
        if (this.giantShroomFeature.get().value().place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, blockPos)) {
            return true;
        }
        serverLevel.setBlock(blockPos, blockState, 3);
        return false;
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
