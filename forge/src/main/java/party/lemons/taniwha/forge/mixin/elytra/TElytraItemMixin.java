package party.lemons.taniwha.forge.mixin.elytra;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import party.lemons.taniwha.item.types.TElytraItem;
import party.lemons.taniwha.item.types.TItem;

@Mixin(TElytraItem.class)
public class TElytraItemMixin extends TItem
{
	public TElytraItemMixin(Properties properties)
	{
		super(properties);
	}

	@Override
	public boolean canElytraFly(ItemStack stack, LivingEntity entity)
	{
		return true;
	}

	@Override
	public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks)
	{
		((TElytraItem)(Object)this).elytraTick(entity, stack);
		return true;
	}
}
