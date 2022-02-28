package party.lemons.taniwha.block.instrument;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public abstract class NoteInstrument
{
    private static final List<NoteInstrument> INSTRUMENTS = Lists.newArrayList();

    public static void register(NoteInstrument instrument)
    {
        INSTRUMENTS.add(instrument);
    }

    public static void remove(NoteInstrument instrument)
    {
        INSTRUMENTS.remove(instrument);
    }

    public static List<NoteInstrument> getInstruments()
    {
        return INSTRUMENTS;
    }

    public static NoteInstrument findInstrumentFor(Level level, BlockPos pos, BlockState state)
    {
        for(NoteInstrument instrument : INSTRUMENTS)
        {
            if(instrument.isValidInstrumentBlock(level, pos, state))
                return instrument;
        }

        return null;
    }

    private SoundEvent sound;
    public NoteInstrument(SoundEvent sound)
    {
        this.sound = sound;
    }

    /***
     *
     * @param level the level
     * @param pos position of the NOTE BLOCK
     * @param state blockstate of the block UNDER the noteblock
     * @return if valid instrument block
     */
    public abstract boolean isValidInstrumentBlock(Level level, BlockPos pos, BlockState state);

    public float getPitch(Level world, BlockPos pos, BlockState state)
    {
        int i = state.getValue(NoteBlock.NOTE);
        return (float)Math.pow(2.0D, (double)(i - 12) / 12.0D);
    }

    public SoundEvent getSound()
    {
        return sound;
    }

    public void play(Level level, BlockPos pos, BlockState state)
    {
        level.playSound(null, pos, getSound(), SoundSource.RECORDS, 3.0F, getPitch(level, pos, state));
    }

    public void doParticle(Level level, BlockPos pos, BlockState state)
    {
        int i = state.getValue(NoteBlock.NOTE);
        level.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5D, (double)pos.getY() + 1.2D, (double)pos.getZ() + 0.5D, (double)i / 24.0D, 0.0D, 0.0D);
    }
}
