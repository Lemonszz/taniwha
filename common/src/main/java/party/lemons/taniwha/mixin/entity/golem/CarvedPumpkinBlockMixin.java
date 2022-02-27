package party.lemons.taniwha.mixin.entity.golem;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.taniwha.entity.golem.GolemHandler;

import java.util.function.Predicate;

@Mixin(CarvedPumpkinBlock.class)
public class CarvedPumpkinBlockMixin
{
    @Inject(at = @At("RETURN"), method = "<init>")
    public void onConstruct(BlockBehaviour.Properties arg, CallbackInfo cbi)
    {
        PUMPKINS_PREDICATE = GolemHandler.STANDARD_HEADS;
    }

    /*
    @Inject(at = @At("HEAD"), method = "canSpawnGolem", cancellable = true)
    public void canSpawnGolem(LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cbi) {
        if(levelReader instanceof ServerLevel sl)
            if (GolemHandler.canDispenseGolem(sl, blockPos, new ItemStack(Blocks.CARVED_PUMPKIN)))
                    cbi.setReturnValue(true);
    }*/

    @Shadow
    @Final
    @Mutable
    private static Predicate<BlockState> PUMPKINS_PREDICATE;
}
