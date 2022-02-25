package party.lemons.taniwha.block.modifier;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.hooks.item.tool.HoeItemHooks;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class StrippableModifier implements BlockModifier{

    private final Supplier<Block> strippedBlock;

    public StrippableModifier(Supplier<Block> strippedBlock)
    {
        this.strippedBlock = strippedBlock;
    }

    @Override
    public void accept(Block block)
    {
        AxeItemHooks.addStrippable(block, strippedBlock.get());
    }
}
