package party.lemons.taniwha.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.Taniwha;

public class TaniwhaTags
{
	public static final TagKey<Block> ANTI_MELTABLES = TagKey.create(Registries.BLOCK, Taniwha.id("anti_meltables"));
	public static final TagKey<Block> BLOCK_PARTICLE_EXCEPTION = TagKey.create(Registries.BLOCK, Taniwha.id("block_particle_exceptions"));


	public static final TagKey<EntityType<?>> CAMPFIRE_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, Taniwha.id("campfire_immune"));
}
