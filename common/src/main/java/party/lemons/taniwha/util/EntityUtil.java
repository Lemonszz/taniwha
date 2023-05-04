package party.lemons.taniwha.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import party.lemons.taniwha.item.TLootContexts;

public class EntityUtil
{
	public static void scatterItemStack(Entity e, ItemStack stack)
	{
		Containers.dropItemStack(e.level, e.getX(), e.getY(), e.getZ(), stack);
	}

	public static void dropFromLootTable(LivingEntity living, ResourceLocation table, LootContext.Builder context) {
		LootTable lootTable = living.getLevel().getServer().getLootTables().get(table);
		lootTable.getRandomItems(context.create(TLootContexts.GENERIC_ENTITY_LOOT_CONTEXT), living::spawnAtLocation);
	}

	public static void dropFromLootTable(LivingEntity living, ResourceLocation table)
	{
		LootContext.Builder context = new LootContext.Builder((ServerLevel)living.level)
				.withRandom(living.getRandom())
				.withParameter(LootContextParams.THIS_ENTITY, living)
				.withParameter(LootContextParams.ORIGIN, living.position());

		if (living.getLastAttacker() != null && living.getLastAttacker() instanceof Player attackingPlayer) {
			context = context.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, attackingPlayer).withLuck(attackingPlayer.getLuck());
		}

		dropFromLootTable(living, table, context);
	}
}
