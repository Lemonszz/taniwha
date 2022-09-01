package party.lemons.taniwha.mixin.level;

import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.taniwha.util.LevelProvider;

@Mixin(RenderChunkRegion.class)
public class RenderChunkRegionMixin implements LevelProvider
{
	/*
	Used in TemperatureGradientColorProvider to get the level
	 */

	@Shadow
	@Final
	protected Level level;

	@Override
	public Level getLevel()
	{
		return level;
	}
}
