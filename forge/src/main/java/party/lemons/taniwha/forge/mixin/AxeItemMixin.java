package party.lemons.taniwha.forge.mixin;

import com.google.common.collect.Maps;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.item.AxeItem;
import party.lemons.taniwha.hooks.block.StrippableHooks;

import java.util.Map;

@Mixin(AxeItem.class)
public class AxeItemMixin
{
    /*
            This is essentially a copy of common/AxeItemMixin, but for the forge specific stuff
     */

    @Shadow
    @Mutable
    @Final
    protected static Map<Block, Block> STRIPPABLES;
    private static boolean t_stripInit = false;


    @Inject(at = @At("HEAD"), method = "getAxeStrippingState")
    private static  void getAxeStrippingState(BlockState originalState, CallbackInfoReturnable<BlockState> cbi)
    {
        if(!t_stripInit)
        {
            Map<Block, Block> newMap = Maps.newHashMap();
            newMap.putAll(STRIPPABLES);

            StrippableHooks.insertTo(newMap);

            STRIPPABLES = newMap;
        }
    }
}
