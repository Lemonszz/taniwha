package party.lemons.taniwha.mixin.block.holders;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.taniwha.block.BlockSetHolder;

@Mixin(ButtonBlock.class)
public abstract class ButtonBlockMixin implements BlockSetHolder
{
	@Shadow @Final private BlockSetType type;

	@Override
	public BlockSetType getBlockSetType()
	{
		return type;
	}
}
