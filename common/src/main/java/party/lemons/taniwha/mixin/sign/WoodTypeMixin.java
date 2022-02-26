package party.lemons.taniwha.mixin.sign;

import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import party.lemons.taniwha.hooks.sign.SignTypeHooks;

@Mixin(WoodType.class)
public class WoodTypeMixin implements SignTypeHooks {

    @Shadow
    private static WoodType register(WoodType woodType){
        return null;
    }

    @Override
    public WoodType registerType(String name)
    {
        WoodType type = new WoodType(name);
        return register(type);
    }
}
