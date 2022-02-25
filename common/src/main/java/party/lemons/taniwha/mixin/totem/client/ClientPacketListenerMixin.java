package party.lemons.taniwha.mixin.totem.client;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.taniwha.item.totem.TotemItem;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Inject(at = @At("HEAD"), method = "findTotem", cancellable = true)
    private static void findTotem(Player player, CallbackInfoReturnable<ItemStack> cbi)
    {
        for (InteractionHand interactionHand : InteractionHand.values()) {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            if (itemStack.getItem() instanceof TotemItem)
                cbi.setReturnValue(itemStack);
        }
    }
}
