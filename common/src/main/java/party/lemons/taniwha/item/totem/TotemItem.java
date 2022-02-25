package party.lemons.taniwha.item.totem;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface TotemItem
{
    boolean canActivate(LivingEntity entity);
    void activateTotem(LivingEntity entity, ItemStack stack);
}