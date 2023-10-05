package party.lemons.taniwha.forge.mixin;

import com.google.common.collect.Maps;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.*;
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
    @Unique
    private static boolean tf_stripInit = false;


    @Inject(at = @At("HEAD"), method = "getAxeStrippingState", remap = false)
    private static  void getAxeStrippingState(BlockState originalState, CallbackInfoReturnable<BlockState> cbi)
    {
        if(!tf_stripInit)
        {
            Map<Block, Block> newMap = Maps.newHashMap();
            newMap.putAll(STRIPPABLES);

            StrippableHooks.insertTo(newMap);

            STRIPPABLES = newMap;
            tf_stripInit = true;
        }
    }
}
