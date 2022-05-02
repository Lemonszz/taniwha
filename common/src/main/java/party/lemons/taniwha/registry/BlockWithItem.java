package party.lemons.taniwha.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public interface BlockWithItem
{
    default boolean hasItem()
    {
        return true;
    }
    default Item.Properties makeItemSettings(CreativeModeTab group)
    {
        return new Item.Properties().tab(group);
    }
    default Supplier<Item> makeItem(CreativeModeTab group)
    {
        return ()->new BlockItem((Block) this, makeItemSettings(group));
    }
}