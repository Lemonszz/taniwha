package party.lemons.taniwha.client.color;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GrassBlockColorProvider implements BlockColor
{
    @Override
    public int getColor(BlockState blockState, @Nullable BlockAndTintGetter world, @Nullable BlockPos blockPos, int tintIndex) {
        if(tintIndex == 0)
            return world != null && blockPos != null ? BiomeColors.getAverageGrassColor(world, blockPos) : GrassColor.getDefaultColor();

        return 0xFFFFFF;
    }
}