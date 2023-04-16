package party.lemons.taniwha.mixin.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import party.lemons.taniwha.block.DripstoneReceiver;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Debug(export = true)
@Mixin(PointedDripstoneBlock.class)
public abstract class PointedDripstoneBlockMixin
{

	@WrapOperation(method = "maybeTransferFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/PointedDripstoneBlock;findFillableCauldronBelowStalactiteTip(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/material/Fluid;)Lnet/minecraft/core/BlockPos;"))
	private static BlockPos findFillableCauldronBelowStalactiteTip(Level level, BlockPos pos, Fluid fluid, Operation<BlockPos> original)
	{
		BlockPos originalPos = original.call(level, pos, fluid);
		if(originalPos != null)
			return originalPos;

		Predicate<BlockState> predicate = state -> state.getBlock() instanceof DripstoneReceiver receiver && receiver.canReceiveDrip(fluid, level, state);

		BiPredicate<BlockPos, BlockState> biPredicate = (blockPosx, blockState) -> canDripThrough(level, blockPosx, blockState);
		return findBlockVertical(level, pos, Direction.DOWN.getAxisDirection(), biPredicate, predicate, 11).orElse(null);
	}

	@Inject(method = "maybeTransferFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;scheduleTick(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;I)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private static void beforeTickBlock(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, float f, CallbackInfo cbi, Optional optional, Fluid fluid, float g, BlockPos blockPos2, BlockPos blockPos3, int i, int j, BlockState dripIntoState)
	{
		if(dripIntoState.getBlock() instanceof DripstoneReceiver receiver && !receiver.useDripstoneTickMethod())
		{
			receiver.receiveStalactiteDrip(dripIntoState, serverLevel, blockPos3, fluid);
			cbi.cancel();
		}
	}

	@Shadow private static boolean canDripThrough(BlockGetter arg, BlockPos arg2, BlockState arg3){throw new AssertionError(); };

	@Shadow private static Optional<BlockPos> findBlockVertical(LevelAccessor arg, BlockPos arg2, Direction.AxisDirection arg3, BiPredicate<BlockPos, BlockState> biPredicate, Predicate<BlockState> predicate, int i){ throw new AssertionError(); }
}
