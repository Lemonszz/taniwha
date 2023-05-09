package party.lemons.taniwha.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public interface BlockSetHolder
{
	BlockSetType getBlockSetType();

	static BlockSetType get(Block block)
	{
		if(block instanceof BlockSetHolder holder)
			return holder.getBlockSetType();

		return null;
	}
}
