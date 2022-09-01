package party.lemons.taniwha.mixin.block.antimelt;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.util.TaniwhaTags;

@Mixin(IceBlock.class)
public class IceBlockMixin {
    @Inject(at = @At("HEAD"), method = "randomTick", cancellable = true)
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random, CallbackInfo cbi)
    {
        if(serverLevel.getBlockState(blockPos.below()).is(TaniwhaTags.ANTI_MELTABLES))
            cbi.cancel();
    }
}
