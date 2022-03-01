package party.lemons.taniwha.block.instrument;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TagNoteInstrument extends NoteInstrument{
    private final TagKey<Block> tag;

    public TagNoteInstrument(TagKey<Block> tag, SoundEvent sound) {
        super(sound);

        this.tag = tag;
    }

    @Override
    public boolean isValidInstrumentBlock(Level world, BlockPos pos, BlockState state) {
        return state.is(tag);
    }
}