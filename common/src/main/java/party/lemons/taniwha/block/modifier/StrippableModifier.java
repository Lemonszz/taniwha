package party.lemons.taniwha.block.modifier;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.hooks.block.StrippableHooks;

import java.util.function.Supplier;

public record StrippableModifier(Supplier<Block> strippedBlock) implements BlockModifier {

    @Override
    public void accept(Block block) {
        StrippableHooks.addStrippable(block, strippedBlock);
    }
}
