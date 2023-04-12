package party.lemons.taniwha.block;

import net.minecraft.world.level.block.state.BlockState;

public interface TBlockExtension
{
	default boolean isFarmlandMoist(BlockState state)
	{
		return false;
	}
}
