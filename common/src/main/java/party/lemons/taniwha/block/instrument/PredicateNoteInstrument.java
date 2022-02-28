package party.lemons.taniwha.block.instrument;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public class PredicateNoteInstrument extends NoteInstrument{

    private final Predicate<BlockState> predicate;

    public PredicateNoteInstrument(Predicate<BlockState> predicate, SoundEvent sound) {
        super(sound);

        this.predicate = predicate;
    }

    @Override
    public boolean isValidInstrumentBlock(Level lev, BlockPos pos, BlockState state) {
        return predicate.test(state);
    }
}