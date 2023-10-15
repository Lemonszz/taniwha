package party.lemons.taniwha.mixin.item.animation;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.item.animation.CustomUseAnimationItem;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
    @Shadow public abstract boolean isUsingItem();

    @Inject(at = @At("HEAD"), method = "triggerItemUseEffects", cancellable = true)
    protected void triggerItemUseEffects(ItemStack stack, int amount, CallbackInfo cbi)
    {
        if (stack.getItem() instanceof CustomUseAnimationItem animationItem && !stack.isEmpty() && this.isUsingItem())
        {
            animationItem.triggerItemUseEffects(((LivingEntity) (Object)this), stack, amount);
            cbi.cancel();
        }
    }
}
