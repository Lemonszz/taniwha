package party.lemons.taniwha.hooks.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import party.lemons.taniwha.mixin.block.FireBlockInvoker;

public class FireBlockHooks
{
    public static void setBlockFlammable(Block block, int catchOdds, int burnOdds)
    {
        ((FireBlockInvoker) Blocks.FIRE).callSetFlammable(block, catchOdds, burnOdds);
    }

}
