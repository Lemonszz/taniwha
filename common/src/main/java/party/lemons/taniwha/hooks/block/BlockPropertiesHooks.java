package party.lemons.taniwha.hooks.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Function;

public interface BlockPropertiesHooks
{
	static BlockBehaviour.Properties withColor(BlockBehaviour.Properties properties, Function<BlockState, MaterialColor> colorFunction)
	{
		return ((BlockPropertiesHooks)properties).colorFunction(colorFunction);
	}

	BlockBehaviour.Properties colorFunction(Function<BlockState, MaterialColor> colorFunction);
}
