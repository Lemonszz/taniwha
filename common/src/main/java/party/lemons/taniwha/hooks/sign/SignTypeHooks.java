package party.lemons.taniwha.hooks.sign;

import net.minecraft.world.level.block.state.properties.WoodType;
import party.lemons.taniwha.mixin.sign.WoodTypeInvoker;

public class SignTypeHooks
{
    public static WoodType register(String name)
    {
        return WoodTypeInvoker.callRegister(name);
    }
}
