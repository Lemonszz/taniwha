package party.lemons.taniwha.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import party.lemons.taniwha.item.TLootContexts;

public class EntityUtil
{
	/*
		Drops an item around an entity
	 */
	public static void scatterItemStack(Entity e, ItemStack stack)
	{
		Containers.dropItemStack(e.level(), e.getX(), e.getY(), e.getZ(), stack);
	}

	public static void dropFromLootTable(LivingEntity living, ResourceLocation table, LootParams.Builder params) {
		LootTable lootTable = living.level().getServer().getLootData().getLootTable(table);
		lootTable.getRandomItems(params.create(TLootContexts.GENERIC_ENTITY_LOOT_CONTEXT), living::spawnAtLocation);
	}

	public static void dropFromLootTable(LivingEntity living, ResourceLocation table)
	{
		LootParams.Builder params = new LootParams.Builder((ServerLevel)living.level())
				.withParameter(LootContextParams.THIS_ENTITY, living)
				.withParameter(LootContextParams.ORIGIN, living.position());

		if (living.getLastAttacker() != null && living.getLastAttacker() instanceof Player attackingPlayer) {
			params = params.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, attackingPlayer).withLuck(attackingPlayer.getLuck());
		}

		dropFromLootTable(living, table, params);
	}
}
