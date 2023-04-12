package party.lemons.taniwha.forge.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import org.spongepowered.asm.mixin.Mixin;
import party.lemons.taniwha.block.TBlockExtension;
import party.lemons.taniwha.block.types.TFarmBlock;

@Mixin(TFarmBlock.class)
public abstract class TFarmBlockMixin extends FarmBlock implements TBlockExtension
{
	@Override
	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable)
	{
		return true;
	}

	@Override
	public boolean isFertile(BlockState state, BlockGetter level, BlockPos pos)
	{
		return isFarmlandMoist(state);
	}

	public TFarmBlockMixin(Properties arg)
	{
		super(arg);
	}
}
