package party.lemons.taniwha.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LayerLightEngine;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.Supplier;

public class TSpreadableBlock extends TBlock
{
    private final Supplier<Block> dirtBlock;

    public TSpreadableBlock(Properties properties, Supplier<Block> dirtBlock)
    {
        super(properties);
        this.dirtBlock = dirtBlock;
    }

    private static boolean canBeGrass(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos abovePos = blockPos.above();
        BlockState aboveState = levelReader.getBlockState(abovePos);

        int i = LayerLightEngine.getLightBlockInto(levelReader, blockState, blockPos, aboveState, abovePos, Direction.UP, aboveState.getLightBlock(levelReader, abovePos));
        return i < levelReader.getMaxLightLevel();
    }

    private static boolean canPropagate(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return canBeGrass(blockState, levelReader, blockPos);
    }

    @Override
    public void randomTick(@NotNull BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random)
    {
        if (!canBeGrass(blockState, serverLevel, blockPos))
        {
            serverLevel.setBlockAndUpdate(blockPos, dirtBlock.get().defaultBlockState());
            return;
        }
        if (serverLevel.getMaxLocalRawBrightness(blockPos.above()) >= 9)
        {
            BlockState blockState2 = this.defaultBlockState();
            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos2 = blockPos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                if (!serverLevel.getBlockState(blockPos2).is(dirtBlock.get()) || !canPropagate(blockState2, serverLevel, blockPos2)) continue;
                serverLevel.setBlockAndUpdate(blockPos2, blockState2);
            }
        }
    }
}
