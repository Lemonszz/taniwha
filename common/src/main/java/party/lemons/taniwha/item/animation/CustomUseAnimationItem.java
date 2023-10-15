package party.lemons.taniwha.item.animation;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/*
    Allows the use of custom use animations/effects on an item.
    Register animation renderers in CustomUseAnimationRegistry
 */
public interface CustomUseAnimationItem
{
    void triggerItemUseEffects(LivingEntity user, ItemStack stack, int amount);
}
