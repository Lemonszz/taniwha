package party.lemons.taniwha.mixin.elytra;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.taniwha.item.types.TElytraItem;

@Mixin(FireworkRocketItem.class)
public class FireworkRocketItemMixin
{
	@Inject(at = @At("HEAD"), method = "use", cancellable = true)
	public void use(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cbi)
	{
		ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
		if(player.isFallFlying() && chestItem.getItem() instanceof TElytraItem el)
		{
			if(!el.isRocketBoostEnabled(chestItem))
				cbi.setReturnValue(InteractionResultHolder.pass(player.getItemInHand(interactionHand)));
		}
	}
}
