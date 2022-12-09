package party.lemons.taniwha.fabric.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import party.lemons.taniwha.util.TaniwhaTags;

@Mixin(CropBlock.class)
public class CropBlockMixin
{
	@Redirect(at = @At(value = "INVOKE", target = "net/minecraft/world/level/block/state/BlockState.is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0), method = "getGrowthSpeed")
	private static boolean isFarmland(BlockState blockState, Block block)
	{
		return blockState.is(TaniwhaTags.FARMLAND);
	}
}
