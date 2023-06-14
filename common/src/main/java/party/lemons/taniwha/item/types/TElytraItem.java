package party.lemons.taniwha.item.types;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.item.TElytra;

public class TElytraItem extends TItem implements TElytra, Equipable
{
	@Nullable private final TagKey<Item> repairTag;

	public TElytraItem(Properties properties, @Nullable TagKey<Item> repairTag)
	{
		super(properties);
		this.repairTag = repairTag;
		DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
	}
	@Override
	public boolean isValidRepairItem(ItemStack arg, ItemStack arg2) {
	return repairTag != null && arg2.is(repairTag);
}

	@Override
	public InteractionResultHolder<ItemStack> use(Level arg, Player arg2, InteractionHand arg3) {
		return this.swapWithEquipmentSlot(this, arg, arg2, arg3);
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

	@Override
	public @NotNull SoundEvent getEquipSound() {
		return SoundEvents.ARMOR_EQUIP_ELYTRA;
	}

	public boolean isRocketBoostEnabled(ItemStack stack)
	{
		return true;
	}

	@Override
	public @NotNull EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.CHEST;
	}
}
