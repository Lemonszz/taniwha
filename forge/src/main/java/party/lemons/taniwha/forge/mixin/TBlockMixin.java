package party.lemons.taniwha.forge.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.taniwha.block.types.TBlock;

@Mixin(TBlock.class)
public abstract class TBlockMixin extends Block
{
	@Shadow
	public BlockPathTypes getNodePathType(){
		throw new AssertionError();
	}

	@Override
	public @Nullable BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob)
	{
		return getNodePathType();
	}

	private TBlockMixin(Properties arg)
	{
		super(arg);
	}
}
