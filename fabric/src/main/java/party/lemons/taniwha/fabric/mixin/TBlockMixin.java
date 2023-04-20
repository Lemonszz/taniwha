package party.lemons.taniwha.fabric.mixin;

import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.block.types.TBlock;

@Mixin(TBlock.class)
public class TBlockMixin
{
	@Shadow
	public BlockPathTypes getNodePathType(){
		throw new AssertionError();
	}

	@Inject(at = @At("HEAD"), method = "onRegister", remap = false)
	private void onRegister(CallbackInfo cbi)
	{
		BlockPathTypes nodeType = getNodePathType();
		if(nodeType != null)
			LandPathNodeTypesRegistry.register((Block)(Object)this, (state, neighbor) -> nodeType);
	}
}
