package party.lemons.taniwha.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockUtil
{
	public static BlockBehaviour.Properties copyProperties(BlockBehaviour.Properties toCopy)
	{
		BlockBehaviour behaviour = new BlockBehaviour(toCopy)
		{
			@Override
			public Item asItem()
			{
				return null;
			}

			@Override
			protected Block asBlock()
			{
				return null;
			}
		};
		return BlockBehaviour.Properties.copy(behaviour);
	}
}
