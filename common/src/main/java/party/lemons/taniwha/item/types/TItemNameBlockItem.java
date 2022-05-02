package party.lemons.taniwha.item.types;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.item.modifier.ItemModifier;
import party.lemons.taniwha.item.modifier.ItemWithModifiers;
import party.lemons.taniwha.registry.ModifierContainer;

public class TItemNameBlockItem extends ItemNameBlockItem implements ItemWithModifiers<TItemNameBlockItem> {
    private ModifierContainer<Item> modifierContainer;

    public TItemNameBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public Block getBlock() {
        return super.getBlock();
    }

    @Override
    public TItemNameBlockItem modifiers(ItemModifier... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public ModifierContainer<Item> getModifierContainer() {
        return modifierContainer;
    }
}
