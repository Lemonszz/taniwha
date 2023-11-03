package party.lemons.taniwha.util;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class LevelUtil
{

    public static void playBlockPlaceSound(Level level, Block block, BlockPos blockPos)
    {
        SoundType type = block.getSoundType(block.defaultBlockState());
        level.playSound(null, blockPos, type.getPlaceSound(), SoundSource.BLOCKS, (type.getVolume() + 1.0f) / 2.0f, type.getPitch() * 0.8f);
    }

    public static void playBlockBreakSound(Level level, Block block, BlockPos blockPos)
    {
        SoundType type = block.getSoundType(block.defaultBlockState());
        level.playSound(null, blockPos, type.getBreakSound(), SoundSource.BLOCKS, (type.getVolume() + 1.0f) / 2.0f, type.getPitch() * 0.8f);
    }
}
