package party.lemons.taniwha.hooks.entity;

import com.google.common.collect.Lists;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import party.lemons.taniwha.mixin.spawn.SpawnPlacementsInvoker;

import java.util.List;
import java.util.function.Supplier;

public class TSpawnPlacement
{
	private static final List<PlacementInfo<? extends Mob>> placements = Lists.newArrayList();

	public static <T extends Mob> void register(Supplier<EntityType<T>> type, SpawnPlacements.Type placementType, Heightmap.Types heightType, SpawnPlacements.SpawnPredicate<T> predicate)
	{
		placements.add(new PlacementInfo<>(type, placementType, heightType, predicate));
	}

	public static void internal_Register()
	{
		placements.forEach(info -> SpawnPlacementsInvoker.callRegister((EntityType) info.type().get(), info.placementType(), info.heightType(), info.predicate()));
	}

	private record PlacementInfo<T extends Mob>(Supplier<EntityType<T>> type, SpawnPlacements.Type placementType, Heightmap.Types heightType, SpawnPlacements.SpawnPredicate<T> predicate)
	{

	}
}
