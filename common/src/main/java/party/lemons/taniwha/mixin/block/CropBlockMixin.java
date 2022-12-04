package party.lemons.taniwha.mixin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.taniwha.util.TaniwhaTags;

@Mixin(CropBlock.class)
public class CropBlockMixin
{
    @Inject(at = @At("HEAD"), method = "mayPlaceOn", cancellable = true)
    private void mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<Boolean> cbi)
    {
        if(blockState.is(TaniwhaTags.FARMLAND))
            cbi.setReturnValue(true);
    }


    @Redirect(at = @At(value = "INVOKE", target = "net/minecraft/world/level/block/state/BlockState.is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0), method = "getGrowthSpeed")
    private static boolean isFarmland(BlockState blockState, Block block)
    {
        return blockState.is(TaniwhaTags.FARMLAND);
    }

}
