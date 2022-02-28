package party.lemons.taniwha.mixin.block.noteblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.taniwha.block.instrument.NoteInstrument;

@Mixin(NoteBlock.class)
public class NoteBlockMixin {

    @Shadow @Final public static IntegerProperty NOTE;


    @Inject(at = @At("HEAD"), method = "triggerEvent", cancellable = true)
    public void triggerEvent(BlockState state, Level level, BlockPos pos, int type, int data, CallbackInfoReturnable<Boolean> cbi) {
        BlockState downState = level.getBlockState(pos.below());
        NoteInstrument noteInstrument = NoteInstrument.findInstrumentFor(level, pos, downState);
        if(noteInstrument != null)
        {
            noteInstrument.play(level, pos, state);
            noteInstrument.doParticle(level, pos, state);
            cbi.setReturnValue(true);
        }
    }
}
