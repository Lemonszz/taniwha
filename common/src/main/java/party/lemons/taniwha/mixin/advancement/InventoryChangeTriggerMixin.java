package party.lemons.taniwha.mixin.advancement;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.data.criterion.WearArmorCriterion;

@Mixin(InventoryChangeTrigger.class)
public class InventoryChangeTriggerMixin
{
    @Inject(at = @At("HEAD"), method = "trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/ItemStack;)V")
    public void trigger(ServerPlayer player, Inventory inventory, ItemStack stack, CallbackInfo cbi)
    {

        for(WearArmorCriterion criterion : WearArmorCriterion.CRITERION)
            criterion.trigger(player);
    }
}
