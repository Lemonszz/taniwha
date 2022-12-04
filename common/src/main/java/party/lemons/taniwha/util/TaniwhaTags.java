package party.lemons.taniwha.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.Taniwha;

public class TaniwhaTags
{
	public static final TagKey<Block> ANTI_MELTABLES = TagKey.create(Registries.BLOCK, Taniwha.id("anti_meltables"));
	public static final TagKey<Block> BLOCK_PARTICLE_EXCEPTION = TagKey.create(Registries.BLOCK, Taniwha.id("block_particle_exceptions"));

	public static final TagKey<EntityType<?>> CAMPFIRE_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, Taniwha.id("campfire_immune"));

	public static final TagKey<Item> POWDERED_SNOW_WALKER = TagKey.create(Registries.ITEM, Taniwha.id("powdered_snow_walker"));

	//Common Tags
	public static final TagKey<Block> FARMLAND = TagKey.create(Registries.BLOCK, Taniwha.commonId("farmland"));


}
