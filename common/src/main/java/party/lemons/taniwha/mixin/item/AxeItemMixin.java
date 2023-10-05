package party.lemons.taniwha.mixin.item;

import com.google.common.collect.Maps;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.taniwha.hooks.block.StrippableHooks;

import java.util.Map;
import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin
{
    @Shadow @Mutable @Final
    protected static Map<Block, Block> STRIPPABLES;
    private boolean t_stripInit = false;

    @Inject(at = @At("HEAD"), method = "getStripped")
    private void getStripped(BlockState blockState, CallbackInfoReturnable<Optional<BlockState>> cbi)
    {
        if(!t_stripInit)
        {
            Map<Block, Block> newMap = Maps.newHashMap();
            newMap.putAll(STRIPPABLES);

            StrippableHooks.insertTo(newMap);

            STRIPPABLES = newMap;
            t_stripInit = true;
        }
    }
}
