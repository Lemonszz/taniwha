package party.lemons.taniwha.item.types;

import net.minecraft.world.item.Item;
import party.lemons.taniwha.item.modifier.ItemModifier;
import party.lemons.taniwha.item.modifier.ItemWithModifiers;

public class TItem extends Item implements ItemWithModifiers<TItem>
{
    public TItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public TItem modifiers(ItemModifier... modifiers) {
        ItemWithModifiers.init(this, modifiers);
        return this;
    }
}