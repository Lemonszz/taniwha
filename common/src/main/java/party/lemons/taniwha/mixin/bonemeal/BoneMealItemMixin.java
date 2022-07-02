package party.lemons.taniwha.mixin.bonemeal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.taniwha.block.ContextBonemealableBlock;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin
{
	@Inject(at = @At("HEAD"), method = "useOn", cancellable = true)
	public void useOn(UseOnContext ctx, CallbackInfoReturnable<InteractionResult> cbi)
	{
		Level level = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();

		if(ContextBonemealableBlock.growContextual(ctx, level, pos))
		{
			if (!level.isClientSide) {
				level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
			}

			cbi.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
		}
	}
}
