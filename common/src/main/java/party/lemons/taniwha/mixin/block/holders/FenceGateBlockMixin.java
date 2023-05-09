package party.lemons.taniwha.mixin.block.holders;

import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.taniwha.block.WoodTypeHolder;

@Mixin(FenceGateBlock.class)
public abstract class FenceGateBlockMixin implements WoodTypeHolder
{
	@Shadow @Final private WoodType type;

	@Override
	public WoodType getWoodType()
	{
		return type;
	}
}
