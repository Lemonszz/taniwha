package party.lemons.taniwha.hooks.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

public interface BlockPropertiesHooks
{
	static BlockBehaviour.Properties withColor(BlockBehaviour.Properties properties, Function<BlockState, MapColor> colorFunction)
	{
		return ((BlockPropertiesHooks)properties).colorFunction(colorFunction);
	}

	BlockBehaviour.Properties colorFunction(Function<BlockState, MapColor> colorFunction);
}
