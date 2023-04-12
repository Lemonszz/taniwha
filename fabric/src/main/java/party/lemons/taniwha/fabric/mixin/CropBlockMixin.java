package party.lemons.taniwha.fabric.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import party.lemons.taniwha.block.TBlockExtension;
import party.lemons.taniwha.util.TaniwhaTags;

@Mixin(CropBlock.class)
public class CropBlockMixin
{
	@WrapOperation(method = "getGrowthSpeed", at = @At(value = "INVOKE", target = "net/minecraft/world/level/block/state/BlockState.is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0))
	private static boolean isFarmlandWithMoistyness(BlockState blockState, Block block, Operation<Boolean> original)
	{
		return original.call(blockState, block) || ((block instanceof TBlockExtension tblock && tblock.isFarmlandMoist(blockState)));
	}
}
