package party.lemons.taniwha.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public interface DripstoneReceiver
{
	boolean canReceiveDrip(Fluid fluid, Level level, BlockState state);
	void receiveStalactiteDrip(BlockState blockState, Level level, BlockPos blockPos, Fluid fluid);
	default boolean useDripstoneTickMethod(){
		return false;
	}
}
