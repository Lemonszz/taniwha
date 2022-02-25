package party.lemons.taniwha.mixin.block.entity.brewingstand;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.taniwha.hooks.block.BrewingStandHooks;

@Mixin(BrewingStandBlockEntity.class)
public abstract class BrewingStandBlockEntityMixin extends BlockEntity implements BrewingStandHooks.BrewingStandAccess
{
    @Shadow private int fuel;

    @Override
    public int getFuel() {
        return fuel;
    }

    @Override
    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    @Inject(at = @At("HEAD"), method = "serverTick")
    private static void serverTick(Level level, BlockPos blockPos, BlockState blockState, BrewingStandBlockEntity brew, CallbackInfo cbi) {
        ItemStack fuelStack = brew.getItem(4);

        if(BrewingStandHooks.getBrewingFuel(brew) <= 0)
        {
            int itemFuelAmount = BrewingStandHooks.getFuelForItem(fuelStack);
            if(itemFuelAmount > 0) {
                BrewingStandHooks.setBrewingFuel(brew, itemFuelAmount);
                fuelStack.shrink(1);
                BrewingStandBlockEntity.setChanged(level, blockPos, blockState);
            }
        }
    }

    private BrewingStandBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
}
