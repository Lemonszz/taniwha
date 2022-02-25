package party.lemons.taniwha.mixin.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FireBlock.class)
public interface FireBlockInvoker {
    @Invoker
    void callSetFlammable(Block block, int catchOdds, int burnOdds);
}