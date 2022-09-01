package party.lemons.taniwha.block.modifier;

import dev.architectury.hooks.item.tool.HoeItemHooks;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public record HoeModifier(Predicate<UseOnContext> predicate, Consumer<UseOnContext> action, Function<UseOnContext, BlockState> newState) implements BlockModifier
{
    @Override
    public void accept(Block block)
    {
        HoeItemHooks.addTillable(block, predicate, action, newState);
    }

    public static Consumer<UseOnContext> changeIntoStateAndDropItem(Supplier<BlockState> blockState, Supplier<ItemLike> itemLike)
    {
        return useOnContext -> {
            useOnContext.getLevel().setBlock(useOnContext.getClickedPos(), blockState.get(), 11);
            Block.popResourceFromFace(useOnContext.getLevel(), useOnContext.getClickedPos(), useOnContext.getClickedFace(), new ItemStack(itemLike.get()));
        };
    }

    public static Consumer<UseOnContext> changeIntoState(Supplier<Supplier<BlockState>> blockState)
    {
        return useOnContext -> useOnContext.getLevel().setBlock(useOnContext.getClickedPos(), blockState.get().get(), 11);
    }
}