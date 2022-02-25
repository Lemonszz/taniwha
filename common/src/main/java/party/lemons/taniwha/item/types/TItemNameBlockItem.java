package party.lemons.taniwha.item.types;

import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.item.modifier.ItemModifier;
import party.lemons.taniwha.item.modifier.ItemWithModifiers;

public class TItemNameBlockItem extends ItemNameBlockItem implements ItemWithModifiers<TItemNameBlockItem> {
    public TItemNameBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public Block getBlock() {
        return super.getBlock();
    }

    @Override
    public TItemNameBlockItem modifiers(ItemModifier... modifiers) {
        ItemWithModifiers.init(this, modifiers);
        return this;
    }
}
