package party.lemons.taniwha.block.instrument;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockNoteInstrument extends NoteInstrument{
    private final Block block;

    public BlockNoteInstrument(Block block, SoundEvent sound) {
        super(sound);

        this.block = block;
    }

    @Override
    public boolean isValidInstrumentBlock(Level world, BlockPos pos, BlockState state) {
        return state.is(block);
    }
}