package party.lemons.taniwha.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;

public interface WoodTypeHolder
{
	WoodType getWoodType();

	static WoodType get(Block block)
	{
		if(block instanceof WoodTypeHolder holder)
			return holder.getWoodType();

		return null;
	}
}
