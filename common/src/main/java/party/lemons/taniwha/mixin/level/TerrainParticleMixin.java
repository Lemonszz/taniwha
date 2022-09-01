package party.lemons.taniwha.mixin.level;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.util.TaniwhaTags;

@Mixin(TerrainParticle.class)
public abstract class TerrainParticleMixin extends TextureSheetParticle
{
	@Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDDLnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V")
	public void onConstruct(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, BlockState blockState, BlockPos blockPos, CallbackInfo cbi)
	{
		if(blockState.is(TaniwhaTags.BLOCK_PARTICLE_EXCEPTION))
		{
			this.rCol = 0.6F;
			this.gCol = 0.6F;
			this.bCol = 0.6F;
		}
	}

	private TerrainParticleMixin(ClientLevel clientLevel, double d, double e, double f)
	{
		super(clientLevel, d, e, f);
	}
}
