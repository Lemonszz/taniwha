package party.lemons.taniwha.fabric.mixin;

import dev.architectury.event.events.common.BlockEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.taniwha.hooks.TEvents;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin
{
	@Inject(method = "place",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/world/item/context/BlockPlaceContext;getClickedPos()Lnet/minecraft/core/BlockPos;"))
	private void place(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir) {
		TEvents.PLACE.invoker().placeBlock(context.getLevel(), context.getClickedPos(), context.getLevel().getBlockState(context.getClickedPos()), context.getPlayer());
	}
}
