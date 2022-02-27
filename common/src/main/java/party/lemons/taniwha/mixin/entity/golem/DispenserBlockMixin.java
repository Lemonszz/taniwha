package party.lemons.taniwha.mixin.entity.golem;

import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.DispenserBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.taniwha.hooks.block.DispenserBlockHooks;

import java.util.Map;

@Mixin(DispenserBlock.class)
public class DispenserBlockMixin implements DispenserBlockHooks {

    @Override
    public void removeBehaviour(Item item) {
        DISPENSER_REGISTRY.remove(item);
    }

    @Override
    public boolean hasBehaviour(Item item) {
        return DISPENSER_REGISTRY.containsKey(item);
    }

    @Shadow @Final private static Map<Item, DispenseItemBehavior> DISPENSER_REGISTRY;
}
