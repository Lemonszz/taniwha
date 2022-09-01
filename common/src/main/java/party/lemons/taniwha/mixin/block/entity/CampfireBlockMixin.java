package party.lemons.taniwha.mixin.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.util.TaniwhaTags;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin
{
	@Inject(at = @At("HEAD"), method = "entityInside", cancellable = true)
	public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity, CallbackInfo cbi)
	{
		if(entity.getType().is(TaniwhaTags.CAMPFIRE_IMMUNE))
			cbi.cancel();
	}
}
