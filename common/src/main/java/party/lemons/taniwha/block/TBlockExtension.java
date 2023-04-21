package party.lemons.taniwha.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public interface TBlockExtension
{
	default boolean isFarmlandMoist(BlockState state)
	{
		return false;
	}

	default BlockPathTypes getNodePathType()
	{
		return null;
	}

	default void onRegister()
	{

	}
}
