package party.lemons.taniwha.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Used to access the UseOnContext during bonemealing.
 * Implement the vanilla Bonemealable methods with default behaviour if no modelSet is found.
 */
public interface ContextBonemealableBlock extends BonemealableBlock
{
	//Called from BoneMealItemMixin
	static boolean growContextual(UseOnContext ctx, Level level, BlockPos pos)
	{
		BlockState blockState = level.getBlockState(pos);
		if (blockState.getBlock() instanceof ContextBonemealableBlock ctxBlock)
		{
			if (ctxBlock.isValidBonemealTarget(ctx, level, pos, blockState, level.isClientSide))
			{
				if (level instanceof ServerLevel)
				{
					if (ctxBlock.isBonemealSuccess(ctx, level, level.random, pos, blockState))
					{
						ctxBlock.performBonemeal(ctx, (ServerLevel)level, level.random, pos, blockState);
					}
					ctx.getItemInHand().shrink(1);
				}

				return true;
			}
		}

		return false;
	}

	boolean isValidBonemealTarget(UseOnContext useOnContext, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, boolean bl);

	boolean isBonemealSuccess(UseOnContext useOnContext, Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState);

	void performBonemeal(UseOnContext useOnContext, ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState);
}
