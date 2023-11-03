package party.lemons.taniwha.util;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class ItemUtil
{
	public static void giveOrDropItem(Player player, InteractionHand handItem, ItemStack itemStack)
	{
		if (player.getItemInHand(handItem).isEmpty()) {
			player.setItemInHand(handItem, itemStack);
		} else if (!player.addItem(itemStack)) {
			player.drop(itemStack, false);
		}
	}


	public static void consumeItem(Player player, ItemStack stack)
	{
		if (!player.getAbilities().instabuild) {
			stack.shrink(1);
		}
	}

	public static void hurtAndBreakOnBlock(@Nullable Player player, ItemStack stack, InteractionHand hand, BlockPos pos)
	{
		if (player instanceof ServerPlayer) {
			CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, pos, stack);
		}
		stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
	}

	/*
		Shrinks an itemstack if the player isn't in creative
	 */
	public static void shrinkStack(ItemStack stack, Player player)
	{
		if(!player.getAbilities().instabuild)
			stack.shrink(1);
	}

	/*
		Run a consumer on each enchantment on an itemstack
		allowEmpty to run on empty itemstacks
	 */
	public static void forEachEnchantment(Consumer consumer, ItemStack stack, boolean allowEmpty)
	{
		if(!stack.isEmpty() || allowEmpty)
		{
			ListTag listTag = stack.getEnchantmentTags();

			for(int i = 0; i < listTag.size(); ++i)
			{
				String string = listTag.getCompound(i).getString("id");
				int j = listTag.getCompound(i).getInt("lvl");
				BuiltInRegistries.ENCHANTMENT.getOptional(ResourceLocation.tryParse(string)).ifPresent((enchantment)->
				{
					consumer.accept(enchantment, stack, j);
				});
			}
		}
	}

	/*
		Drops a random drop from a given loot table
	 */
	public static void dropLootTable(Level level, double x, double y, double z, ResourceLocation table) {

		LootTable lootTable = level.getServer().getLootData().getLootTable(table);

		LootParams.Builder context = new LootParams.Builder((ServerLevel) level);
		lootTable.getRandomItems(context.create(LootContextParamSets.EMPTY), (i)->spawnItemStack(level, i, x, y, z));
	}

	/*
		Spawns an itemstack with random pop velocity
	 */
	public static void spawnItemStack(Level level, ItemStack itemStack, double x, double y, double z)
	{
		double itemWidth = EntityType.ITEM.getWidth();
		double variance = 1.0D - itemWidth;
		double halfWidth = itemWidth / 2.0D;
		double xx = Math.floor(x) + level.random.nextDouble() * variance + halfWidth;
		double yy = Math.floor(y) + level.random.nextDouble() * variance;
		double zz = Math.floor(z) + level.random.nextDouble() * variance + halfWidth;


		while(!itemStack.isEmpty()) {
			ItemEntity itemEntity = new ItemEntity(level, xx, yy, zz, itemStack.split(level.random.nextInt(21) + 10));
			itemEntity.setDeltaMovement(level.random.triangle(0.0D, 0.11485000171139836D), level.random.triangle(0.2D, 0.11485000171139836D), level.random.triangle(0.0D, 0.11485000171139836D));
			level.addFreshEntity(itemEntity);
		}
	}

	/*
		Assuming an item is in one of the entities hands, gets that hand
	 */
	public static InteractionHand getHandPossiblyHolding(LivingEntity entity, Predicate<ItemStack> predicate)
	{
		return predicate.test(entity.getMainHandItem()) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
	}

	/*
		Run a consumer on each enchantment on an itemstack
 	*/
	public static void forEachEnchantment(Consumer consumer, ItemStack stack)
	{
		forEachEnchantment(consumer, stack, false);
	}

	@FunctionalInterface
	public interface Consumer
	{
		void accept(Enchantment enchantment, ItemStack stack, int level);
	}
}