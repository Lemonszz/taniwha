package party.lemons.taniwha.client.color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import party.lemons.taniwha.util.LevelProvider;

public class TemperatureGradientColorProvider implements BlockColor
{
	private final int[] colors;

	public TemperatureGradientColorProvider(int... colors)
	{
		this.colors = colors;
	}

	@Override
	public int getColor(BlockState blockState, @Nullable BlockAndTintGetter tintGetter, @Nullable BlockPos blockPos, int index) {

		if (tintGetter instanceof LevelProvider provider && blockPos != null) {
			return calculateBlockTint(blockPos, provider.getLevel());
		}

		return colors[colors.length - 1];
	}

	public int getColourForTemp(float temp) {
		temp = Math.max(-1, temp);
		temp = Math.min(2, temp);
		temp = (temp - -1) / (2 - -1);

		float scaledTemp = (int) (temp * (colors.length - 1));
		int c1 = colors[(int) scaledTemp];
		int c2 = colors[Math.min((int) scaledTemp + 1, colors.length - 1)];
		float t = scaledTemp - Math.round(scaledTemp);

		return (int) Mth.lerp(t, c1, c2);
	}

	public int calculateBlockTint(BlockPos pos, Level level) {
		int blendRadius = Minecraft.getInstance().options.biomeBlendRadius().get();
		if (blendRadius == 0) {
			return getColourForTemp(level.getBiome(pos).value().getBaseTemperature());
		} else {
			int j = (blendRadius * 2 + 1) * (blendRadius * 2 + 1);
			int r = 0;
			int g = 0;
			int b = 0;
			Cursor3D cursor3d = new Cursor3D(pos.getX() - blendRadius, pos.getY(), pos.getZ() - blendRadius, pos.getX() + blendRadius, pos.getY(), pos.getZ() + blendRadius);

			int j1;
			for (BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos(); cursor3d.advance(); b += j1 & 255) {
				checkPos.set(cursor3d.nextX(), cursor3d.nextY(), cursor3d.nextZ());
				j1 = getColourForTemp(level.getBiome(checkPos).value().getBaseTemperature());
				r += (j1 & 16711680) >> 16;
				g += (j1 & '\uff00') >> 8;
			}

			return (r / j & 255) << 16 | (g / j & 255) << 8 | b / j & 255;
		}
	}
}