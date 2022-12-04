package party.lemons.taniwha.mixin.block;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.taniwha.util.TaniwhaTags;

@Mixin(PowderSnowBlock.class)
public class PoweredSnowBlockMixin
{
    @Inject(at = @At("RETURN"), method = "canEntityWalkOnPowderSnow", cancellable = true)
    private static void canEntityWalkOnPowderSnow(Entity entity, CallbackInfoReturnable<Boolean> cbi) {
        if (entity instanceof LivingEntity living) {
            if(living.getItemBySlot(EquipmentSlot.FEET).is(TaniwhaTags.POWDERED_SNOW_WALKER))
                cbi.setReturnValue(true);
        }
    }
}
