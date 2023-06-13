package party.lemons.taniwha.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.Taniwha;

public class TaniwhaTags
{
	//Blocks in the ANTI_MELTABLES tag will stop snow/ice above it from melting
	public static final TagKey<Block> ANTI_MELTABLES = TagKey.create(Registries.BLOCK, Taniwha.id("anti_meltables"));

	//This stops particles from the blocks in this tag being tinted by the block's grass/foliage color
	public static final TagKey<Block> BLOCK_PARTICLE_EXCEPTION = TagKey.create(Registries.BLOCK, Taniwha.id("block_particle_exceptions"));

	//Entities in this tag can't be damaged by campfires
	public static final TagKey<EntityType<?>> CAMPFIRE_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, Taniwha.id("campfire_immune"));

	//Items in this tag, when equipped in the foot slot will allow the wearer to walk on powdered snow
	public static final TagKey<Item> POWDERED_SNOW_WALKER = TagKey.create(Registries.ITEM, Taniwha.id("powdered_snow_walker"));
	public static final TagKey<Item> T_ELYTRA = TagKey.create(Registries.ITEM, Taniwha.id("taniwha_elytra"));

	//Common Tags

	///Farmland blocks
	public static final TagKey<Block> FARMLAND = TagKey.create(Registries.BLOCK, Taniwha.commonId("farmland"));
}
