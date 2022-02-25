package party.lemons.taniwha.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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

    default Item makeItem(CreativeModeTab group)
    {
        return new BlockItem((Block) this, makeItemSettings(group));
    }

    default void registerItem(ResourceLocation id, CreativeModeTab group)
    {
        RegistryHelper.registerObject(Registry.ITEM, id, makeItem(group));
    }
}