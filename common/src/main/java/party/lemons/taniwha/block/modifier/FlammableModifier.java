package party.lemons.taniwha.block.modifier;

import com.google.common.collect.Maps;
import net.minecraft.world.level.block.Block;
import party.lemons.taniwha.hooks.block.FireBlockHooks;

import java.util.Map;

public record FlammableModifier(int catchOdds, int burnOdds) implements BlockModifier
{
    public static final FlammableModifier WOOD = create(5, 20);
    public static final FlammableModifier LEAVES = create(30, 60);
    public static final FlammableModifier TALL_FLOWER = create(60, 100);
    public static final FlammableModifier IVY = create(60, 50);

    public static FlammableModifier create(int catchOdds, int burnOdds) {
        return new FlammableModifier(catchOdds, burnOdds);
    }

    @Override
    public void accept(Block block) {
        FireBlockHooks.setBlockFlammable(block, catchOdds, burnOdds);
    }
}