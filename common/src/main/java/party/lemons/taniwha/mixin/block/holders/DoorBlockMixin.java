package party.lemons.taniwha.mixin.block.holders;

import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.taniwha.block.BlockSetHolder;

@Mixin(DoorBlock.class)
public abstract class DoorBlockMixin implements BlockSetHolder
{
	@Shadow @Final private BlockSetType type;

	@Override
	public BlockSetType getBlockSetType()
	{
		return type;
	}
}
