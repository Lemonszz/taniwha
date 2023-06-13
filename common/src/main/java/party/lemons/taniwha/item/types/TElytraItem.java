package party.lemons.taniwha.item.types;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import party.lemons.taniwha.item.TElytra;

public class TElytraItem extends TItem implements TElytra, Equipable
{
	public TElytraItem(Properties properties)
	{
		super(properties);
		DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
	}

	@Override
	public void elytraTick(LivingEntity entity, ItemStack chestStack)
	{
		int nextRoll = entity.getFallFlyingTicks() + 1;

		if (!entity.level().isClientSide && nextRoll % 10 == 0) {
			if ((nextRoll / 10) % 2 == 0) {
				chestStack.hurtAndBreak(1, entity, p -> p.broadcastBreakEvent(EquipmentSlot.CHEST));
			}

			entity.gameEvent(GameEvent.ELYTRA_GLIDE);
		}
	}

	public boolean isRocketBoostEnabled(ItemStack stack)
	{
		return true;
	}

	@Override
	public EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.CHEST;
	}
}
