package party.lemons.taniwha.mixin.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.taniwha.hooks.block.BlockPropertiesHooks;

import java.util.function.Function;

@Mixin(BlockBehaviour.Properties.class)
public class BlockPropertiesMixin implements BlockPropertiesHooks
{
	@Shadow Function<BlockState, MapColor> mapColor;

	@Override
	public BlockBehaviour.Properties colorFunction(Function<BlockState, MapColor> colorFunction)
	{
		mapColor = colorFunction;
		return ((BlockBehaviour.Properties) (Object)this);
	}
}
