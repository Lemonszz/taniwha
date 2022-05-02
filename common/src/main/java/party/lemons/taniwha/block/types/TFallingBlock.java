package party.lemons.taniwha.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.block.modifier.BlockModifier;
import party.lemons.taniwha.block.modifier.BlockWithModifiers;
import party.lemons.taniwha.registry.BlockWithItem;
import party.lemons.taniwha.registry.ModifierContainer;

public class TFallingBlock extends FallingBlock implements BlockWithItem, BlockWithModifiers<TFallingBlock>
{
    private ModifierContainer<Block> modifierContainer;
    private final int dustColor;

    public TFallingBlock(int dustColor, Properties properties) {
        super(properties);

        this.dustColor = dustColor;
    }

    @Override
    public int getDustColor(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return dustColor;
    }

    @Override
    protected void falling(FallingBlockEntity fallingBlockEntity) {
        super.falling(fallingBlockEntity);

        fallingBlockEntity.dropItem = doFallingBlockDrop();
    }

    public boolean doFallingBlockDrop()
    {
        return true;
    }

    @Override
    public TFallingBlock modifiers(BlockModifier... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}
