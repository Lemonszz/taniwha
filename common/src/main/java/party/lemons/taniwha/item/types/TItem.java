package party.lemons.taniwha.item.types;

import net.minecraft.world.item.Item;
import party.lemons.taniwha.item.modifier.ItemModifier;
import party.lemons.taniwha.item.modifier.ItemWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TItem extends Item implements ItemWithModifiers<TItem>
{
    private ModifierContainer<Item> modifierContainer;

    public TItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public TItem modifiers(ItemModifier... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public ModifierContainer<Item> getModifierContainer() {
        return modifierContainer;
    }
}