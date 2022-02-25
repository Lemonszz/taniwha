package party.lemons.taniwha.mixin.sign;

import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WoodType.class)
public class WoodTypeInvoker {

    @Invoker
    public static WoodType callRegister(WoodType type)
    {
        throw new AssertionError();
    }
}
