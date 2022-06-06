package party.lemons.taniwha.block.instrument;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlockStateNoteInstrument extends NoteInstrument{
    private final BlockState state;

    public BlockStateNoteInstrument(BlockState state, SoundEvent sound) {
        super(sound);

        this.state = state;
    }

    @Override
    public boolean isValidInstrumentBlock(Level world, BlockPos pos, BlockState state) {
        return this.state.equals(state);
    }
}