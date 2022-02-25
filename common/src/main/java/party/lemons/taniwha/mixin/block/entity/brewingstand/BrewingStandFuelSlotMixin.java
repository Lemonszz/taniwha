package party.lemons.taniwha.mixin.block.entity.brewingstand;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.taniwha.hooks.block.BrewingStandHooks;

@Mixin(targets = "net.minecraft.world.inventory/BrewingStandMenu$FuelSlot")
public class BrewingStandFuelSlotMixin {

    @Inject(at = @At("HEAD"), method = "mayPlaceItem", cancellable = true)
    private static void mayPlaceItem(ItemStack itemStack, CallbackInfoReturnable<Boolean> cbi) {
        if(BrewingStandHooks.getFuelForItem(itemStack) > 0)
            cbi.setReturnValue(true);
    }
}