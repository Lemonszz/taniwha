package party.lemons.taniwha.fabric.mixin.elytra;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import party.lemons.taniwha.item.types.TElytraItem;
import party.lemons.taniwha.item.types.TItem;

@Mixin(TElytraItem.class)
public class TElytraItemMixin extends TItem implements FabricElytraItem
{
    public TElytraItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void doVanillaElytraTick(LivingEntity entity, ItemStack chestStack) {
        ((TElytraItem)(Object)this).elytraTick(entity, chestStack);
    }
}
