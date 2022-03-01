package party.lemons.taniwha.client.color;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.util.MathUtils;

public class FoliageShiftBlockColorProvider extends FoliageBlockColorProvider
{
    protected final int rShift, gShift, bShift;

    public FoliageShiftBlockColorProvider(int rShift, int gShift, int bShift)
    {
        this.rShift = rShift;
        this.gShift = gShift;
        this.bShift = bShift;
    }

    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter world, @Nullable BlockPos pos, int tintIndex) {
        if(tintIndex == 0)
        {

                int color = super.getColor(state, world, pos, tintIndex);
                int[] boosts = getColorBoosts(world, state, pos, tintIndex);

                return MathUtils.colourBoost(color, boosts[0], boosts[1], boosts[2]);
        }
        return 0xFFFFFF;
    }


    protected int[] getColorBoosts(BlockAndTintGetter world, BlockState state, BlockPos pos, int tintIndex)
    {
        return new int[]{rShift, gShift, bShift};
    }
}